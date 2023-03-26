package com.danielpm1982.springboot3clientmng.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ADDRESS")
//toString() shouldn't consider the addressClient field in order to avoid infinite looping
@Data @NoArgsConstructor @AllArgsConstructor @ToString(exclude = {"addressClient"})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long addressId;
    @Column(name = "ADDRESS_STREET")
    private String addressStreet;
    @Column(name = "ADDRESS_NUMBER")
    private int addressNumber;
    @Column(name = "ADDRESS_CITY")
    private String addressCity;
    @Column(name = "ADDRESS_STATE")
    private String addressState;
    @Column(name = "ADDRESS_COUNTRY")
    private String addressCountry;
    @Column(name = "ADDRESS_ZIP_CODE")
    private String addressZipCode;
    @JsonIgnore //this avoids infinite looping when generating the JSON responses
    @ManyToOne(targetEntity = Client.class, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "CLIENT_ID", nullable = true)
    private Client addressClient;
}
