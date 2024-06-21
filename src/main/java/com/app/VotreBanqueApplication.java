package com.app;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.dao.ClientRepository;
import com.app.dao.CompteRepository;
import com.app.dao.OperationRepository;
import com.app.entities.Client;
import com.app.entities.Compte;
import com.app.entities.CompteCourant;
import com.app.entities.CompteEpargne;
import com.app.entities.Retrait;
import com.app.entities.Versement;
import com.app.service.IBanqueService;

@SpringBootApplication
public class VotreBanqueApplication implements CommandLineRunner{
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private IBanqueService banqueMetier;
	public static void main(String[] args) {
		SpringApplication.run(VotreBanqueApplication.class, args);
		
	}
	
	@Override
	public void run(String... args) throws Exception {
		/*Client c1 = clientRepository.save(new Client("Koami", "koami@Gmail.com"));
		Client c2 = clientRepository.save(new Client("Junior", "junior@Gmail.com"));
		
		Compte cp1 = compteRepository.save(new CompteCourant("c1", new Date(), 90000, c1, 6000));
		Compte cp2 = compteRepository.save(new CompteEpargne("c2", new Date(), 6000, c2, 5.5));
		
		operationRepository.save(new Versement(new Date(), 9000, cp1));
		operationRepository.save(new Versement(new Date(), 6000, cp1));
		operationRepository.save(new Versement(new Date(), 2300, cp1));
		operationRepository.save(new Retrait(new Date(), 9000, cp1));
		
		operationRepository.save(new Versement(new Date(), 2300, cp2));
		operationRepository.save(new Versement(new Date(), 400, cp2));
		operationRepository.save(new Versement(new Date(), 2300, cp2));
		operationRepository.save(new Retrait(new Date(), 3000, cp2));
		
		banqueMetier.verser("c1", 1111111);*/
	}
	
	

}
