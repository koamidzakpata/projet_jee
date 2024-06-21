package com.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.app.entities.Compte;
import com.app.entities.CompteCourant;
import com.app.entities.CompteEpargne;
import com.app.entities.Operation;
import com.app.entities.Client;

public interface IBanqueService {
    public Compte consulterCompte(String codeCpte);
    public void verser(String codeCpte, double montant);
    public void retirer(String codeCpte, double montant);
    public void virement(String codeCpte1, String codeCpte2, double montant);
    public Page<Operation> listOperation(String codeCpte, int page, int size);
    public List<Compte> listeDesComptes();
    public List<Client> listeDesClients();
    public void creerOperation(Operation operation);
	public void creerClient(Client client);
	public Client trouverClientParNom(String nom);
	public void creerCompteCourant(String codeCompte, Date dateCreation, double solde, Client client, double decouvert);
	public void creerCompteEpargne(String codeCompte, Date dateCreation, double solde, Client client, double taux);
	/*void creerCompteCourant(CompteCourant compteCourant);
    void creerCompteEpargne(CompteEpargne compteEpargne);*/
}
