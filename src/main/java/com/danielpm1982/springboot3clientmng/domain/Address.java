package com.danielpm1982.springboot3clientmng.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADDRESS")
@Data @NoArgsConstructor @AllArgsConstructor
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
}
