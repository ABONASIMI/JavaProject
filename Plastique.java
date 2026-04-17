public class Plastique extends RessourceMarine {

    public Plastique(int quantite) {
        super("Plastique", quantite);
    }

    @Override
    public void evoluer() {
        // rien
    }

    @Override
    public String toString() {
        return "Plastique : quantite = " + getQuantite();
    }
}
