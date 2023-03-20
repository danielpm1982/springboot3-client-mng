package com.danielpm1982.springboot3clientmng.controller;
import com.danielpm1982.springboot3clientmng.bootstrap.Bootstrap;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.error.*;
import com.danielpm1982.springboot3clientmng.repository.ClientRepositoryInterface;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientRestController{
    private final ClientRepositoryInterface clientRepositoryInterface;
    private final Bootstrap bootstrap;
    public ClientRestController(ClientRepositoryInterface clientRepositoryInterface, Bootstrap bootstrap) {
        this.clientRepositoryInterface = clientRepositoryInterface;
        this.bootstrap = bootstrap;
    }
    @GetMapping({"/clients","/clients/"})
    private List<Client> getAllClients(){
        List<Client> clientList = clientRepositoryInterface.findAllClients();
        if(clientList.isEmpty()){
            throw new ClientNotFoundException("No Clients found !");
        } else{
            return clientList;
        }
    }
    @DeleteMapping({"/clients/delete-all", "/clients/delete-all/"})
    private ModelAndView deleteAllAndTruncateClients(){
        clientRepositoryInterface.deleteAllClients();
        clientRepositoryInterface.truncate();
        if(!clientRepositoryInterface.findAllClients().isEmpty()){
            throw new RuntimeException("Error deleting and/or truncating Clients !");
        } else{
            throw new ClientSuccessException("All Client registries deleted and database truncated ! Client Id counting reset !");
        }
    }
    @PutMapping({"/clients/reset-all", "/clients/reset-all/"})
    private ModelAndView resetAllAndTruncateClients(){
        clientRepositoryInterface.truncate();
        bootstrap.loadInitialSampleClients();
        return new ModelAndView("redirect:/api/clients");
    }
    @GetMapping({"/client/{clientId}", "/client/{clientId}/"})
    private Client getClientById(@PathVariable("clientId") Long clientId){
        Client client = clientRepositoryInterface.findClientById(clientId);
        if(client==null){
            throw new ClientNotFoundException("Client not found ! clientId="+clientId);
        } else{
            return client;
        }
    }
    @PostMapping({"/client/add", "/client/add/"})
    //@RequestBody set as "required = false" only to avoid default 500 exception and display the custom exception instead. A payload is required !
    private ModelAndView addClient(@RequestBody(required = false) Client client){
        if(client==null||client.getClientName().equals("")||client.getClientName()==null||
                client.getClientEmail().equals("")||client.getClientEmail()==null||
                client.getClientId()!=null){
           throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                   "containing both clientName and clientEmail properties with non-empty values. The clientId property, " +
                   "on the other hand, must not be sent, as its value is generated at the server side. Please, " +
                   "send another request with the proper payload.");
        } else{
            clientRepositoryInterface.saveClient(client);
            return new ModelAndView("redirect:/api/clients");
        }
    }
    @DeleteMapping({"/client/{clientId}/delete", "/client/{clientId}/delete/"})
    private ModelAndView deleteClientById(@PathVariable("clientId") Long clientId){
        Client client = clientRepositoryInterface.findClientById(clientId);
        if(client==null){
            throw new ClientNotFoundException("Client not found ! Cannot be deleted ! clientId="+clientId);
        } else{
            clientRepositoryInterface.deleteClientById(clientId);
        throw new ClientSuccessException("Client deleted ! clientId="+clientId);
        }
    }
    @PutMapping({"/client/{clientId}/update", "/client/{clientId}/update/"})
    private Client updateClient(@PathVariable("clientId") Long clientId, @RequestBody(required = true) Client client){
        if(clientRepositoryInterface.findClientById(clientId)==null){
            throw new ClientNotFoundException("Client not found ! Cannot be updated ! clientId="+clientId);
        }
        if(client==null||client.getClientName().equals("")||client.getClientName()==null||
                client.getClientEmail().equals("")||client.getClientEmail()==null||
                client.getClientId()!=null){
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "containing both clientName and clientEmail properties with non-empty values. The clientId property, " +
                    "on the other hand, must not be sent, as its value is generated only once at the server side. " +
                    "The clientId primary key (PK) cannot be updated ! Please, send another request with the proper payload.");
        }
        return clientRepositoryInterface.updateClient(clientRepositoryInterface.findClientById(clientId));
    }
}
