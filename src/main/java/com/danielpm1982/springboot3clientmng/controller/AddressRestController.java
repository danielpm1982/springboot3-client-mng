package com.danielpm1982.springboot3clientmng.controller;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.error.*;
import com.danielpm1982.springboot3clientmng.service.AddressServiceInterface;
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
        Address address = addressServiceInterface.findAddressById(addressId);
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
    @PostMapping({"/addresses", "/addresses/"})
    //@RequestBody set as "required = false" only to avoid default 500 exception and display the custom exception instead. A payload is required !
    private ModelAndView addAddress(@RequestBody(required = false) Address addressDTO){
        if(addressDTO==null||addressDTO.getAddressStreet()==null||addressDTO.getAddressStreet().equals("")||
                addressDTO.getAddressNumber()<=0||addressDTO.getAddressCity()==null||addressDTO.getAddressCity().equals("")||
                addressDTO.getAddressState()==null||addressDTO.getAddressState().equals("")||addressDTO.getAddressCountry()==null||
                addressDTO.getAddressCountry().equals("")||addressDTO.getAddressZipCode()==null||addressDTO.getAddressCity().equals("")||
                addressDTO.getAddressId()!=null){
           throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                   "containing all Address properties except addressId, which is automatically generated at the server side. Please, " +
                   "send another request with the proper payload.");
        } else{
                addressServiceInterface.saveAddress(addressDTO);
                return new ModelAndView("redirect:/api/addresses");
        }
    }
    @DeleteMapping({"/addresses/{addressId}", "/addresses/{addressId}/"})
    private void deleteAddressById(@PathVariable("addressId") Long addressId){
        Address persistentAddress = addressServiceInterface.findAddressById(addressId);
        if(persistentAddress==null){
            throw new AddressNotFoundException("Address not found ! Cannot be deleted ! addressId="+addressId);
        } else{
            addressServiceInterface.deleteAddressById(addressId);
            throw new AddressSuccessException("Address deleted ! addressId="+addressId);
        }
    }
    @PutMapping({"/addresses/{addressId}", "/addresses/{addressId}/"})
    private Address updateAddress(@PathVariable("addressId") Long addressId, @RequestBody(required = true) Address addressDTO){
        final Address managedAddress = addressServiceInterface.findAddressById(addressId);
        if(managedAddress==null){
            throw new AddressNotFoundException("Address not found ! Cannot be updated ! addressId="+addressId);
        }
        if(addressDTO==null||addressDTO.getAddressStreet()==null||addressDTO.getAddressStreet().equals("")||
                addressDTO.getAddressNumber()<=0||addressDTO.getAddressCity()==null||addressDTO.getAddressCity().equals("")||
                addressDTO.getAddressState()==null||addressDTO.getAddressState().equals("")||addressDTO.getAddressCountry()==null||
                addressDTO.getAddressCountry().equals("")||addressDTO.getAddressZipCode()==null||addressDTO.getAddressCity().equals("")||
                addressDTO.getAddressId()!=null){
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "containing all Address properties except addressId, which is automatically generated at the server side. Please, " +
                    "send another request with the proper payload. The addressId is not updatable, all other properties are.");
        } else {
            managedAddress.setAddressStreet(addressDTO.getAddressStreet());
            managedAddress.setAddressNumber(addressDTO.getAddressNumber());
            managedAddress.setAddressCity(addressDTO.getAddressCity());
            managedAddress.setAddressState(addressDTO.getAddressState());
            managedAddress.setAddressCountry(addressDTO.getAddressCountry());
            managedAddress.setAddressZipCode(addressDTO.getAddressZipCode());
            Address mergedAddress = addressServiceInterface.updateAddress(managedAddress);
            return mergedAddress;
        }
    }
}
