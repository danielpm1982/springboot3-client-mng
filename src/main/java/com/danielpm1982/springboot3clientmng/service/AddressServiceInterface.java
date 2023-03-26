package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.domain.Client;
import java.util.List;

public interface AddressServiceInterface {
    public Address saveAddress(Address address);
    public List<Address> findAllAddresses();
    public Address findAddressById(Long id);
    public Address updateAddress(Address address);
    public void deleteAddressById(Long id);
    public void deleteAllAddresses();
    public void truncateDBTable();
    public void setClientOnAndSaveAddresses(Client persistentClient, List<Address> addressList);
}
