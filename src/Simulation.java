/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                           File: Simulation.java                            */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe principale qui gere toute la simulation.
 */
public class Simulation {
    private static final int LARGEUR_AFFICHAGE = 3;

    private Terrain terrain;
    private ArrayList<Plankton> planktons;
    private ArrayList<Ressource> recifs;
    private ArrayList<Sardine> sardines;
    private ArrayList<Tuna> tunas;
    private FishingShip fishingShip;
    private final SimulationConfig config;
    private final Random random;
    private int etapeCourante;

    public Simulation() {
        this.config = SimulationConfig.getInstance();
        this.random = new Random();
        this.etapeCourante = 0;
    }

    public void initialiser() throws SimulationException {
        creerTerrain();
        initialiserCollections();
        initialiserRessources();
        initialiserAgents();

        SimulationLogger.separateur();
        SimulationLogger.log("Simulation initialisee.");
        afficherEtat();
    }

    private void creerTerrain() throws SimulationException {
        try {
            terrain = new Terrain(config.getNbLignes(), config.getNbColonnes());
        } catch (RuntimeException e) {
            throw new SimulationException("Impossible de creer le terrain.", e);
        }
    }

    private void initialiserCollections() {
        planktons = new ArrayList<Plankton>();
        recifs = new ArrayList<Ressource>();
        sardines = new ArrayList<Sardine>();
        tunas = new ArrayList<Tuna>();
    }

    private void initialiserRessources() throws SimulationException {
        for (int i = 1; i <= config.getNombrePlanktons(); i++) {
            Plankton p = new Plankton(config.getQuantiteInitialePlankton(), config.getQuantiteMaxPlankton());
            placerRessourceAleatoire(p);
            planktons.add(p);
        }

        for (int i = 1; i <= config.getNombreRecifs(); i++) {
            Ressource recif = new Ressource("Recif", 1);
            placerRessourceAleatoire(recif);
            recifs.add(recif);
        }
    }

    private void initialiserAgents() throws SimulationException {
        for (int i = 1; i <= config.getNombreSardines(); i++) {
            Sardine s = new Sardine("Sardine-" + i, terrain);
            placerAgentAleatoire(s);
            sardines.add(s);
        }

        for (int i = 1; i <= config.getNombreTunas(); i++) {
            Tuna t = new Tuna("Tuna-" + i, terrain);
            placerAgentAleatoire(t);
            tunas.add(t);
        }

        fishingShip = new FishingShip("Ship-1", terrain);
        placerAgentAleatoire(fishingShip);
    }

    private void placerRessourceAleatoire(Ressource ressource) throws SimulationException {
        int maxEssais = terrain.nbLignes * terrain.nbColonnes * 3;

        for (int essai = 0; essai < maxEssais; essai++) {
            int lig = 1 + random.nextInt(terrain.nbLignes);
            int col = 1 + random.nextInt(terrain.nbColonnes);

            if (terrain.caseEstVide(lig, col)) {
                boolean ok = terrain.setCase(lig, col, ressource);
                if (ok) {
                    return;
                }
            }
        }
        throw new SimulationException("Impossible de placer " + ressource.type + " : terrain trop rempli.");
    }

    private void placerAgentAleatoire(Agent agent) throws SimulationException {
        int maxEssais = terrain.nbLignes * terrain.nbColonnes * 3;

        for (int essai = 0; essai < maxEssais; essai++) {
            int lig = 1 + random.nextInt(terrain.nbLignes);
            int col = 1 + random.nextInt(terrain.nbColonnes);

            if (terrain.sontValides(lig, col) && !caseOccupeeParAgent(lig, col)) {
                agent.setPosition(lig, col);
                return;
            }
        }
        throw new SimulationException("Impossible de placer l'agent " + agent.getNom() + ".");
    }

    private boolean caseOccupeeParAgent(int ligne, int colonne) {
        for (Sardine s : sardines) {
            if (s != null && s.estActif() && s.getLigne() == ligne && s.getColonne() == colonne) {
                return true;
            }
        }
        for (Tuna t : tunas) {
            if (t != null && t.estActif() && t.getLigne() == ligne && t.getColonne() == colonne) {
                return true;
            }
        }
        return fishingShip != null && fishingShip.estActif()
            && fishingShip.getLigne() == ligne && fishingShip.getColonne() == colonne;
    }

    public void executerUneEtape() throws SimulationException {
        etapeCourante++;

        SimulationLogger.separateur();
        SimulationLogger.log("ETAPE " + etapeCourante);

        faireGrandirPlanktons();
        faireAgirSardines();
        faireAgirTunas();
        faireAgirBateau();

        afficherEtat();
    }

    public void lancer() throws SimulationException {
        SimulationLogger.separateur();
        SimulationLogger.log("Debut de la simulation marine");

        for (int i = 0; i < config.getNombreEtapesMax(); i++) {
            if (tunas.isEmpty()) {
                SimulationLogger.log("Arret: il n'y a plus de thons.");
                break;
            }
            executerUneEtape();
        }

        SimulationLogger.separateur();
        SimulationLogger.log("Fin de la simulation.");
        SimulationLogger.log("Prises du bateau : " + fishingShip.getNbPrises());
    }

    private void faireGrandirPlanktons() {
        int nbQuiGrandissent = 0;
        for (Plankton p : planktons) {
            if (p.getLigne() != -1 && p.getColonne() != -1) {
                int avant = p.getQuantite();
                p.grandir();
                if (p.getQuantite() > avant) {
                    nbQuiGrandissent++;
                }
            }
        }
        SimulationLogger.log("Plancton: " + nbQuiGrandissent + " ressource(s) ont grandi.");
    }

