package com.danielpm1982.springboot3clientmng.validator;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.error.InvalidPayloadException;

public class ClientDTOValidator {
    private static Client clientDTO;
    public static boolean validateClientDTOForAddingClient(Client clientDTO){
        ClientDTOValidator.clientDTO=clientDTO;
        if(!validateNotNull()||!validateNullIdProp()||!validateNotNullProps()||!validateNotEmptyProps()){
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "containing both clientName and clientEmail properties with non-empty values. The clientId property, " +
                    "on the other hand, must not be sent, as its value is generated once and only at the server side. Please, " +
                    "send another request with the proper payload.");
        } else if(!validateNullOrEmptyClientAddressList()) {
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "and must not contain the clientAddress property. Please, send another request with the proper payload.");
        }
        return true;
    }
    public static boolean validateClientDTOForUpdatingClient(Client clientDTO){
        ClientDTOValidator.clientDTO=clientDTO;
        if(!validateNotNull()||!validateNullIdProp()||!validateNotNullProps()||!validateNotEmptyProps()){
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "containing both clientName and clientEmail properties with non-empty values. The clientId property, " +
                    "on the other hand, must not be sent, as its value is generated once and only at the server side. " +
                    "The clientId primary key (PK) cannot be updated ! Please, send another request with the proper payload.");
        } else if(!validateNullOrEmptyClientAddressList()){
            throw new InvalidPayloadException("Invalid payload data ! A valid JSON file must be sent, at the request body, " +
                    "and must not contain the clientAddress property. Please, send another request with the proper payload.");
        }
        return true;
    }
    private static boolean validateNotNull(){
        return clientDTO!=null;
    }
    private static boolean validateNotNullProps(){
        return clientDTO.getClientName()!=null&&clientDTO.getClientEmail()!=null;
    }
    private static boolean validateNotEmptyProps(){
        return !clientDTO.getClientName().equals("")&&!clientDTO.getClientEmail().equals("");
    }
    private static boolean validateNullIdProp(){
        return clientDTO.getClientId()==null;
    }
    private static boolean validateNullOrEmptyClientAddressList(){
        return clientDTO.getClientAddressList()==null||clientDTO.getClientAddressList().isEmpty();
    }
}
