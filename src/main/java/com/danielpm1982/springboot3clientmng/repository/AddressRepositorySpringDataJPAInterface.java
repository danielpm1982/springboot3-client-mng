package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepositorySpringDataJPAInterface extends JpaRepository<Address,Long> {
    @Query("delete from Address")
    @Modifying
    @Transactional
    public void deleteAll();
    @Query(nativeQuery = true, value = "SET REFERENTIAL_INTEGRITY FALSE;TRUNCATE TABLE ADDRESS RESTART IDENTITY;SET REFERENTIAL_INTEGRITY TRUE")
    @Modifying
    @Transactional
    public void truncateDBTable();
}
