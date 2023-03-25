package com.danielpm1982.springboot3clientmng.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Table(name = "CLIENT")
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID")
    private Long clientId;
    @Column(name = "CLIENT_NAME")
    private String clientName;
    @Column(name = "CLIENT_EMAIL")
    private String clientEmail;
    @OneToMany(mappedBy = "addressClient", targetEntity = Address.class, fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Address> clientAddressList;
}
