package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.domain.Client;
import java.util.List;
import java.util.Optional;

public interface AddressServiceInterface {
    public Address saveAddress(Address address);

    public List<Address> findAllAddresses();

    public Optional<Address> findAddressById(Long id);

    public Address updateAddress(Address address);

    public void deleteAddressById(Long id);

    public void deleteAllAddresses();

    public void truncateDBTable();

    public void setClientOnAndSaveAddresses(Client persistentClient, List<Address> addressList);

    public List<Address> findAddressByAddressNumberEqualsAndAddressStreetLikeAndAddressCityLikeAllIgnoreCaseOrderByAddressIdAsc(Integer addressNumber,
                                                                                                                                String addressStreet,
                                                                                                                                String addressCity);
    public List<Address> findAddressByAddressCityLikeOrAddressStateLikeAllIgnoreCaseOrderByAddressIdAsc(String addressCity, String addressState);
    public List<Address> findAddressesByAddressClientClientIdEquals(Long clientId);
}
