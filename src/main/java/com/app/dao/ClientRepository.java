package com.app.dao;

import com.app.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
	public Client findByNom(String nom);
}
