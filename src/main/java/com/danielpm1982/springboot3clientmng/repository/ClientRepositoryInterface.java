package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Client;
import java.util.List;

public interface ClientRepositoryInterface {
    public Client save(Client client);
    public List<Client> findAll();
    public Client findById(Long id);
    public Client update(Client client);
    public void deleteById(Long id);
    public void deleteAll();
    public void truncateDBTable();
}
