package com.attornatus.api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Entity
@Table(name = "endereco")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logradouro")
    private String street;

    private String cep;
    @Column(name = "numero")
    private String number;
    @Column(name = "cidade")
    private String city;
    private boolean isPrincipal;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

}