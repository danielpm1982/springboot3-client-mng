package com.danielpm1982.springboot3clientmng.controller;
import com.danielpm1982.springboot3clientmng.bootstrap.Bootstrap;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.error.*;
import com.danielpm1982.springboot3clientmng.service.ClientServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientRestController{
    private final ClientServiceInterface clientServiceInterface;
    private final Bootstrap bootstrap;
    public ClientRestController(@Qualifier("clientService") ClientServiceInterface clientServiceInterface, Bootstrap bootstrap) {
        this.clientServiceInterface = clientServiceInterface;
        this.bootstrap = bootstrap;
    }
    @GetMapping({"/clients","/clients/"})
    private List<Client> getAllClients(){
        List<Client> clientList = clientServiceInterface.findAllClients();
        if(clientList.isEmpty()){
            throw new ClientNotFoundException("No Clients found !");
        } else{
            return clientList;
        }
    }
    @GetMapping({"/clients/{clientId}", "/clients/{clientId}/"})
    private Client getClientById(@PathVariable("clientId") Long clientId){
        Client client = clientServiceInterface.findClientById(clientId);
        if(client==null){
            throw new ClientNotFoundException("Client not found ! clientId="+clientId);
        } else{
            return client;
        }
    }
    @PostMapping({"/clients", "/clients/"})
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
            clientServiceInterface.saveClient(client);
            return new ModelAndView("redirect:/api/clients");
        }
    }
    @DeleteMapping({"/clients/{clientId}", "/clients/{clientId}"})
    private ModelAndView deleteClientById(@PathVariable("clientId") Long clientId){
        Client client = clientServiceInterface.findClientById(clientId);
        if(client==null){
            throw new ClientNotFoundException("Client not found ! Cannot be deleted ! clientId="+clientId);
        } else{
            clientServiceInterface.deleteClientById(clientId);
        throw new ClientSuccessException("Client deleted ! clientId="+clientId);
        }
    }
    @PutMapping({"/clients/{clientId}", "/clients/{clientId}/"})
    private Client updateClient(@PathVariable("clientId") Long clientId, @RequestBody(required = true) Client client){
        if(clientServiceInterface.findClientById(clientId)==null){
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
        //the clientId is received as a @PathVariable argument and not at the updating payload data.
        //the clientId is a constant value (PK at the DB) that must be used to find the registry but can't be updated.
        //the clientId, received as a @PathVariable argument, is here set to the Client DTO, that received the updating data
        //from the payload (clientName and clientEmail).
        client.setClientId(clientId);
        //the Client DTO, then with all 3 fields values set, is sent to the persistence layer for updating the registry
        //at the DB.
        //the Client DTO used here must not be sent to the persistence layer without a valid and already existent clientId,
        //otherwise it would be handled as a new registry being added and not as an existing registry being updated.
        //At the ClientRepository, the saveOrUpdateClient() method is the same and takes that fact as a distinction: if the
        //instance sent there has a null or non-existent clientId it'll be saved as a new registry, otherwise, if it has a
        //valid and existing clientId it'll be updated.
        //Therefore, this method must not only validade that the @PathVariable clientId is valid and exists at the DB,
        //but also must set that clientId to the Client DTO, which is the instance that'll be sent to the persistence layer
        //for updating the existing registry.
        return clientServiceInterface.updateClient(client);
    }
    @DeleteMapping({"/clients/delete-all", "/clients/delete-all/"})
    private ModelAndView deleteAllAndTruncateClients(){
        clientServiceInterface.deleteAllClients();
        clientServiceInterface.truncate();
        if(!clientServiceInterface.findAllClients().isEmpty()){
            throw new RuntimeException("Error deleting and/or truncating Clients !");
        } else{
            throw new ClientSuccessException("All Client registries deleted and database truncated ! Client Id counting reset !");
        }
    }
    @PutMapping({"/clients/reset-all", "/clients/reset-all/"})
    private ModelAndView resetAllAndTruncateClients(){
        clientServiceInterface.truncate();
        bootstrap.loadInitialSampleClients();
        return new ModelAndView("redirect:/api/clients");
    }
}
