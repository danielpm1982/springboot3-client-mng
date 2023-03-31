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
        return addressServiceInterface.findAddressById(addressId).
                orElseThrow(()->new AddressNotFoundException("Address not found ! addressId="+addressId));
    }
    @GetMapping({"/addresses/findByClientId", "/addresses/findByClientId/"})
    private List<Address> getAddressesByAddressClientClientId(@RequestParam(value = "clientId", required = true) Long clientId){
        final List<Address> addressList = addressServiceInterface.findAddressesByAddressClientClientIdEquals(clientId);
        if(addressList.isEmpty()){
            throw new AddressNotFoundException("No Addresses found ! clientId="+clientId);
        } else{
            return addressList;
        }
    }
    @GetMapping({"/addresses/findByNumberAndStreetAndCity", "/addresses/findByNumberAndStreetAndCity/"})
    private List<Address> getAddressByAddressNumberAndAddressStreetAndAddressCity(@RequestParam(value = "addressNumber", required = false) Integer addressNumber,
                                                                            @RequestParam(value = "addressStreet", required = false) String addressStreet,
                                                                            @RequestParam(value = "addressCity", required = false) String addressCity){
        List<Address> addressList = addressServiceInterface.
                findAddressByAddressNumberEqualsAndAddressStreetLikeAndAddressCityLikeAllIgnoreCaseOrderByAddressIdAsc(addressNumber,addressStreet,addressCity);
        if(addressList.isEmpty()){
            throw new AddressNotFoundException("No Address found ! addressNumber="+addressNumber+" addressStreet="+addressStreet+" addressCity: "+addressCity);
        } else{
            return addressList;
        }
    }
    @GetMapping({"/addresses/findByCityOrState", "/addresses/findByCityOrState/"})
    private List<Address> getAddressByAddressCityOrAddressState(@RequestParam(value = "addressCity", required = false) String addressCity,
                                                                @RequestParam(value = "addressState", required = false) String addressState){
        List<Address> addressList = addressServiceInterface.findAddressByAddressCityLikeOrAddressStateLikeAllIgnoreCaseOrderByAddressIdAsc(addressCity,addressState);
        if(addressList.isEmpty()){
            throw new AddressNotFoundException("No Address found ! addressCity="+addressCity+" addressState="+addressState);
        } else{
            return addressList;
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
        addressServiceInterface.findAddressById(addressId).
                orElseThrow(()->new AddressNotFoundException("Address not found ! Cannot be deleted ! addressId="+addressId));
        addressServiceInterface.deleteAddressById(addressId);
        throw new AddressSuccessException("Address deleted ! addressId="+addressId);
    }
    //@RequestBody set as "required = false" only to avoid default 500 exception and display the custom exception instead. A payload is required !
    @PutMapping({"/addresses/{addressId}", "/addresses/{addressId}/"})
    private Address updateAddress(@PathVariable("addressId") Long addressId, @RequestBody(required = false) Address addressDTO){
        final Address persistentAddress = addressServiceInterface.findAddressById(addressId).
                orElseThrow(()->new AddressNotFoundException("Address not found ! Cannot be updated ! addressId="+addressId));
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
