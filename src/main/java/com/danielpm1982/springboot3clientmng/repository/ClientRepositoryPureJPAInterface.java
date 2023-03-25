package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepositoryPureJPAInterface {
    public Client save(Client client);
    public List<Client> findAll();
    public Optional<Client> findById(Long id);
    public Client update(Client client);
    public void deleteById(Long id);
    public void deleteAll();
    public void truncateDBTable();
}
