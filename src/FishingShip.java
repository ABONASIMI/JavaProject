/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                           File: FishingShip.java                           */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

import java.util.ArrayList;

/**
 * Bateau de peche : il cherche les thons et les capture.
 */
public class FishingShip extends Agent {
    private int nbPrises;

    public FishingShip(String nom, Terrain terrain) {
        super("Ship", nom, terrain);
        this.nbPrises = 0;
    }

    @Override
    public String agir(Simulation simulation) throws SimulationException {
        return agirAvecTunas(simulation.getTunas());
    }

    public int getNbPrises() {
        return nbPrises;
    }

    private String agirAvecTunas(ArrayList<Tuna> tunas) throws SimulationException {
        if (!estActif()) {
            return nom + " est inactif.";
        }

        Tuna cible = chercherTunaLePlusProche(tunas);
        if (cible == null) {
            return nom + " ne trouve plus de thon.";
        }

        int distance = distanceA(cible);
        int ligCible = cible.getLigne();
        int colCible = cible.getColonne();

        if (distance <= 1) {
            cible.desactiverEtRetirer();
            tunas.remove(cible);
            nbPrises++;

            seDeplacer(ligCible, colCible);
            return nom + " capture " + cible.getNom() + " (prises=" + nbPrises + ").";
        }

        boolean deplace = seDeplacer(ligCible, colCible);
        if (deplace) {
            return nom + " se deplace vers un thon.";
        }
        return nom + " est bloque.";
    }

    private Tuna chercherTunaLePlusProche(ArrayList<Tuna> tunas) {
        Tuna meilleureCible = null;
        int meilleureDistance = Integer.MAX_VALUE;

        for (Tuna t : tunas) {
            if (t == null || !t.estActif() || t.getLigne() == -1 || t.getColonne() == -1) {
                continue;
            }
            int d = distanceA(t);
            if (d < meilleureDistance) {
                meilleureDistance = d;
                meilleureCible = t;
            }
        }
        return meilleureCible;
    }
}
