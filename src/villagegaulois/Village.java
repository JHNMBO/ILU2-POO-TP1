package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche; // Instance de la classe interne

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
	}

// Creation de la classe interne

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!(etals[i].isEtalOccupe()))
					return i;
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int nbEtals = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtals++;
				}
			}
			Etal[] etalsTrouve = new Etal[nbEtals];
			int indiceEtalsT = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsTrouve[indiceEtalsT] = etals[i];
					indiceEtalsT++;
				}
			}
			return etalsTrouve;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois)
					return etals[i];
			}
			return null;
		}

		private String afficherMarche() {
			int nbEtalLibre = 0;
			StringBuilder text = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
					text.append(etals[i].afficherEtal());
				}
				nbEtalLibre++;
			}
			if (nbEtalLibre != 0) {
				System.out.println("Il reste " + nbEtalLibre + " �tals non utilis�s dans le march�.\n");
			}
			return text.toString();
		}

	} // Fin de la classe interne

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if(chef==null) {
			throw new VillageSansChefException("Le village n'a pas de chef\n");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur + " cherche un endroit ou vendre " + nbProduit + " " + produit + ".\n");
		int indiceEtal = marche.trouverEtalLibre();
		if (indiceEtal==-1) {
			chaine.append("Mais il n'y a plus de place.\n");
		}
		else {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur + " vend des " + produit + " � l'�tal n�" + indiceEtal + ".\n");
		}
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsTrouve = marche.trouverEtals(produit);
		if (etalsTrouve.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n");
		} else if (etalsTrouve.length == 1){
			chaine.append("Seul le vendeur " + etalsTrouve[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des" + produit + " sont : \n");
			for (int i = 0; i < etalsTrouve.length; i++) {
				chaine.append("- " + etalsTrouve[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = rechercherEtal(vendeur);
		return etalVendeur.libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + getNom() + "\" possède plusieurs étals :\n" + marche.afficherMarche());
		return chaine.toString();
	}
}