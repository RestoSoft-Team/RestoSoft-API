package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "configuracion_paginas_clientes", schema = "restosoft_v2")
public class ConfiguracionPaginaCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpagina", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "titulo", length = 100)
    private String titulo;

    @Size(max = 255)
    @Column(name = "lema")
    private String lema;

    @Size(max = 500)
    @Column(name = "logo", length = 500)
    private String logo;

    @Size(max = 255)
    @Column(name = "direccion")
    private String direccion;

    @Size(max = 600)
    @Column(name = "horarios", length = 600)
    private String horarios;

    @Size(max = 255)
    @Column(name = "telefonos")
    private String telefonos;

    @Size(max = 500)
    @Column(name = "imagen_1", length = 500)
    private String imagen1;

    @Size(max = 500)
    @Column(name = "imagen_2", length = 500)
    private String imagen2;

    @Size(max = 100)
    @Column(name = "diseno_card", length = 100)
    private String disenoCard;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa idempresa;


}