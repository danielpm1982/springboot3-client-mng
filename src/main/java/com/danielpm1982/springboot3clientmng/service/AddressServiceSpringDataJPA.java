package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.repository.AddressRepositorySpringDataJPAInterface;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceSpringDataJPA implements AddressServiceInterface {
    private final AddressRepositorySpringDataJPAInterface addressRepositorySpringDataJPAInterface;
    public AddressServiceSpringDataJPA(AddressRepositorySpringDataJPAInterface addressRepositorySpringDataJPAInterface) {
        this.addressRepositorySpringDataJPAInterface = addressRepositorySpringDataJPAInterface;
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
    public Optional<Address> findAddressById(Long id) {
        return addressRepositorySpringDataJPAInterface.findById(id);
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
    public List<Address> findAddressByAddressNumberEqualsAndAddressStreetLikeAndAddressCityLikeAllIgnoreCaseOrderByAddressIdAsc(Integer addressNumber,
                                                                                            String addressStreet,
                                                                                            String addressCity){
        return addressRepositorySpringDataJPAInterface.findByAddressNumberEqualsAndAddressStreetLikeAndAddressCityLikeAllIgnoreCaseOrderByAddressIdAsc(
                addressNumber,"%"+addressStreet+"%","%"+addressCity+"%");
    }
    public List<Address> findAddressByAddressCityLikeOrAddressStateLikeAllIgnoreCaseOrderByAddressIdAsc(String addressCity, String addressState) {
        return addressRepositorySpringDataJPAInterface.findByAddressCityLikeOrAddressStateLikeAllIgnoreCaseOrderByAddressIdAsc("%"+addressCity+"%","%"+addressState+"%");
    }
    public List<Address> findAddressesByAddressClientClientIdEquals(Long clientId){
        return addressRepositorySpringDataJPAInterface.findByAddressClientClientIdEquals(clientId);
    }
}
