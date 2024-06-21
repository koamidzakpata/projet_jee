package com.app.entities;

import java.util.Date;
import java.util.Objects;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@DiscriminatorValue("CE")
public class CompteEpargne extends Compte {
	
    @NotNull
    @PositiveOrZero
    private double taux;

    public CompteEpargne() {
        super();
    }

    public CompteEpargne(String codeCompte, Date dateCreation, double solde, Client client, double taux) {
        super(codeCompte, dateCreation, solde, client);
        this.taux = taux;
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }

    @Override
    public String toString() {
        return "CompteEpargne{" +
               "codeCompte='" + getCodeCompte() + '\'' +
               ", dateCreation=" + getDateCreation() +
               ", solde=" + getSolde() +
               ", client=" + getClient() +
               ", taux=" + taux +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompteEpargne that = (CompteEpargne) o;
        return Double.compare(that.taux, taux) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), taux);
    }
}
