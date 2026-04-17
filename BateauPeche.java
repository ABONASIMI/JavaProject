public class BateauPeche extends Bateau {

    public BateauPeche(int capacite, Terrain terrain, int lig, int col) {
        super(capacite, terrain, lig, col);
    }

    @Override
    public void agir() {
        Ressource r = lireRessourceCase(getLig(), getCol());
        if (r instanceof PetitPoisson) {
            pecher((PetitPoisson) r);
        }
    }

    @Override
    public String toString() {
        return "BateauPeche : lig = " + getLig()
                + ", col = " + getCol()
                + ", capacite = " + getCapacite();
    }
}
