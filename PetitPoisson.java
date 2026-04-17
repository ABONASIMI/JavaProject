public class PetitPoisson extends RessourceMarine {

    public PetitPoisson(int quantite) {
        super("PetitPoisson", quantite);
    }

    public void perdrePoissons(int nb) {
        retirerQuantite(nb);
    }

    @Override
    public void evoluer() {
        if (getQuantite() < 3) {
            ajouterQuantite(2);
        } else if (Math.random() < 0.20) {
            ajouterQuantite(1);
        }
    }

    @Override
    public String toString() {
        return "PetitPoisson : quantite = " + getQuantite();
    }
}
