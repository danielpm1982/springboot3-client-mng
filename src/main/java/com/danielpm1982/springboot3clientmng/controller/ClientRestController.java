package com.danielpm1982.springboot3clientmng.controller;
import com.danielpm1982.springboot3clientmng.bootstrap.Bootstrap;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.error.*;
import com.danielpm1982.springboot3clientmng.service.AddressServiceInterface;
import com.danielpm1982.springboot3clientmng.service.ClientServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientRestController{
    private final ClientServiceInterface clientServiceInterface;
    private final AddressServiceInterface addressServiceInterface;
    private final Bootstrap bootstrap;
    private final String CLIENT_SERVICE_BEAN_ID="clientServicePureJPA";
//    private final String CLIENT_SERVICE_BEAN_ID = "clientServiceSpringDataJPA";
    private final String ADDRESS_SERVICE_BEAN_ID = "addressServiceSpringDataJPA";
    public ClientRestController(@Qualifier(value=CLIENT_SERVICE_BEAN_ID) ClientServiceInterface clientServiceInterface,
                                @Qualifier(value=ADDRESS_SERVICE_BEAN_ID) AddressServiceInterface addressServiceInterface,
                                Bootstrap bootstrap) {
        this.clientServiceInterface = clientServiceInterface;
        this.addressServiceInterface=addressServiceInterface;
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
    private ModelAndView addClient(@RequestBody(required = false) Client clientDTO){
        if(clientDTO==null||clientDTO.getClientName()==null||clientDTO.getClientName().equals("")||
                clientDTO.getClientEmail()==null||clientDTO.getClientEmail().equals("")||
                clientDTO.getClientId()!=null){
           throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                   "containing both clientName and clientEmail properties with non-empty values. The clientId property, " +
                   "on the other hand, must not be sent, as its value is generated once and only at the server side. Please, " +
                   "send another request with the proper payload.");
        } else if(clientDTO.getClientAddress()!=null){
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "and must not contain the clientAddress property. Please, send another request with the proper payload.");
        } else{
                //Regarding the PureJPA Implementation:
                //The Client DTO argument here is a transient entity of Client, that will be persisted into the JPA Persistence Context,
                //at the Repository (DAO), and its state (data) will be saved at the DB, returning the persistent entity.
                //The Client instance initially works as a DTO, but, when persisted into the Persistence Context, it is turned into a
                //real persistent entity, whose data is synchronized and flushed to the DB, and then this same persistent entity is
                //returned. While being persisted, it is set the clientId automatically, according to the auto-generated PK at the DBMS.
                //The clientId field initially must be null, and is automatically set only when the instance is turned into a persistent
                //entity. After we have the persistent entity returned, we can then get its clientId value if needed (the same PK value at
                //the DB).
                //Although possible, we shouldn't use the JPA merge() method, instead of the persist() one, at the Repository implementation,
                //to persist new transient entities. That violates completely the semantics of the JPA specifications for both methods.
                //Some may achieve that by mimicking the transient as a detached entity (setting the same id of a pre-existing registry), and
                //that would work, but, again, that breaks completely the purpose of such JPA methods. JPA merge() should only be used to merge
                //persistent or detached entities, while transient new entities should only be persisted by using JPA persist().
                //We decided to follow the specs here... both at the addClient() as at the updateClient() methods... as well as at the
                //ClientRepository.
                //Regarding the SpringDataJPA Implementation:
                //In the case of SpringDataJPA, considering the automatic implementation of the JPARepository interface, it'll use the merge()
                //method (both for saving as for updating) instead of the persist() one.
                clientServiceInterface.saveClient(clientDTO);
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
    private Client updateClient(@PathVariable("clientId") Long clientId, @RequestBody(required = true) Client clientDTO){
        final Client managedClient = clientServiceInterface.findClientById(clientId);
        if(managedClient==null){
            throw new ClientNotFoundException("Client not found ! Cannot be updated ! clientId="+clientId);
        }
        if(clientDTO==null||clientDTO.getClientName()==null||clientDTO.getClientName().equals("")||
                clientDTO.getClientEmail()==null||clientDTO.getClientEmail().equals("")||
                clientDTO.getClientId()!=null){
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "containing both clientName and clientEmail properties with non-empty values. The clientId property, " +
                    "on the other hand, must not be sent, as its value is generated once and only at the server side. " +
                    "The clientId primary key (PK) cannot be updated ! Please, send another request with the proper payload.");
        } else if(clientDTO.getClientAddress()!=null){
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "and must not contain the clientAddress property. Please, send another request with the proper payload.");
        } else {
            //Regarding the PureJPA Implementation:
            //The clientId is received as a @PathVariable argument and not as part of the updating payload data.
            //The clientId is a constant value (PK at the DB) that must be used to find the registry but can't be updated.
            //The clientId value, received as the @PathVariable argument, is used to fetch a managed Client instance -
            //persistent entity - from the JPA persistence context (with values of the actual DB state). The managed Client instance
            //is later set with the values from the Client DTO and next sent to be merged back with the persistence context, and then
            //synchronized with the DB, updating the state of the respective DB registry with the data from the payload (clientName
            //and clientEmail, but not the clientId). The returned Client instance is persistent entity (with values already updated)
            //and not the Client DTO.
            //It wouldn't be right to send the Client DTO to be merged, even after setting the right clientId at it, as it is a transient
            //entity and not a persistent or true detached one (we could mimic it as detached though, but that would break the JPA specs).
            //Therefore, we chose to use a true persistent entity - managedClient - in order to send it (along with the updating data) to
            //be merged with the persistence context, instead of sending a mimicked (as "detached") Client DTO, directly.
            managedClient.setClientName(clientDTO.getClientName());
            managedClient.setClientEmail(clientDTO.getClientEmail());
            Client mergedClient = clientServiceInterface.updateClient(managedClient);
            return mergedClient;
        }
    }
    @DeleteMapping({"/clients/delete-all-no-truncate", "/clients/delete-all-no-truncate/"})
    private ModelAndView deleteAllClients(){
        clientServiceInterface.deleteAllClients();
        addressServiceInterface.deleteAllAddresses();
        if(!clientServiceInterface.findAllClients().isEmpty()){
            throw new RuntimeException("Error deleting Clients !");
        } else{
            throw new ClientSuccessException("All Client registries deleted !");
        }
    }
    @DeleteMapping({"/clients/delete-all-and-truncate", "/clients/delete-all-and-truncate/"})
    private ModelAndView deleteAllAndTruncateClients(){
        clientServiceInterface.deleteAllClients();
        clientServiceInterface.truncateDBTable();
        addressServiceInterface.deleteAllAddresses();
        addressServiceInterface.truncateDBTable();
        if(!clientServiceInterface.findAllClients().isEmpty()){
            throw new RuntimeException("Error deleting and/or truncating Clients !");
        } else{
            throw new ClientSuccessException("All Client registries deleted and database truncated ! Client Id counting reset !");
        }
    }
    @PutMapping({"/clients/reset-default", "/clients/reset-default/"})
    private ModelAndView resetAllAndTruncateClients(){
        clientServiceInterface.truncateDBTable();
        addressServiceInterface.truncateDBTable();
        bootstrap.loadInitialSampleClients();
        return new ModelAndView("redirect:/api/clients");
    }
}
