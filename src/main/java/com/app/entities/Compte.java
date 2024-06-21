package com.app.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_CPTE", discriminatorType = DiscriminatorType.STRING, length = 2)
public abstract class Compte implements Serializable {
	
    @Id
    @NotNull
    private String codeCompte;

    @NotNull
    @PastOrPresent
    private Date dateCreation;

    @NotNull
    private double solde;

    @ManyToOne
    @JoinColumn(name = "CODE_CLI")
    private Client client;

    @OneToMany(mappedBy = "compte")
    private Collection<Operation> operations;

    public Compte() {
        super();
    }

    public Compte(String codeCompte, Date dateCreation, double solde, Client client) {
        super();
        this.codeCompte = codeCompte;
        this.dateCreation = dateCreation;
        this.solde = solde;
        this.client = client;
    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Collection<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Collection<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return "Compte{" +
               "codeCompte='" + codeCompte + '\'' +
               ", dateCreation=" + dateCreation +
               ", solde=" + solde +
               ", client=" + client +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compte compte = (Compte) o;
        return Objects.equals(codeCompte, compte.codeCompte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeCompte);
    }
}