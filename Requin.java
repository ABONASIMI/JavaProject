public class Requin extends PredateurMarin {

    public Requin(int puissance, Terrain terrain, int lig, int col) {
        super(puissance, terrain, lig, col);
    }

    public void manger(PetitPoisson p) {
        if (p != null) {
            p.perdrePoissons(getPuissance());
        }
    }

    @Override
    public void agir() {
        Ressource r = lireRessourceCase(getLig(), getCol());
        if (r instanceof PetitPoisson) {
            manger((PetitPoisson) r);
        }
    }

    @Override
    public String toString() {
        return "Requin : lig = " + getLig()
                + ", col = " + getCol()
                + ", puissance = " + getPuissance();
    }
}
