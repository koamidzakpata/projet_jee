package com.app.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @NotNull
    @Size(min = 2, max = 50)
    private String nom;

    @NotNull
    @Email
    private String email;

    @OneToMany(mappedBy="client", fetch=FetchType.LAZY)
    private Collection<Compte> comptes;

    public Client() {
        super();
    }

    public Client(String nom, String email) {
        super();
        this.nom = nom;
        this.email = email;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(Collection<Compte> comptes) {
        this.comptes = comptes;
    }

    @Override
    public String toString() {
        return "Client{" +
               "code=" + code +
               ", nom='" + nom + '\'' +
               ", email='" + email + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(code, client.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}