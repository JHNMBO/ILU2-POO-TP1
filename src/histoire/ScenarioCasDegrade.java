package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois acheteur = new Gaulois("Testeur1", 1);
		etal.libererEtal();
		try {
			etal.acheterProduit(0, acheteur);
		}
		catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Fin du test");
		}
}
