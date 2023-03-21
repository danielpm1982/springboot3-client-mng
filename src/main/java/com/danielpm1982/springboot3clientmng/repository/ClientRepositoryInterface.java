package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Client;
import java.util.List;

public interface ClientRepositoryInterface {
    public Client saveOrUpdateClient(Client client);
    public List<Client> findAllClients();
    public Client findClientById(Long id);
    public void deleteClientById(Long id);
    public void deleteAllClients();
    public void truncate();
}
