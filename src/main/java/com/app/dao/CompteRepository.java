package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Compte;
import com.app.entities.CompteCourant;
import com.app.entities.CompteEpargne;

public interface CompteRepository extends JpaRepository<Compte, String> {
	CompteCourant save(CompteCourant compteCourant);
	CompteEpargne save(CompteEpargne compteEpargne);
}
