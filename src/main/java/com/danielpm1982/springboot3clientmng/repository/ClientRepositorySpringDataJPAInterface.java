package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.domain.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepositorySpringDataJPAInterface extends JpaRepository<Client,Long> {
    @Query("delete from Client")
    @Modifying
    @Transactional
    public void deleteAll();
    @Query(nativeQuery = true, value = "SET REFERENTIAL_INTEGRITY FALSE;TRUNCATE TABLE CLIENT RESTART IDENTITY;SET REFERENTIAL_INTEGRITY TRUE")
    @Modifying
    @Transactional
    public void truncateDBTable();
    @Query("update Client c set c.clientAddress=:address where c.id=:clientId")
    @Modifying
    @Transactional
    public void setAddressOnClient(@Param("address") Address address, @Param("clientId") Long clientId);
}
