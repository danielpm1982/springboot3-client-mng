package com.danielpm1982.springboot3clientmng.bootstrap;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.service.AddressServiceInterface;
import com.danielpm1982.springboot3clientmng.service.ClientServiceInterface;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class Bootstrap {
    private final ClientServiceInterface clientServiceInterface;
    private final AddressServiceInterface addressServiceInterface;
    private final Logger logger;
    public Bootstrap(@Qualifier("clientServiceSpringDataJPA") ClientServiceInterface clientServiceInterface,
//            @Qualifier("clientServicePureJPA") ClientServiceInterface clientServiceInterface,
                     @Qualifier("addressServiceSpringDataJPA") AddressServiceInterface addressServiceInterface) {
        this.clientServiceInterface = clientServiceInterface;
        this.addressServiceInterface = addressServiceInterface;
        this.logger = LogManager.getLogger(this.getClass());
    }
    public void testClientRepository(){
        logger.log(Level.WARN, "Initiating CRUD tests for Client REST API:");
        try {
            testAddClients();
            testGetSingleClient();
            testListAllClients();
            testUpdateSingleClient();
            testDeleteSingleCLient();
            testDeleteAllClients();
            truncateClientTable();
            logger.log(Level.WARN, "All Tests Passed !!!");
        } catch (Exception e){
            logger.log(Level.FATAL, "One or more of the tests FAILED !!");
            logger.log(Level.FATAL, "Exception type: "+e.getClass()+". Exception message: "+e.getMessage()+".");
            logger.log(Level.FATAL, Arrays.stream(e.getStackTrace()).toList());
            logger.log(Level.FATAL, "SHUTTING APP DOWN...");
            System.exit(1);
        }
    }
    public void loadInitialSampleClients(){
        testAddClients();
    }
    private void testAddClients(){
        Client c1 = new Client(null, "client1name", "client1email",null);
        Client c2 = new Client(null, "client2name", "client2email",null);
        Client c3 = new Client(null, "client3name", "client3email",null);
        c1 = clientServiceInterface.saveClient(c1);
        c2 = clientServiceInterface.saveClient(c2);
        clientServiceInterface.saveClient(c3);
        Address a1 = new Address(null, "street1", 100, "city1","state1","country1","60000-000");
        a1 = addressServiceInterface.saveAddress(a1);
        Address a2 = new Address(null, "street2", 100, "city2","state2","country2","90000-000");
        a2 = addressServiceInterface.saveAddress(a2);
        clientServiceInterface.setAddressOnClient(a1, c1.getClientId());
        clientServiceInterface.setAddressOnClient(a2, c2.getClientId());
        logger.log(Level.INFO, "Saving Clients:");
        logger.log(Level.INFO, "Clients Saved !");
    }
    private void testListAllClients(){
        logger.log(Level.INFO, "Listing All Clients:");
        clientServiceInterface.findAllClients().forEach(x->logger.log(Level.INFO,x));
    }
    private void testGetSingleClient(){
        logger.log(Level.INFO, "Finding Client Id=1");
        logger.log(Level.INFO,clientServiceInterface.findClientById(1L));
    }
    private void testUpdateSingleClient(){
        logger.log(Level.INFO, "Updating Client Id=1 with name \"updated\" at the end:");
        Client c1 = clientServiceInterface.findClientById(1L);
        c1.setClientName(c1.getClientName()+"-updated");
        logger.log(Level.INFO,clientServiceInterface.updateClient(c1));
    }
    private void testDeleteSingleCLient(){
        logger.log(Level.INFO, "Delete Client Id=3:");
        clientServiceInterface.deleteClientById(3L);
        clientServiceInterface.findAllClients().forEach(x->logger.log(Level.INFO,x));
    }
    private void testDeleteAllClients(){
        logger.log(Level.INFO, "Delete All Clients:");
        clientServiceInterface.deleteAllClients();
        clientServiceInterface.findAllClients().forEach(x->logger.log(Level.INFO,x));
        addressServiceInterface.deleteAllAddresses();
        addressServiceInterface.findAllAddresses().forEach(x->logger.log(Level.INFO,x));
    }
    private void truncateClientTable(){
        clientServiceInterface.truncateDBTable();
        addressServiceInterface.truncateDBTable();
    }
}
