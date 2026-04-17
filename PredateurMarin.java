public abstract class PredateurMarin extends AgentMarin {
    private int puissance;

    public PredateurMarin(int puissance, Terrain terrain, int lig, int col) {
        super(terrain, lig, col);
        this.puissance = puissance;
    }

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    @Override
    public String toString() {
        return "PredateurMarin : lig = " + getLig()
                + ", col = " + getCol()
                + ", puissance = " + puissance;
    }
}
