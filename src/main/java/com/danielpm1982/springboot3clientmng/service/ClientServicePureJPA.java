package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.repository.ClientRepositoryPureJPAInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientServicePureJPA implements ClientServiceInterface {
    private final ClientRepositoryPureJPAInterface clientRepositoryPureJPAInterface;
    public ClientServicePureJPA(@Qualifier("clientRepositoryPureJPA") ClientRepositoryPureJPAInterface clientRepositoryPureJPAInterface) {
        this.clientRepositoryPureJPAInterface = clientRepositoryPureJPAInterface;
    }
    @Override
    public Client saveClient(Client client) {
        return clientRepositoryPureJPAInterface.save(client);
    }
    @Override
    public List<Client> findAllClients() {
        return clientRepositoryPureJPAInterface.findAll();
    }
    @Override
    public Client findClientById(Long id) {
        return clientRepositoryPureJPAInterface.findById(id).orElse(null);
    }
    @Override
    public Client updateClient(Client client) {
        return clientRepositoryPureJPAInterface.update(client);
    }
    @Override
    public void deleteClientById(Long id) {
        clientRepositoryPureJPAInterface.deleteById(id);
    }
    @Override
    public void deleteAllClients() {
        clientRepositoryPureJPAInterface.deleteAll();
    }
    @Override
    public void truncateDBTable() {
        clientRepositoryPureJPAInterface.truncateDBTable();
    }
}
