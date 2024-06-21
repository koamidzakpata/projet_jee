package com.app.entities;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("R")
public class Retrait extends Operation {

    public Retrait() {
        // Appel au constructeur par défaut de la classe Operation
        super();
    }

    public Retrait(Date dateOperation, double montant, Compte compte) {
        // Appel au constructeur avec paramètres de la classe Operation
        super(dateOperation, montant, compte);
    }
    
    // Vous pouvez ajouter des méthodes spécifiques aux retraits si nécessaire
}
