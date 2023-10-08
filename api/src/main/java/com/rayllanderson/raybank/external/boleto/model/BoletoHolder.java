package com.rayllanderson.raybank.external.boleto.model;

import com.rayllanderson.raybank.external.validator.CPFOrCNPJ;
import lombok.Getter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
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
