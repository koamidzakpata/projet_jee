package com.app.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface compteEpargneRepository extends JpaRepository<CompteEpargne, String> {
	List<CompteEpargne> findByTauxGreaterThan(double taux);
}
