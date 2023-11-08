package com.rayllanderson.raybank.external.boleto.model;

import com.rayllanderson.raybank.external.validator.CPFOrCNPJ;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
public class BoletoHolder {
    @Id
    @CPFOrCNPJ
    private String document;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "holder")
    private Set<Boleto> boletos = new HashSet<>();

    @Deprecated(since = "0.0.1")
    public BoletoHolder() {}

    public BoletoHolder(String name, String document) {
        this.name = name;
        this.document = document;
    }

    @Override
    public String toString() {
        return "BoletoHolder{" + "document='" + document + '\'' + ", name='" + name + '\'' + '}';
    }
}
