package com.trilha.back.finacys.entity;

import com.trilha.back.finacys.request.CategoriaRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CATEGORIA")
@Data
@EqualsAndHashCode
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;


    @Column(name = "CATEGORIA_NOME")
    private String name;


    @Column(name = "CATEGORIA_DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lancamento> lancamento;


    public Categoria(Long id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Categoria() {
        super();
    }


    public Categoria(CategoriaRequest categoriaRequest) {
        super();
        this.name = categoriaRequest.getName();
        this.description = categoriaRequest.getDescription();
    }
}
