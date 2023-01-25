package com.attornatus.api.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pessoa")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nome")
    private String name;

    @Embedded
    @Column(name = "enderecos")
    @OneToMany(mappedBy = "person")
    private List<Address> addressList;

}
