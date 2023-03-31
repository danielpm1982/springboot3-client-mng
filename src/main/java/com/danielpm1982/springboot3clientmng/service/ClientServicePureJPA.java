package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.repository.ClientRepositoryPureJPAInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServicePureJPA implements ClientServiceInterface {
    private final ClientRepositoryPureJPAInterface clientRepositoryPureJPAInterface;
    public ClientServicePureJPA(@Qualifier("clientRepositoryPureJPA") ClientRepositoryPureJPAInterface clientRepositoryPureJPAInterface) {
        this.clientRepositoryPureJPAInterface = clientRepositoryPureJPAInterface;
    }
    @Override
    public Client saveClient(Client client) {
        return clientRepositoryPureJPAInterface.save(client);
    }
    @Override
    public List<Client> findAllClients() {
        return clientRepositoryPureJPAInterface.findAll();
    }
    @Override
    public Page<Client> findAllClientsPagedAndOrderedAscBy(Integer pageNumber, Integer pageSize, String propertyToOrderBy){
        //pagNumber must be decremented as at SpringDataJPA it is implemented starting from 0, while the user would
        //start from 1, when asking for the first page
        Pageable pageable = PageRequest.of(--pageNumber, pageSize, Sort.by(propertyToOrderBy).ascending());
        return clientRepositoryPureJPAInterface.findAll(pageable);
    }
    @Override
    public Optional<Client> findClientById(Long id) {
        return clientRepositoryPureJPAInterface.findById(id);
    }
    @Override
    public boolean existsClientById(Long id) {
        return clientRepositoryPureJPAInterface.existsById(id);
    }
    @Override
    public Client updateClient(Client client) {
        return clientRepositoryPureJPAInterface.update(client);
    }
    @Override
    public void deleteClientById(Long id) {
        clientRepositoryPureJPAInterface.deleteById(id);
    }
    @Override
    public void deleteAllClients() {
        clientRepositoryPureJPAInterface.deleteAll();
    }
    @Override
    public void truncateDBTable() {
        clientRepositoryPureJPAInterface.truncateDBTable();
    }
    @Override
    public List<Client> findClientByClientEmailIgnoreCaseLikeOrderByClientNameAsc(String clientEmail){
        return clientRepositoryPureJPAInterface.findByClientEmailIgnoreCaseLikeOrderByClientNameAsc("%"+clientEmail+"%");
    }
    @Override
    public List<Client> findClientByClientNameIgnoreCaseLikeOrderByClientNameAsc(String clientName) {
        return clientRepositoryPureJPAInterface.findByClientNameIgnoreCaseLikeOrderByClientNameAsc("%"+clientName+"%");
    }
}
