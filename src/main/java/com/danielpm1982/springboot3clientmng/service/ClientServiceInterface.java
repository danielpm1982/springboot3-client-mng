package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Client;
import java.util.List;

public interface ClientServiceInterface {
    public Client saveClient(Client client);
    public List<Client> findAllClients();
    public Client findClientById(Long id);
    public Client updateClient(Client client);
    public void deleteClientById(Long id);
    public void deleteAllClients();
    public void truncateDBTable();
}
