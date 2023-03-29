package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ClientRepositorySpringDataJPAInterface extends JpaRepository<Client,Long> {
    @Query("delete from Client")
    @Modifying
    @Transactional
    public void deleteAll();
    @Query(nativeQuery = true, value = "SET REFERENTIAL_INTEGRITY FALSE;TRUNCATE TABLE CLIENT RESTART IDENTITY;SET REFERENTIAL_INTEGRITY TRUE")
    @Modifying
    @Transactional
    public void truncateDBTable();
    public List<Client> findByClientEmailIgnoreCaseLikeOrderByClientNameAsc(String clientEmail);
    public List<Client> findByClientNameIgnoreCaseLikeOrderByClientNameAsc(String clientName);
}
