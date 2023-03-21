package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ClientRepository implements ClientRepositoryInterface{
    private final EntityManager em;
    public ClientRepository(EntityManager entityManager) {
        this.em = entityManager;
    }
    @Transactional
    public Client saveOrUpdateClient(Client client) {
        return em.merge(client);
    }
    @Override
    public List<Client> findAllClients() {
        return em.createQuery("from Client", Client.class).getResultList();
    }
    @Override
    public Client findClientById(Long id) {
        return em.find(Client.class,id);
    }
    @Transactional
    @Override
    public void deleteClientById(Long id) {
        em.remove(this.findClientById(id));
    }
    @Transactional
    @Override
    public void deleteAllClients() {
        em.createQuery("delete from Client").executeUpdate();
    }
    @Transactional
    @Override
    public void truncate() {
        Query nativeQuery = em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE;" +
                "TRUNCATE TABLE CLIENT RESTART IDENTITY;" +
                "SET REFERENTIAL_INTEGRITY TRUE;");
        nativeQuery.executeUpdate();
    }
}
