package histoire;

import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		etal.libererEtal();
		try {
			etal.acheterProduit(1, null);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Fin du test");
		}
	}
}
