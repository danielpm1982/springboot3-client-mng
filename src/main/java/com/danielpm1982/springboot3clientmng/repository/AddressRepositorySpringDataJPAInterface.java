package com.danielpm1982.springboot3clientmng.repository;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.domain.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AddressRepositorySpringDataJPAInterface extends JpaRepository<Address,Long> {
    @Query("delete from Address")
    @Modifying
    @Transactional
    public void deleteAll();
    @Query(nativeQuery = true, value = "SET REFERENTIAL_INTEGRITY FALSE;TRUNCATE TABLE ADDRESS RESTART IDENTITY;SET REFERENTIAL_INTEGRITY TRUE")
    @Modifying
    @Transactional
    public void truncateDBTable();
    @Query("select a from Address a where a.addressClient=:client")
    public List<Address> findAddressesByClient(Client client);
    @Query("delete from Address a where a.addressId=:addressId")
    @Modifying
    @Transactional
    public void deleteById(Long addressId);
}
