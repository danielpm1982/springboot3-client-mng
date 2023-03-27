package com.danielpm1982.springboot3clientmng.validator;
import com.danielpm1982.springboot3clientmng.domain.Address;
import com.danielpm1982.springboot3clientmng.error.InvalidPayloadException;
import java.util.List;

public class AddressDTOValidator {
    private static Address addressDTO;
    private static List<Address> addressListDTO;
    public static boolean validateAddressDTOForAddingAddress(Address addressDTO){
        AddressDTOValidator.addressDTO=addressDTO;
        if(!validateNotNull()||!validateNullIdProp()||!validateNotNullProps()||!validateNotEmptyProps()) {
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "containing all Address properties except addressId, which is automatically generated at the server side. Please, " +
                    "send another request with the proper payload.");
        }
        return true;
    }
    public static boolean validateAddressDTOForUpdatingAddress(Address addressDTO){
        AddressDTOValidator.addressDTO=addressDTO;
        if(!validateNotNull()||!validateNullIdProp()||!validateNotNullProps()||!validateNotEmptyProps()) {
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "containing all Address properties except addressId, which is automatically generated at the server side. Please, " +
                    "send another request with the proper payload.");
        }
        return true;
    }
    public static boolean validateAddressListDTOForSettingAddressListOnClient(List<Address> addressListDTO){
        AddressDTOValidator.addressListDTO=addressListDTO;
        if(!validateListNotNullOrEmpty()||!validateNullIdPropAtListFirstElement()||!validateNotNullPropsAtListFirstElement()||
                !validateNotEmptyPropsAtListFirstElement()) {
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "containing a list of Addresses, with at least one Address, as well as all Address properties, except addressId, " +
                    "which is automatically generated at the server side. Please, send another request with the proper payload. " +
                    "Address list not set to Client !");
        }
        return true;
    }
    private static boolean validateNotNull(){
        return addressDTO!=null;
    }
    private static boolean validateListNotNullOrEmpty(){
        return addressListDTO!=null&&!addressListDTO.isEmpty();
    }
    private static boolean validateNotNullProps(){
        return addressDTO.getAddressStreet()!=null&&
                addressDTO.getAddressNumber()>0&&
                addressDTO.getAddressCity()!=null&&
                addressDTO.getAddressState()!=null&&
                addressDTO.getAddressCountry()!=null&&
                addressDTO.getAddressZipCode()!=null;
    }
    private static boolean validateNotNullPropsAtListFirstElement(){
        return !(addressListDTO.get(0).getAddressStreet()==null)&&
                addressListDTO.get(0).getAddressNumber()>0&&
                !(addressListDTO.get(0).getAddressCity()==null)&&
                !(addressListDTO.get(0).getAddressState()==null)&&
                !(addressListDTO.get(0).getAddressCountry()==null)&&
                !(addressListDTO.get(0).getAddressZipCode()==null);
    }
    private static boolean validateNotEmptyProps(){
        return !addressDTO.getAddressStreet().equals("")&&
                addressDTO.getAddressNumber()>0&&
                !addressDTO.getAddressCity().equals("")&&
                !addressDTO.getAddressState().equals("")&&
                !addressDTO.getAddressCountry().equals("")&&
                !addressDTO.getAddressZipCode().equals("");
    }
    private static boolean validateNotEmptyPropsAtListFirstElement(){
        return !addressListDTO.get(0).getAddressStreet().equals("")&&
                addressListDTO.get(0).getAddressNumber()>0&&
                !addressListDTO.get(0).getAddressCity().equals("")&&
                !addressListDTO.get(0).getAddressState().equals("")&&
                !addressListDTO.get(0).getAddressCountry().equals("");
    }
    private static boolean validateNullIdProp(){
        return addressDTO.getAddressId()==null;
    }
    private static boolean validateNullIdPropAtListFirstElement(){
        return !addressListDTO.stream().anyMatch(x->x.getAddressId()!=null);
    }
}
