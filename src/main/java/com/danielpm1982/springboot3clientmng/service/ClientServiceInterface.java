package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Client;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface ClientServiceInterface {
    public Client saveClient(Client client);
    public List<Client> findAllClients();
    public Page<Client> findAllClientsPagedAndOrderedAscBy(Integer pageNumber, Integer pageSize, String propertyToOrderBy);
    public Optional<Client> findClientById(Long id);
    public boolean existsClientById(Long id);
    public Client updateClient(Client client);
    public void deleteClientById(Long id);
    public void deleteAllClients();
    public void truncateDBTable();
    public List<Client> findClientByClientEmailIgnoreCaseLikeOrderByClientNameAsc(String clientEmail);
    public List<Client> findClientByClientNameIgnoreCaseLikeOrderByClientNameAsc(String clientName);
}
