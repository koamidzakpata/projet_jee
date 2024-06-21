package com.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.ClientRepository;
import com.app.dao.CompteRepository;
import com.app.dao.OperationRepository;
import com.app.entities.Client;
import com.app.entities.Compte;
import com.app.entities.CompteCourant;
import com.app.entities.CompteEpargne;
import com.app.entities.Operation;
import com.app.entities.Retrait;
import com.app.entities.Versement;
import com.app.entities.compteCourantRepository;

@Service
@Transactional
public class BanqueServiceImpl implements IBanqueService {

	@Autowired
    private ClientRepository clientRepository;
	@Autowired
    private CompteRepository compteRepository;
	@Autowired
    private OperationRepository operationRepository;

    @Autowired
    public BanqueServiceImpl(ClientRepository clientRepository, CompteRepository compteRepository,
            OperationRepository operationRepository) {
        this.clientRepository = clientRepository;
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public Compte consulterCompte(String codeCpte) {
        Optional<Compte> optionalCompte = compteRepository.findById(codeCpte);
        return optionalCompte.orElseThrow(() -> new RuntimeException("Compte introuvable"));
    }

    @Override
    public void verser(String codeCpte, double montant) {
        Compte cp = consulterCompte(codeCpte);
        Versement v = new Versement(new Date(), montant, cp);
        operationRepository.save(v);
        cp.setSolde(cp.getSolde() + montant);
        // Pas besoin de sauvegarder explicitement le compte ici
    }

    @Override
    public void retirer(String codeCpte, double montant) {
        Compte cp = consulterCompte(codeCpte);
        double facilitesCaisse = 0;
        if (cp instanceof CompteCourant) {
            facilitesCaisse = ((CompteCourant) cp).getDecouvert();
        }
        if (cp.getSolde() + facilitesCaisse < montant)
            throw new RuntimeException("Solde insuffisant");
        Retrait r = new Retrait(new Date(), montant, cp);
        operationRepository.save(r);
        cp.setSolde(cp.getSolde() - montant);
        // Pas besoin de sauvegarder explicitement le compte ici
    }

    @Override
    public void virement(String codeCpte1, String codeCpte2, double montant) {
        if (codeCpte1.equals(codeCpte2))
            throw new RuntimeException("Impossible d'éffectuer virement sur le même compte");
        retirer(codeCpte1, montant);
        verser(codeCpte2, montant);
    }

    @Override
    public Page<Operation> listOperation(String codeCpte, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return operationRepository.listOperation(codeCpte, pageable);
    }

    @Override
    public List<Compte> listeDesComptes() {
        return compteRepository.findAll();
    }

    @Override
    public List<Client> listeDesClients() {
        return clientRepository.findAll();
    }

    @Override
    public void creerOperation(Operation operation) {
        operationRepository.save(operation);
    }

	@Override
	public void creerClient(Client client) {
		clientRepository.save(client);		
	}
	
	 @Override
	 public Client trouverClientParNom(String nom) {
	     return clientRepository.findByNom(nom);
	 }
	 
	 @Override
	    public void creerCompteCourant(String codeCompte, Date dateCreation, double solde, Client client, double decouvert) {
	        CompteCourant compteCourant = new CompteCourant();
	        compteCourant.setCodeCompte(codeCompte);
	        compteCourant.setDateCreation(dateCreation);
	        compteCourant.setSolde(solde);
	        compteCourant.setClient(client);
	        compteCourant.setDecouvert(decouvert);
	        compteRepository.save(compteCourant);
	    }

	    @Override
	    public void creerCompteEpargne(String codeCompte, Date dateCreation, double solde, Client client, double taux) {
	        CompteEpargne compteEpargne = new CompteEpargne();
	        compteEpargne.setCodeCompte(codeCompte);
	        compteEpargne.setDateCreation(dateCreation);
	        compteEpargne.setSolde(solde);
	        compteEpargne.setClient(client);
	        compteEpargne.setTaux(taux);
	        compteRepository.save(compteEpargne);
	    }
	    
	    /*@Override
	    public CompteCourant creerCompteCourant(String codeCompte, Date dateCreation, double solde, Client client, double decouvert) {
	        CompteCourant compteCourant = new CompteCourant(codeCompte, dateCreation, solde, client, decouvert);
	        return compteRepository.save(compteCourant);
	    }
	    
	    @Override
	    public CompteEpargne creerCompteEpargne(String codeCompte, Date dateCreation, double solde, Client client, double taux) {
	        CompteEpargne compteEpargne = new CompteEpargne(codeCompte, dateCreation, solde, client, taux);
	        return compteRepository.save(compteEpargne);
	    }*/
	
}