package com.danielpm1982.springboot3clientmng.controller;
import com.danielpm1982.springboot3clientmng.bootstrap.Bootstrap;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.error.*;
import com.danielpm1982.springboot3clientmng.service.AddressServiceInterface;
import com.danielpm1982.springboot3clientmng.service.ClientServiceInterface;
import com.danielpm1982.springboot3clientmng.validator.AddressDTOValidator;
import com.danielpm1982.springboot3clientmng.validator.ClientDTOValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientRestController{
    private final ClientServiceInterface clientServiceInterface;
    private final AddressServiceInterface addressServiceInterface;
    private final Bootstrap bootstrap;
//    private final String CLIENT_SERVICE_BEAN_ID="clientServicePureJPA";
    private final String CLIENT_SERVICE_BEAN_ID = "clientServiceSpringDataJPA";
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
//        final Client client = clientServiceInterface.findClientById(clientId).get();
//        if(client==null){
//            throw new ClientNotFoundException("Client not found ! clientId="+clientId);
//        } else{
//            return client;
//        }
        return clientServiceInterface.findClientById(clientId).orElseThrow(()->new ClientNotFoundException("Client not found ! clientId="+clientId));
    }
    @GetMapping({"/clients/pageableAndOrderableAscBy", "/pageableAndOrderableAscBy/"})
    private List<Client> getAllClientsPagedAndOrderedBy(@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                                        @RequestParam(value = "propertyToOrderBy", required = true) String propertyToOrderBy){
        Page<Client> clientPage = clientServiceInterface.findAllClientsPagedAndOrderedAscBy(pageNumber, pageSize, propertyToOrderBy);
        List<Client> clientListForPage = clientPage.getContent();
        if(clientListForPage.isEmpty()){
            throw new ClientNotFoundException("No Clients found for this Page ! actualPageNumber="+
                    (clientPage.getNumber()+1)+" totalPages="+clientPage.getTotalPages()+" actualPageElements="+clientPage.getNumberOfElements()+
                    " totalElements="+clientPage.getTotalElements()+" elementsPerPage="+clientPage.getSize()+" propertyToOrderBy="+propertyToOrderBy);
        } else{
            return clientListForPage;
        }
    }
    @GetMapping({"/clients/findByEmail", "/clients/findByEmail/"})
    private List<Client> getClientByClientEmail(@RequestParam(value = "clientEmail", required = true) String clientEmail){
        List<Client> clientList = clientServiceInterface.findClientByClientEmailIgnoreCaseLikeOrderByClientNameAsc(clientEmail);
        if(clientList.isEmpty()){
            throw new ClientNotFoundException("No Client found ! clientEmail="+clientEmail);
        } else{
            return clientList;
        }
    }
    @GetMapping({"/clients/findByName", "/clients/findByName/"})
    private List<Client> getClientByClientName(@RequestParam(value = "clientName", required = true) String clientName){
        List<Client> clientList = clientServiceInterface.findClientByClientNameIgnoreCaseLikeOrderByClientNameAsc(clientName);
        if(clientList.isEmpty()){
            throw new ClientNotFoundException("No Client found ! clientName="+clientName);
        } else{
            return clientList;
        }
    }
    @GetMapping({"/clients/{clientId}/addresses", "/clients/{clientId}/addresses/"})
    private List<Address> getAddressListFromClient(@PathVariable("clientId") Long clientId){
        final Client persistentClient = clientServiceInterface.findClientById(clientId).
                orElseThrow(()->new ClientNotFoundException("Client not found ! Cannot get Addresses ! clientId="+clientId));
        return persistentClient.getClientAddressList();
    }
    @PostMapping({"/clients", "/clients/"})
    //@RequestBody set as "required = false" only to avoid default 500 exception and display the custom exception instead. A payload is required !
    private ModelAndView addClient(@RequestBody(required = false) Client clientDTO){
        ClientDTOValidator.validateClientDTOForAddingClient(clientDTO);
        clientServiceInterface.saveClient(clientDTO);
        return new ModelAndView("redirect:/api/clients");
        //Regarding the PureJPA Implementation:
        //The Client DTO argument here is a transient entity of Client, that will be persisted into the JPA Persistence Context,
        //through the ClientRepositoryPureJPA (DAO), and its state (data) will be saved at the DB, returning the persistent entity.
        //The Client instance initially works as a DTO, but, when persisted into the Persistence Context, it is turned into a
        //real persistent entity, whose data is synchronized and flushed to the DB, and then this same persistent entity is
        //returned. While being persisted, it is set the clientId automatically, according to the auto-generated PK at the DBMS.
        //The clientId field initially must be null (or else it's ignored), and then it is automatically set only when the instance
        //is turned into a persistent entity. After we have the persistent entity returned, we can then get its clientId value if needed
        //(the same PK value at the DB).
        //Although possible, we shouldn't use the JPA merge() method, instead of persist(), at the Repository implementation,
        //to persist new transient entities. That violates completely the semantics of the JPA specifications for both methods.
        //Some may achieve that by mimicking the transient as a detached entity (setting the same id of a pre-existing registry), and
        //that would work, but, again, that breaks completely the purpose of such JPA methods. JPA merge() should only be used to merge
        //persistent or detached entities, while transient new entities should only be persisted by using JPA persist().
        //We decided to follow the specs here for both addClient() and updateClient() methods, at the ClientRepositoryPureJPA.
        //Regarding the SpringDataJPA Implementation:
        //In the case of SpringDataJPA, though, considering the automatic implementation of the ClientRepositorySpringDataJPAInterface
        //interface, it'll use the merge() method (both for saving as for updating) instead of persist(). We could eventually override
        //and reimplement the save() method for also using persist(), but we could also use the merge() Spring standard implementation
        //as well. Either way, Client instances will have their data saved at the DB.
    }
    @DeleteMapping({"/clients/{clientId}", "/clients/{clientId}/"})
    private void deleteClientById(@PathVariable("clientId") Long clientId){
        if(!clientServiceInterface.existsClientById(clientId)){
            throw new ClientNotFoundException("Client not found ! Cannot be deleted ! clientId="+clientId);
        } else{
            clientServiceInterface.deleteClientById(clientId);
            throw new ClientSuccessException("Client deleted ! clientId="+clientId);
        }
    }
    @PutMapping({"/clients/{clientId}", "/clients/{clientId}/"})
    //@RequestBody set as "required = false" only to avoid default 500 exception and display the custom exception instead. A payload is required !
    private Client updateClient(@PathVariable("clientId") Long clientId, @RequestBody(required = false) Client clientDTO){
        final Client persistentClient = clientServiceInterface.findClientById(clientId).
                orElseThrow(()->new ClientNotFoundException("Client not found ! Cannot be updated ! clientId="+clientId));
        ClientDTOValidator.validateClientDTOForUpdatingClient(clientDTO);
        persistentClient.setClientName(clientDTO.getClientName());
        persistentClient.setClientEmail(clientDTO.getClientEmail());
        return clientServiceInterface.updateClient(persistentClient);
        //Regarding the PureJPA Implementation:
        //The clientId is received as a @PathVariable argument and not as part of the updating payload data.
        //The clientId is a constant value (PK at the DB) that must be used to find the registry but can't be updated.
        //The clientId value, received as the @PathVariable argument, is used to fetch a managed Client instance -
        //persistent entity - from the JPA persistence context (with values of the actual DB state). The managed Client instance
        //is later set with the values from the Client DTO and next sent to be merged back with the persistence context, and then
        //synchronized with the DB, updating the state of the respective DB registry with the data from the payload (clientName
        //and clientEmail, but not clientId). The returned Client instance is a persistent entity (with values already updated)
        //and not the Client DTO.
        //It wouldn't be right to send the Client DTO to be merged, even after setting the right clientId at it, as it is a transient
        //entity and not a persistent or true detached one. We could mimic it as detached though - when setting an existing clientId -
        //but that would go against the JPA specs.
        //Therefore, we chose to use a true persistent entity - persistentClient - in order to send it (along with the updating data)
        //to be merged with the persistence context, instead of the Client DTO.
    }
    @DeleteMapping({"/clients/delete-all-no-truncate", "/clients/delete-all-no-truncate/"})
    private void deleteAllClients(){
        addressServiceInterface.deleteAllAddresses(); //first delete entities which, in the DB, have FK to Clients
        clientServiceInterface.deleteAllClients();
        if(!clientServiceInterface.findAllClients().isEmpty()){
            throw new RuntimeException("Error deleting Clients !");
        } else{
            throw new ClientSuccessException("All Client registries deleted !");
        }
    }
    @DeleteMapping({"/clients/delete-all-and-truncate", "/clients/delete-all-and-truncate/"})
    private void deleteAllAndTruncateClients(){
        addressServiceInterface.deleteAllAddresses(); //first delete entities which, in the DB, have FK to Clients
        clientServiceInterface.deleteAllClients();
        clientServiceInterface.truncateDBTable();
        addressServiceInterface.truncateDBTable();
        if(!clientServiceInterface.findAllClients().isEmpty()){
            throw new RuntimeException("Error deleting and/or truncating Clients !");
        } else{
            throw new ClientSuccessException("All Client registries deleted and database truncated ! Client Id counting reset !");
        }
    }
    @PutMapping({"/clients/reset-default", "/clients/reset-default/"})
    private ModelAndView resetAllAndTruncateClients(){
        addressServiceInterface.truncateDBTable();
        clientServiceInterface.truncateDBTable();
        bootstrap.loadInitialSampleClients();
        return new ModelAndView("redirect:/api/clients");
    }
    //The endpoint below creates a List of Addresses, based on the Address payload, sets the Client for each Address and saves them at the DB.
    //Either If the Client has a null/empty or a non-null/non-empty Address list, he will have that field/property updated and mapped by
    //the updated addressClient field at each Address instance.
    //@RequestBody set as "required = false" only to avoid default 500 exception and display the custom exception instead. A payload is required !
    @PutMapping({"/clients/{clientId}/addresses", "/clients/{clientId}/addresses/"})
    private ModelAndView setAddressListOnClient(@PathVariable("clientId") Long clientId, @RequestBody(required = false) List<Address> addressListDTO){
        final Client persistentClient = clientServiceInterface.findClientById(clientId).
                orElseThrow(()->new ClientNotFoundException("Client not found ! Cannot add Address ! clientId="+clientId));
        AddressDTOValidator.validateAddressListDTOForSettingAddressListOnClient(addressListDTO);
        addressServiceInterface.setClientOnAndSaveAddresses(persistentClient, addressListDTO);
        return new ModelAndView("redirect:/api/clients/"+clientId);
    }
}
