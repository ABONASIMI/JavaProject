import java.lang.reflect.Method;

public abstract class AgentMarin {
    private Terrain terrain;
    private int lig;
    private int col;

    public AgentMarin(Terrain terrain, int lig, int col) {
        this.terrain = terrain;
        this.lig = lig;
        this.col = col;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public int getLig() {
        return lig;
    }

    public int getCol() {
        return col;
    }

    public void setLig(int lig) {
        this.lig = lig;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public double distance(int lig, int col) {
        int dLig = this.lig - lig;
        int dCol = this.col - col;
        return Math.sqrt(dLig * dLig + dCol * dCol);
    }

    public void seDeplacer(int lig, int col) {
        this.lig = lig;
        this.col = col;
    }

    protected Ressource lireRessourceCase(int lig, int col) {
        try {
            for (Method m : terrain.getClass().getMethods()) {
                Class<?>[] p = m.getParameterTypes();
                if (p.length == 2
                        && p[0] == int.class
                        && p[1] == int.class
                        && Ressource.class.isAssignableFrom(m.getReturnType())) {
                    return (Ressource) m.invoke(terrain, lig, col);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur acces Terrain", e);
        }
        return null;
    }

    public abstract void agir();
}
