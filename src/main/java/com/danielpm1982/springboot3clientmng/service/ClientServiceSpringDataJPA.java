package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.repository.ClientRepositorySpringDataJPAInterface;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientServiceSpringDataJPA implements ClientServiceInterface {
    private final ClientRepositorySpringDataJPAInterface clientRepositorySpringDataJPAInterface;
    public ClientServiceSpringDataJPA(ClientRepositorySpringDataJPAInterface clientRepositorySpringDataJPAInterface) {
        this.clientRepositorySpringDataJPAInterface = clientRepositorySpringDataJPAInterface;
    }
    @Override
    public Client saveClient(Client client) {
        return clientRepositorySpringDataJPAInterface.save(client);
    }
    @Override
    public List<Client> findAllClients() {
        return clientRepositorySpringDataJPAInterface.findAll();
    }
    @Override
    public Client findClientById(Long id) {
        return clientRepositorySpringDataJPAInterface.findById(id).get();
    }
    @Override
    public Client updateClient(Client client) {
        return clientRepositorySpringDataJPAInterface.save(client);
    }
    @Override
    public void deleteClientById(Long id) {
        clientRepositorySpringDataJPAInterface.deleteById(id);
    }
    @Override
    public void deleteAllClients() {
        clientRepositorySpringDataJPAInterface.deleteAll();
    }
    @Override
    public void truncateDBTable() {
        clientRepositorySpringDataJPAInterface.truncateDBTable();
    }
}
