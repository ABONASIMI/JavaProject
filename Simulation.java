import java.util.ArrayList;
import java.lang.reflect.Method;

public class Simulation {
    private Terrain terrain;
    private ArrayList<AgentMarin> agents;
    private ArrayList<RessourceMarine> ressources;
    private int tour;
    private int nbLig;
    private int nbCol;

    public Simulation(int nbLig, int nbCol, int nbPoissons, int nbPlastiques, int nbRequins, int nbBateaux) {
        this.nbLig = nbLig;
        this.nbCol = nbCol;
        this.terrain = new Terrain(nbLig, nbCol);
        this.agents = new ArrayList<AgentMarin>();
        this.ressources = new ArrayList<RessourceMarine>();
        this.tour = 0;

        initialiserRessources(nbPoissons, nbPlastiques);
        initialiserAgents(nbRequins, nbBateaux);
    }

    public int getTour() {
        return tour;
    }

    public ArrayList<AgentMarin> getAgents() {
        return agents;
    }

    public int getNbLig() {
        return nbLig;
    }

    public int getNbCol() {
        return nbCol;
    }

    public Ressource getCase(int lig, int col) {
        return lireCase(lig, col);
    }

    private Ressource lireCase(int lig, int col) {
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
            throw new RuntimeException("Erreur lecture Terrain", e);
        }
        return null;
    }

    private void ecrireCase(int lig, int col, Ressource r) {
        try {
            for (Method m : terrain.getClass().getMethods()) {
                Class<?>[] p = m.getParameterTypes();
                if (p.length == 3) {
                    boolean ordre1 = p[0] == int.class && p[1] == int.class && Ressource.class.isAssignableFrom(p[2]);
                    boolean ordre2 = Ressource.class.isAssignableFrom(p[0]) && p[1] == int.class && p[2] == int.class;

                    if (ordre1) {
                        m.invoke(terrain, lig, col, r);
                        return;
                    }
                    if (ordre2) {
                        m.invoke(terrain, r, lig, col);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur ecriture Terrain", e);
        }
    }

    private void initialiserRessources(int nbPoissons, int nbPlastiques) {
        for (int i = 0; i < nbPoissons; i++) {
            PetitPoisson p = new PetitPoisson((int) (Math.random() * 5) + 1); // 1 a 5
            placerRessourceAleatoire(p);
        }

        for (int i = 0; i < nbPlastiques; i++) {
            Plastique p = new Plastique((int) (Math.random() * 3) + 1); // 1 a 3
            placerRessourceAleatoire(p);
        }
    }

    private void initialiserAgents(int nbRequins, int nbBateaux) {
        for (int i = 0; i < nbRequins; i++) {
            int lig = (int) (Math.random() * nbLig) + 1;
            int col = (int) (Math.random() * nbCol) + 1;
            agents.add(new Requin(2, terrain, lig, col)); // mange au max 2
        }

        for (int i = 0; i < nbBateaux; i++) {
            int lig = (int) (Math.random() * nbLig) + 1;
            int col = (int) (Math.random() * nbCol) + 1;
            agents.add(new BateauPeche(2, terrain, lig, col)); // peche au max 2
        }
    }

    private void placerRessourceAleatoire(RessourceMarine r) {
        boolean placee = false;

        while (!placee) {
            int lig = (int) (Math.random() * nbLig) + 1;
            int col = (int) (Math.random() * nbCol) + 1;

            if (lireCase(lig, col) == null) {
                ecrireCase(lig, col, r);
                ressources.add(r);
                placee = true;
            }
        }
    }

    public void etape() {
        tour++;
        System.out.println("===== TOUR " + tour + " =====");

        deplacerAgentsAleatoirement();

        for (AgentMarin a : agents) {
            a.agir();
            System.out.println(a);
        }

        mettreAJourRessources();
        nettoyerRessourcesVides();

        System.out.println("Poissons restants : " + compterPoissons());
        System.out.println("Plastiques restants : " + compterPlastiques());
        System.out.println();
    }

    private void deplacerAgentsAleatoirement() {
        for (AgentMarin a : agents) {
            int nouvLig = a.getLig() + (int) (Math.random() * 3) - 1;
            int nouvCol = a.getCol() + (int) (Math.random() * 3) - 1;

            if (nouvLig < 1) nouvLig = 1;
            if (nouvLig > nbLig) nouvLig = nbLig;
            if (nouvCol < 1) nouvCol = 1;
            if (nouvCol > nbCol) nouvCol = nbCol;

            a.seDeplacer(nouvLig, nouvCol);
        }
    }

    private void mettreAJourRessources() {
        for (int i = 1; i <= nbLig; i++) {
            for (int j = 1; j <= nbCol; j++) {
                Ressource r = lireCase(i, j);
                if (r instanceof RessourceMarine) {
                    ((RessourceMarine) r).evoluer();
                }
            }
        }
    }

    private void nettoyerRessourcesVides() {
        ArrayList<RessourceMarine> nouvelles = new ArrayList<RessourceMarine>();

        for (int i = 1; i <= nbLig; i++) {
            for (int j = 1; j <= nbCol; j++) {
                Ressource r = lireCase(i, j);

                if (r instanceof RessourceMarine) {
                    RessourceMarine rm = (RessourceMarine) r;
                    if (rm.estVide()) {
                        ecrireCase(i, j, null);
                    } else {
                        nouvelles.add(rm);
                    }
                }
            }
        }

        ressources = nouvelles;
    }

    public int compterPoissons() {
        int total = 0;
        for (RessourceMarine r : ressources) {
            if (r instanceof PetitPoisson) {
                total += r.getQuantite();
            }
        }
        return total;
    }

    public int compterPlastiques() {
        int total = 0;
        for (RessourceMarine r : ressources) {
            if (r instanceof Plastique) {
                total += r.getQuantite();
            }
        }
        return total;
    }

    public boolean estFinie() {
        return compterPoissons() == 0;
    }

    public void lancer(int nbToursMax) {
        int i = 0;
        while (i < nbToursMax && !estFinie()) {
            etape();
            i++;
        }

        System.out.println("===== FIN DE SIMULATION =====");
        System.out.println("Nombre de tours : " + tour);
        System.out.println("Poissons restants : " + compterPoissons());
        System.out.println("Plastiques restants : " + compterPlastiques());
    }
}
