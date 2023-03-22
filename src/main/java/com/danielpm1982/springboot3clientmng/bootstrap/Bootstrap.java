package com.danielpm1982.springboot3clientmng.bootstrap;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.service.ClientServiceInterface;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class Bootstrap {
    private final ClientServiceInterface clientServiceInterface;
    private final Logger logger;
    public Bootstrap(ClientServiceInterface clientServiceInterface) {
        this.clientServiceInterface = clientServiceInterface;
        this.logger = LogManager.getLogger(Bootstrap.class);
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
        Client c1 = new Client(null, "client1name", "client1email");
        Client c2 = new Client(null, "client2name", "client2email");
        Client c3 = new Client(null, "client3name", "client3email");
        logger.log(Level.INFO, "Saving Clients:");
        clientServiceInterface.saveClient(c1);
        clientServiceInterface.saveClient(c2);
        clientServiceInterface.saveClient(c3);
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
    }
    private void truncateClientTable(){
        clientServiceInterface.truncateDBTable();
    }
}