    private void faireAgirSardines() throws SimulationException {
        if (sardines.isEmpty()) {
            SimulationLogger.log("Aucune sardine.");
            return;
        }
        ArrayList<Sardine> copie = new ArrayList<Sardine>(sardines);
        for (Sardine s : copie) {
            SimulationLogger.log(s.agir(this));
        }
        sardines.removeIf(s -> !s.estActif());
    }

    private void faireAgirTunas() throws SimulationException {
        if (tunas.isEmpty()) {
            SimulationLogger.log("Aucun thon.");
            return;
        }
        ArrayList<Tuna> copie = new ArrayList<Tuna>(tunas);
        for (Tuna t : copie) {
            SimulationLogger.log(t.agir(this));
        }
        tunas.removeIf(t -> !t.estActif());
    }

    private void faireAgirBateau() throws SimulationException {
        SimulationLogger.log(fishingShip.agir(this));
    }

    private void afficherEtat() {
        afficherResume();
        afficherPositionsAgents();
        afficherTerrainComplet();
    }

    private void afficherResume() {
        SimulationLogger.log(
            "Resume: planctons=" + planktons.size() +
            ", recifs=" + recifs.size() +
            ", sardines=" + sardines.size() +
            ", tunas=" + tunas.size()
        );
    }

    private void afficherPositionsAgents() {
        for (Sardine s : sardines) {
            SimulationLogger.log(s.getNom() + " en (" + s.getLigne() + "," + s.getColonne() + ")");
        }
        for (Tuna t : tunas) {
            SimulationLogger.log(t.getNom() + " en (" + t.getLigne() + "," + t.getColonne() + ")");
        }
        if (fishingShip != null) {
            SimulationLogger.log(fishingShip.getNom() + " en (" + fishingShip.getLigne() + "," + fishingShip.getColonne() + ")");
        }
    }

    private void afficherTerrainComplet() {
        String[][] vue = construireVueBase();
        poserSardines(vue);
        poserTunas(vue);
        poserSymboleAgent(vue, fishingShip, "Shi");
        imprimerVue(vue, terrain.nbLignes, terrain.nbColonnes);
    }

    private String[][] construireVueBase() {
        String[][] vue = new String[terrain.nbLignes][terrain.nbColonnes];

        for (int lig = 1; lig <= terrain.nbLignes; lig++) {
            for (int col = 1; col <= terrain.nbColonnes; col++) {
                Ressource ressource = terrain.getCase(lig, col);
                if (ressource == null) {
                    vue[lig - 1][col - 1] = "";
                } else {
                    vue[lig - 1][col - 1] = symboleRessource(ressource);
                }
            }
        }
        return vue;
    }

    private void poserSardines(String[][] vue) {
        for (Sardine s : sardines) {
            poserSymboleAgent(vue, s, "Sar");
        }
    }

    private void poserTunas(String[][] vue) {
        for (Tuna t : tunas) {
            poserSymboleAgent(vue, t, "Tun");
        }
    }

    private void poserSymboleAgent(String[][] vue, Agent agent, String symbole) {
        if (agent == null || !agent.estActif() || !agent.estPlace()) {
            return;
        }

        int lig = agent.getLigne();
        int col = agent.getColonne();
        if (!terrain.sontValides(lig, col)) {
            return;
        }

        vue[lig - 1][col - 1] = symbole;
    }

    private String symboleRessource(Ressource ressource) {
        String type = ressource.type;
        if ("Plancton".equalsIgnoreCase(type)) {
            return "Pla";
        }
        if ("Recif".equalsIgnoreCase(type)) {
            return "Rec";
        }
        return premiersCaracteres(type, 3);
    }

    private String premiersCaracteres(String texte, int tailleMax) {
        if (texte == null || texte.isEmpty()) {
            return "";
        }
        if (texte.length() <= tailleMax) {
            return texte;
        }
        return texte.substring(0, tailleMax);
    }

    private void imprimerVue(String[][] vue, int nbLignes, int nbColonnes) {
        int largeurCase = LARGEUR_AFFICHAGE;
        for (int lig = 0; lig < nbLignes; lig++) {
            for (int col = 0; col < nbColonnes; col++) {
                largeurCase = Math.max(largeurCase, vue[lig][col].length());
            }
        }

        String separateur = construireSeparateur(largeurCase, nbColonnes);
        StringBuilder affichage = new StringBuilder();
        affichage.append(separateur).append("\n");

        for (int lig = 0; lig < nbLignes; lig++) {
            for (int col = 0; col < nbColonnes; col++) {
                affichage.append("|");
                affichage.append(String.format("%-" + largeurCase + "s", vue[lig][col]));
            }
            affichage.append("|\n");
            affichage.append(separateur).append("\n");
        }

        System.out.print(affichage.toString());
    }

    private String construireSeparateur(int largeurCase, int nbColonnes) {
        StringBuilder tirets = new StringBuilder();
        for (int i = 0; i < largeurCase; i++) {
            tirets.append("-");
        }

        StringBuilder separateur = new StringBuilder(":");
        for (int col = 0; col < nbColonnes; col++) {
            separateur.append(tirets).append(":");
        }
        return separateur.toString();
    }

    public ArrayList<Plankton> getPlanktons() {
        return planktons;
    }

    public ArrayList<Sardine> getSardines() {
        return sardines;
    }

    public ArrayList<Tuna> getTunas() {
        return tunas;
    }
}
