package com.app.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.entities.Client;
import com.app.entities.Compte;
import com.app.entities.CompteCourant;
import com.app.entities.CompteEpargne;
import com.app.entities.Operation;
import com.app.service.IBanqueService;

@Controller
public class BanqueController {

	@Autowired
    private IBanqueService banqueService;

    @Autowired
    public BanqueController(IBanqueService banqueService) {
        this.banqueService = banqueService;
    }

    @RequestMapping("/operations")
    public String index() {
        return "comptes";
    }

    @RequestMapping("/consulterCompte")
    public String consulter(Model model, String codeCompte,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        try {
            Compte cp = banqueService.consulterCompte(codeCompte);
            Pageable pageable = PageRequest.of(page, size);
            Page<Operation> pageOperations = banqueService.listOperation(codeCompte, page, size);
            model.addAttribute("codeCompte", codeCompte);
            model.addAttribute("compte", cp);
            model.addAttribute("listOperations", pageOperations.getContent());

            int totalPages = pageOperations.getTotalPages();
            int[] pages = new int[Math.min(totalPages, 5)]; // Afficher jusqu'à 5 pages
            for (int i = 0; i < pages.length; i++) {
                pages[i] = i;
            }
            model.addAttribute("pages", pages);
        } catch (Exception e) {
            model.addAttribute("exception", e);
        }
        return "comptes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/saveOperation", method = RequestMethod.POST)
    public String saveOperation(Model model, String typeOperation,
            String codeCompte, double montant, String codeCompte2) {
        try {
            if ("VERS".equals(typeOperation)) {
                banqueService.verser(codeCompte, montant);
            } else if ("RET".equals(typeOperation)) {
                banqueService.retirer(codeCompte, montant);
            } else if ("VIR".equals(typeOperation)) {
                banqueService.virement(codeCompte, codeCompte2, montant);
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/consulterCompte?codeCompte=" + codeCompte + "&error=" + e.getMessage();
        }
        return "redirect:/consulterCompte?codeCompte=" + codeCompte;
    }

    @RequestMapping("/comptes")
    public String listerComptes(Model model) {
        try {
            List<Compte> comptes = banqueService.listeDesComptes();
            model.addAttribute("comptes", comptes);
        } catch (Exception e) {
            model.addAttribute("exception", e);
        }
        return "listeComptes";
    }

    @RequestMapping("/clients")
    public String listerClients(Model model) {
        try {
            List<Client> clients = banqueService.listeDesClients();
            model.addAttribute("clients", clients);
        } catch (Exception e) {
            model.addAttribute("exception", e);
        }
        return "listeClients";
    }
    
    // Mapping pour afficher le formulaire de création de client
    @GetMapping("/createClient")
    public String showCreateClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "createClient"; // Assurez-vous que "createClient.html" existe dans le dossier des vues
    }

    @PostMapping("/saveClient")
    public String saveClient(@ModelAttribute("client") Client client, Model model) {
        try {
            // Vérifier si un client avec le même nom existe déjà
            Client existingClient = banqueService.trouverClientParNom(client.getNom());
            if (existingClient != null) {
                throw new RuntimeException("Un client avec ce nom existe déjà.");
            }

            // Sauvegarder le client s'il n'existe pas encore
            banqueService.creerClient(client);
            return "redirect:/clients";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "createClient";
        }
    }
    
    @GetMapping("/createCompteCourant")
    public String showCreerCompteCourantForm(Model model) {
        model.addAttribute("compteCourant", new CompteCourant());
        List<Client> clients = banqueService.listeDesClients();
        model.addAttribute("clients", clients);
        return "createCompteCourant"; // Assurez-vous que "createCompteCourant.html" existe dans le dossier des vues
    }

    @PostMapping("/saveCompteCourant")
    public String saveCompteCourant(@ModelAttribute("compteCourant") CompteCourant compteCourant, Model model) {
        try {
            // Vous pouvez ajuster la création du compte courant avec les paramètres nécessaires
            CompteCourant nouveauCompteCourant = new CompteCourant(
                    compteCourant.getCodeCompte(),
                    new Date(), // Date de création (vous pouvez ajuster selon votre logique)
                    compteCourant.getSolde(),
                    compteCourant.getClient(),
                    compteCourant.getDecouvert()
            );
            banqueService.creerCompteCourant(nouveauCompteCourant.getCodeCompte(), nouveauCompteCourant.getDateCreation(), nouveauCompteCourant.getSolde(), nouveauCompteCourant.getClient(), nouveauCompteCourant.getDecouvert());
            return "redirect:/comptes"; // Redirection vers la liste des comptes
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Client> clients = banqueService.listeDesClients();
            model.addAttribute("clients", clients);
            return "createCompteCourant";
        }
    }

    @GetMapping("/createCompteEpargne")
    public String showCreerCompteEpargneForm(Model model) {
        model.addAttribute("compteEpargne", new CompteEpargne());
        List<Client> clients = banqueService.listeDesClients();
        model.addAttribute("clients", clients);
        return "createCompteEpargne"; // Assurez-vous que "createCompteEpargne.html" existe dans le dossier des vues
    }

    @PostMapping("/saveCompteEpargne")
    public String saveCompteEpargne(@ModelAttribute("compteEpargne") CompteEpargne compteEpargne, Model model) {
        try {
            // Vous pouvez ajuster la création du compte épargne avec les paramètres nécessaires
            CompteEpargne nouveauCompteEpargne = new CompteEpargne(
                    compteEpargne.getCodeCompte(),
                    new Date(), // Date de création (vous pouvez ajuster selon votre logique)
                    compteEpargne.getSolde(),
                    compteEpargne.getClient(),
                    compteEpargne.getTaux()
            );
            banqueService.creerCompteEpargne(nouveauCompteEpargne.getCodeCompte(), nouveauCompteEpargne.getDateCreation(), nouveauCompteEpargne.getSolde(), nouveauCompteEpargne.getClient(), nouveauCompteEpargne.getTaux());
            return "redirect:/comptes"; // Redirection vers la liste des comptes
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Client> clients = banqueService.listeDesClients();
            model.addAttribute("clients", clients);
            return "createCompteEpargne";
        }
    }

}
