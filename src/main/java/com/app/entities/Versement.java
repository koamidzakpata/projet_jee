package com.app.entities;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("V")
public class Versement extends Operation {

    public Versement() {
        // Appel au constructeur par défaut de la classe Operation
        super();
    }

    public Versement(Date dateOperation, double montant, Compte compte) {
        // Appel au constructeur avec paramètres de la classe Operation
        super(dateOperation, montant, compte);
    }
    
    // Vous pouvez ajouter des méthodes spécifiques aux versements si nécessaire
}
