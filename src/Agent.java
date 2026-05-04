/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                              File: Agent.java                              */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

/**
 * Classe de base pour tous les agents mobiles de la simulation.
 * On herite de Ressource pour reutiliser simplement la gestion de position (ligne, colonne).
 */
public abstract class Agent extends Ressource implements Movable {
    protected String nom;
    protected Terrain terrain;
    protected boolean actif;

    public Agent(String type, String nom, Terrain terrain) {
        super(type, 1);
        this.nom = nom;
        this.terrain = terrain;
        this.actif = true;
    }

    public String getNom() {
        return nom;
    }

    public boolean estActif() {
        return actif;
    }

    public void desactiver() {
        this.actif = false;
    }

    public int distanceA(int ligne, int colonne) {
        if (!estPlace()) {
            return Integer.MAX_VALUE;
        }
        return Math.abs(getLigne() - ligne) + Math.abs(getColonne() - colonne);
    }

    public int distanceA(Ressource ressource) {
        if (ressource == null || ressource.getLigne() == -1 || ressource.getColonne() == -1) {
            return Integer.MAX_VALUE;
        }
        return distanceA(ressource.getLigne(), ressource.getColonne());
    }

    public boolean estPlace() {
        return getLigne() != -1 && getColonne() != -1;
    }

    public void retirerDuTerrain() {
        // Les agents ne sont pas stockes dans Terrain : on vide juste leur position interne.
        resetPosition();
    }

    protected void desactiverEtRetirer() {
        desactiver();
        retirerDuTerrain();
    }

    /**
     * Action principale executee a chaque etape.
     */
    public abstract String agir(Simulation simulation) throws SimulationException;

    @Override
    public boolean seDeplacer(int ligneCible, int colonneCible) throws SimulationException {
        if (!actif || !estPlace()) {
            return false;
        }

        if (!terrain.sontValides(ligneCible, colonneCible)) {
            throw new SimulationException("Cible invalide pour " + nom + " : (" + ligneCible + "," + colonneCible + ")");
        }

        int ligneActuelle = getLigne();
        int colonneActuelle = getColonne();

        int nouvelleLigne = ligneActuelle;
        int nouvelleColonne = colonneActuelle;

        if (ligneCible > ligneActuelle) {
            nouvelleLigne++;
        } else if (ligneCible < ligneActuelle) {
            nouvelleLigne--;
        } else if (colonneCible > colonneActuelle) {
            nouvelleColonne++;
        } else if (colonneCible < colonneActuelle) {
            nouvelleColonne--;
        }

        if (nouvelleLigne == ligneActuelle && nouvelleColonne == colonneActuelle) {
            return false;
        }

        if (!terrain.sontValides(nouvelleLigne, nouvelleColonne)) {
            return false;
        }

        setPosition(nouvelleLigne, nouvelleColonne);
        return true;
    }
}
