package com.danielpm1982.springboot3clientmng.service;
import com.danielpm1982.springboot3clientmng.domain.Client;
import com.danielpm1982.springboot3clientmng.repository.AddressRepositorySpringDataJPAInterface;
import com.danielpm1982.springboot3clientmng.repository.ClientRepositorySpringDataJPAInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceSpringDataJPA implements ClientServiceInterface {
    private final ClientRepositorySpringDataJPAInterface clientRepositorySpringDataJPAInterface;
    public ClientServiceSpringDataJPA(ClientRepositorySpringDataJPAInterface clientRepositorySpringDataJPAInterface, AddressRepositorySpringDataJPAInterface addressRepositorySpringDataJPAInterface) {
        this.clientRepositorySpringDataJPAInterface = clientRepositorySpringDataJPAInterface;
    }
    @Override
    public Client saveClient(Client client) {
        return clientRepositorySpringDataJPAInterface.save(client);
    }
    @Override
    public List<Client> findAllClients() {
        return clientRepositorySpringDataJPAInterface.findAll();
    }
    @Override
    public Page<Client> findAllClientsPagedAndOrderedAscBy(Integer pageNumber, Integer pageSize, String propertyToOrderBy){
        //pagNumber must be decremented as at SpringDataJPA it is implemented starting from 0, while the user would
        //start from 1, when asking for the first page
        Pageable pageable = PageRequest.of(--pageNumber, pageSize, Sort.by(propertyToOrderBy).ascending());
        return clientRepositorySpringDataJPAInterface.findAll(pageable);
    }
    @Override
    public Optional<Client> findClientById(Long id) {
        return clientRepositorySpringDataJPAInterface.findById(id);
    }
    @Override
    public boolean existsClientById(Long id) {
        return clientRepositorySpringDataJPAInterface.existsById(id);
    }
    @Override
    public Client updateClient(Client client) {
        return clientRepositorySpringDataJPAInterface.save(client);
    }
    @Override
    public void deleteClientById(Long id) {
        clientRepositorySpringDataJPAInterface.deleteById(id);
    }
    @Override
    public void deleteAllClients() {
        clientRepositorySpringDataJPAInterface.deleteAll();
    }
    @Override
    public void truncateDBTable() {
        clientRepositorySpringDataJPAInterface.truncateDBTable();
    }
    @Override
    public List<Client> findClientByClientEmailIgnoreCaseLikeOrderByClientNameAsc(String clientEmail){
        return clientRepositorySpringDataJPAInterface.findByClientEmailIgnoreCaseLikeOrderByClientNameAsc("%"+clientEmail+"%");
    }
    @Override
    public List<Client> findClientByClientNameIgnoreCaseLikeOrderByClientNameAsc(String clientName) {
        return clientRepositorySpringDataJPAInterface.findByClientNameIgnoreCaseLikeOrderByClientNameAsc("%"+clientName+"%");
    }
}
