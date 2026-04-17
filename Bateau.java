public abstract class Bateau extends AgentMarin {
    private int capacite;

    public Bateau(int capacite, Terrain terrain, int lig, int col) {
        super(terrain, lig, col);
        this.capacite = capacite;
    }

    public int getCapacite() {
        return capacite;
    }

    public void pecher(PetitPoisson p) {
        if (p != null) {
            p.perdrePoissons(capacite);
        }
    }

    @Override
    public String toString() {
        return "Bateau : lig = " + getLig()
                + ", col = " + getCol()
                + ", capacite = " + capacite;
    }
}
