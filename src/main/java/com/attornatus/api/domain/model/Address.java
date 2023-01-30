package com.attornatus.api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
@Entity
@Table(name = "endereco")
public class Address {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(name = "logradouro")
    private String street;

    @NotBlank
    private String cep;

    @NotBlank
    @Size(max = 10)
    @Column(name = "numero")
    private String number;

    @NotBlank
    @Size(max = 60)
    @Column(name = "cidade")
    private String city;

    @NotNull
    private Boolean isPrincipal;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private Person person;

}