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
    //deleteById() is overridden here in order to guarantee deletion even when address is already associated with a Client
    //the default spring data implementation wouldn't delete an Address when it would be mapping the Client association, to avoid
    //integrity constraint violations, but to delete the Address anyway just implement the method deleteById() by using a @Query, as below:
    @Query("delete from Address a where a.addressId=:addressId")
    @Modifying
    @Transactional
    public void deleteById(Long addressId);
}
