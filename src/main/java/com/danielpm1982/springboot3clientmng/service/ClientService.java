package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.repository.ClientRepositoryInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService implements ClientServiceInterface {
    private final ClientRepositoryInterface clientRepositoryInterface;
    public ClientService(@Qualifier("clientRepository")ClientRepositoryInterface clientRepositoryInterface) {
        this.clientRepositoryInterface = clientRepositoryInterface;
    }
    @Override
    public Client saveClient(Client client) {
        return clientRepositoryInterface.save(client);
    }
    @Override
    public List<Client> findAllClients() {
        return clientRepositoryInterface.findAll();
    }
    @Override
    public Client findClientById(Long id) {
        return clientRepositoryInterface.findById(id);
    }
    @Override
    public Client updateClient(Client client) {
        return clientRepositoryInterface.update(client);
    }
    @Override
    public void deleteClientById(Long id) {
        clientRepositoryInterface.deleteById(id);
    }
    @Override
    public void deleteAllClients() {
        clientRepositoryInterface.deleteAll();
    }
    @Override
    public void truncateDBTable() {
        clientRepositoryInterface.truncateDBTable();
    }
}
