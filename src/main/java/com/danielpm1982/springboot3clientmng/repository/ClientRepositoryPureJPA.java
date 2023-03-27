package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryPureJPA implements ClientRepositoryPureJPAInterface{
    private final EntityManager em;
    public ClientRepositoryPureJPA(EntityManager entityManager) {
        this.em = entityManager;
    }
    @Transactional
    @Override
    public Client save(Client client) {
        em.persist(client);
        em.flush();
        return client;
    }
    @Override
    public List<Client> findAll() {
        return em.createQuery("from Client", Client.class).getResultList();
    }
    @Override
    public Optional<Client> findById(Long id) {
            return Optional.ofNullable(em.find(Client.class,id));
    }
    @Override
    public boolean existsById(Long id) {
        return em.contains(em.find(Client.class,id));
    }
    @Transactional
    @Override
    public Client update(Client client) {
        return em.merge(client);
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        em.remove(em.find(Client.class,id));
    }
    @Transactional
    @Override
    public void deleteAll() {
        em.createQuery("delete from Client").executeUpdate();
    }
    @Transactional
    @Override
    public void truncateDBTable() {
        Query nativeQuery = em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE;" +
                "TRUNCATE TABLE CLIENT RESTART IDENTITY;" +
                "SET REFERENTIAL_INTEGRITY TRUE;");
        nativeQuery.executeUpdate();
    }
}
