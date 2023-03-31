package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ClientRepositoryPureJPAInterface {
    public Client save(Client client);
    public List<Client> findAll();
    public Page<Client> findAll(Pageable pageable);
    public Optional<Client> findById(Long id);
    public boolean existsById(Long id);
    public Client update(Client client);
    public void deleteById(Long id);
    public void deleteAll();
    public void truncateDBTable();
    public List<Client> findByClientEmailIgnoreCaseLikeOrderByClientNameAsc(String clientEmail);
    public List<Client> findByClientNameIgnoreCaseLikeOrderByClientNameAsc(String clientName);
}
