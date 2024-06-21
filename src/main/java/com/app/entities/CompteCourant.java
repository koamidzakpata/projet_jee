package com.app.entities;

import java.util.Date;
import java.util.Objects;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("CC")
public class CompteCourant extends Compte {
	
    @NotNull
    private double decouvert;

    public CompteCourant() {
        super();
    }

    public CompteCourant(String codeCompte, Date dateCreation, double solde, Client client, double decouvert) {
        super(codeCompte, dateCreation, solde, client);
        this.decouvert = decouvert;
    }

    public double getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(double decouvert) {
        this.decouvert = decouvert;
    }

    @Override
    public String toString() {
        return "CompteCourant{" +
               "codeCompte='" + getCodeCompte() + '\'' +
               ", dateCreation=" + getDateCreation() +
               ", solde=" + getSolde() +
               ", client=" + getClient() +
               ", decouvert=" + decouvert +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompteCourant that = (CompteCourant) o;
        return Double.compare(that.decouvert, decouvert) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), decouvert);
    }
}
