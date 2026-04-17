public abstract class RessourceMarine extends Ressource {
    private String type;
    private int quantite;

    public RessourceMarine(String type, int quantite) {
        super(type, quantite);
        this.type = type;
        this.quantite = quantite;
    }

    public String getType() {
        return type;
    }

    public int getQuantite() {
        return quantite;
    }

    public boolean estVide() {
        return quantite <= 0;
    }

    public void retirerQuantite(int q) {
        quantite -= q;
        if (quantite < 0) {
            quantite = 0;
        }
    }

    public void ajouterQuantite(int q) {
        quantite += q;
    }

    public abstract void evoluer();

    @Override
    public String toString() {
        return type + " : quantite = " + quantite;
    }
}
