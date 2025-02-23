package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Client> findAll(Pageable pageable){
        final TypedQuery<Client> typedQuery;
        String propertyToOrderBy = pageable.getSort().get().findFirst().get().getProperty();
        String directionToOrderBy = pageable.getSort().get().findFirst().get().getDirection().toString();
        switch (propertyToOrderBy){
            case "clientName":
                if(directionToOrderBy.equals("DESC")){
                    typedQuery = em.createQuery("from Client c order by c.clientName desc", Client.class);
                } else{
                    typedQuery = em.createQuery("from Client c order by c.clientName asc", Client.class);
                }
                break;
            case "clientEmail":
                if(directionToOrderBy.equals("DESC")){
                    typedQuery = em.createQuery("from Client c order by c.clientEmail desc", Client.class);
                } else{
                    typedQuery = em.createQuery("from Client c order by c.clientEmail asc", Client.class);
                }
                break;
            default:
                typedQuery = em.createQuery("from Client c order by c.clientId asc", Client.class);
        }
        typedQuery.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Client> clientListAtPage = typedQuery.getResultList();
        Query totalClientCountQuery = em.createQuery("select count(c.id) from Client c");
        final long totalClientCount = (long)totalClientCountQuery.getSingleResult();
        return new PageImpl<>(clientListAtPage, pageable, totalClientCount);
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
    public List<Client> findByClientEmailIgnoreCaseLikeOrderByClientNameAsc(String clientEmail){
        clientEmail = clientEmail.toLowerCase();
        TypedQuery<Client> queryList = em.createQuery("select c from Client c where c.clientEmail like '"+
                clientEmail+"' order by c.clientName asc", Client.class);
        return queryList.getResultList();
    }
    @Override
    public List<Client> findByClientNameIgnoreCaseLikeOrderByClientNameAsc(String clientName) {
        clientName = clientName.toLowerCase();
        TypedQuery<Client> queryList = em.createQuery("select c from Client c where c.clientName like '"+
                clientName+"' order by c.clientName asc", Client.class);
        return queryList.getResultList();
    }
}
