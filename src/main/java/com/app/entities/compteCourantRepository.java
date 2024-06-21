package com.app.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface compteCourantRepository extends JpaRepository<CompteCourant, String> {
	List<CompteCourant> findByDecouvertGreaterThan(double decouvert);
}
