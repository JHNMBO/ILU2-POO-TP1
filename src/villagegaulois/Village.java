package villagegaulois;

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
	
	private static class Marche {
		private Etal[] etals;
		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for(int i=0;i<nbEtals;i++) {
				etals[i] = new Etal();
			}
		}
		
		//string builder
		
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
			for (int i = 0; i < etalProduit.length; i++) {
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
			//StringBuilder Final = new StringBuilder();
			String Final = "";
			for (int i = 0; i < etals.length; i++) {
				Final += etals[i].afficherEtal();
				if (!(etals[i].isEtalOccupe())) {
					nbEtalVide++;
				}
			}
			if (nbEtalVide>0) {
				Final += "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
			} 
			return Final;
		}
	}
	
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
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public static void main(String[] args) {
		
	}
}