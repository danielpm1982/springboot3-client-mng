package com.danielpm1982.springboot3clientmng.controller;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.error.*;
import com.danielpm1982.springboot3clientmng.service.AddressServiceInterface;
import com.danielpm1982.springboot3clientmng.validator.AddressDTOValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressRestController {
    private final AddressServiceInterface addressServiceInterface;
    private final String ADDRESS_SERVICE_BEAN_ID = "addressServiceSpringDataJPA";
    public AddressRestController(@Qualifier(value=ADDRESS_SERVICE_BEAN_ID) AddressServiceInterface addressServiceInterface) {
        this.addressServiceInterface=addressServiceInterface;
    }
    @GetMapping({"/addresses","/addresses/"})
    private List<Address> getAllAddresses(){
        List<Address> addressList = addressServiceInterface.findAllAddresses();
        if(addressList.isEmpty()){
            throw new AddressNotFoundException("No Addresses found !");
        } else{
            return addressList;
        }
    }
    @GetMapping({"/addresses/{addressId}", "/addresses/{addressId}/"})
    private Address getAddressById(@PathVariable("addressId") Long addressId){
        final Address address = addressServiceInterface.findAddressById(addressId);
        if(address==null){
            throw new AddressNotFoundException("Address not found ! addressId="+addressId);
        } else{
            return address;
        }
    }
    //Although Address instances could be created and saved at the DB with no association with Clients, in order to avoid orphan Addresses -
    //that are set not to be removed automatically - it's advisable to only add addresses along with the respective Client association,
    //by using the endpoint: PUT /clients/{clientId}/addresses" or PUT "/clients/{clientId}/addresses/". Through that, not only the Address
    //instances will be created and saved, but also they'll have, at once, the respective Client associated to them. There would be very
    //few scenarios in which an Address should be saved without its relationship being set to a Client at the same request event.
    //@RequestBody set as "required = false" only to avoid default 500 exception and display the custom exception instead. A payload is required !
    @PostMapping({"/addresses", "/addresses/"})
    //@RequestBody set as "required = false" only to avoid default 500 exception and display the custom exception instead. A payload is required !
    private ModelAndView addAddress(@RequestBody(required = false) Address addressDTO){
        AddressDTOValidator.validateAddressDTOForAddingAddress(addressDTO);
        addressServiceInterface.saveAddress(addressDTO);
        return new ModelAndView("redirect:/api/addresses");
    }
    @DeleteMapping({"/addresses/{addressId}", "/addresses/{addressId}/"})
    private void deleteAddressById(@PathVariable("addressId") Long addressId){
        final Address persistentAddress = addressServiceInterface.findAddressById(addressId);
        if(persistentAddress==null){
            throw new AddressNotFoundException("Address not found ! Cannot be deleted ! addressId="+addressId);
        } else{
            addressServiceInterface.deleteAddressById(addressId);
            throw new AddressSuccessException("Address deleted ! addressId="+addressId);
        }
    }
    //@RequestBody set as "required = false" only to avoid default 500 exception and display the custom exception instead. A payload is required !
    @PutMapping({"/addresses/{addressId}", "/addresses/{addressId}/"})
    private Address updateAddress(@PathVariable("addressId") Long addressId, @RequestBody(required = false) Address addressDTO){
        final Address persistentAddress = addressServiceInterface.findAddressById(addressId);
        if(persistentAddress==null){
            throw new AddressNotFoundException("Address not found ! Cannot be updated ! addressId="+addressId);
        }
        AddressDTOValidator.validateAddressDTOForUpdatingAddress(addressDTO);
        persistentAddress.setAddressStreet(addressDTO.getAddressStreet());
        persistentAddress.setAddressNumber(addressDTO.getAddressNumber());
        persistentAddress.setAddressCity(addressDTO.getAddressCity());
        persistentAddress.setAddressState(addressDTO.getAddressState());
        persistentAddress.setAddressCountry(addressDTO.getAddressCountry());
        persistentAddress.setAddressZipCode(addressDTO.getAddressZipCode());
        return addressServiceInterface.updateAddress(persistentAddress);
    }
}
