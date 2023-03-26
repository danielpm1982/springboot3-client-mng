package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.repository.AddressRepositorySpringDataJPAInterface;
import com.danielpm1982.springboot3clientmng.repository.ClientRepositorySpringDataJPAInterface;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressServiceSpringDataJPA implements AddressServiceInterface {
    private final AddressRepositorySpringDataJPAInterface addressRepositorySpringDataJPAInterface;
    private final ClientRepositorySpringDataJPAInterface clientRepositorySpringDataJPAInterface;
    public AddressServiceSpringDataJPA(AddressRepositorySpringDataJPAInterface addressRepositorySpringDataJPAInterface,
                                       ClientRepositorySpringDataJPAInterface clientRepositorySpringDataJPAInterface) {
        this.addressRepositorySpringDataJPAInterface = addressRepositorySpringDataJPAInterface;
        this.clientRepositorySpringDataJPAInterface=clientRepositorySpringDataJPAInterface;
    }
    @Override
    public Address saveAddress(Address address) {
        return addressRepositorySpringDataJPAInterface.save(address);
    }
    @Override
    public List<Address> findAllAddresses() {
        return addressRepositorySpringDataJPAInterface.findAll();
    }
    @Override
    public Address findAddressById(Long id) {
        return addressRepositorySpringDataJPAInterface.findById(id).orElse(null);
    }
    @Override
    public Address updateAddress(Address address) {
        return addressRepositorySpringDataJPAInterface.save(address);
    }
    @Override
    public void deleteAddressById(Long id) {
        addressRepositorySpringDataJPAInterface.deleteById(id);
    }
    @Override
    public void deleteAllAddresses() {
        addressRepositorySpringDataJPAInterface.deleteAll();
    }
    @Override
    public void truncateDBTable() {
        addressRepositorySpringDataJPAInterface.truncateDBTable();
    }
    @Override
    public void setClientOnAndSaveAddresses(Client persistentClient, List<Address> addressList) {
        addressList.forEach(x->x.setAddressClient(persistentClient));
        addressRepositorySpringDataJPAInterface.saveAllAndFlush(addressList);
    }
}
