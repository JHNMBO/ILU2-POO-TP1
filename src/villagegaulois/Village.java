package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}
	
	private static class Marche { // Création classe interne Marche
		private Etal[] etals;
		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for(int i=0;i<nbEtals;i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtalProduit++;
				}
			}
			Etal[] etalProduit = new Etal[nbEtalProduit];
			nbEtalProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalProduit[nbEtalProduit] = etals[i];
					nbEtalProduit++;
				}
			}
			return etalProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			String nom;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder text = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				text.append(etals[i].afficherEtal());
				if (!(etals[i].isEtalOccupe())) {
					nbEtalVide++;
				}
			}
			if (nbEtalVide>0) {
				text.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché\n");
			} 
			return text.toString();
		}
		
	} //Fin Classe Interne
	
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

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		try {
			if(chef==null) {
				throw new VillageSansChefException("Ce village n'a pas de chef");
			}
			if (nbVillageois < 1) {
				chaine.append("Il n'y a encore aucun habitant au village du chef "
						+ chef.getNom() + ".\n");
			} else {
				chaine.append("Au village du chef " + chef.getNom()
						+ " vivent les légendaires gaulois :\n");
				for (int i = 0; i < nbVillageois; i++) {
					chaine.append("- " + villageois[i].getNom() + "\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		int etalLibre = marche.trouverEtalLibre();
		marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
		StringBuilder text = new StringBuilder();
		text.append(vendeur.getNom() + " cherche un endroit pour vendre ");
		text.append(nbProduit);
		text.append(" " + produit + "\nLe vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°");
		text.append(etalLibre+1);
		text.append("\n");
		return text.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] listeEtal = marche.trouverEtals(produit);
		StringBuilder text = new StringBuilder();
		if (listeEtal.length==0) {
			text.append("Il n'y a pas de vendeur qui propose des fleurs au marché\n");
		}
		else if (listeEtal.length==1) {
			text.append("Seul le vendeur " + listeEtal[0].getVendeur().getNom() + " propose des " + produit + " au marché\n");
		}
		else {
			text.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < listeEtal.length; i++) {
				text.append("- " + listeEtal[i].getVendeur().getNom() + "\n");
			}
		}
		return text.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = rechercherEtal(vendeur);
		return etalVendeur.libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder text = new StringBuilder();
		text.append("Le marché du village \"" + getNom() + "\" possède plusieurs étals :\n" + marche.afficherMarche());
		return text.toString();
	}
	
}