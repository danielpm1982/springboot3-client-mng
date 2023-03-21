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
        return clientRepositoryInterface.saveClient(client);
    }
    @Override
    public List<Client> findAllClients() {
        return clientRepositoryInterface.findAllClients();
    }
    @Override
    public Client findClientById(Long id) {
        return clientRepositoryInterface.findClientById(id);
    }
    @Override
    public Client updateClient(Client client) {
        return clientRepositoryInterface.updateClient(client);
    }
    @Override
    public void deleteClientById(Long id) {
        clientRepositoryInterface.deleteClientById(id);
    }
    @Override
    public void deleteAllClients() {
        clientRepositoryInterface.deleteAllClients();
    }
    @Override
    public void truncate() {
        clientRepositoryInterface.truncate();
    }
}
