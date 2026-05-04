/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                             File: Sardine.java                             */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

import java.util.ArrayList;

/**
 * Sardine : poisson qui cherche et mange le plancton.
 */
public class Sardine extends Fish {
    public Sardine(String nom, Terrain terrain) {
        super("Sardine", nom, terrain, 6);
    }

    @Override
    public String agir(Simulation simulation) throws SimulationException {
        return agirAvecPlanktons(simulation.getPlanktons());
    }

    private String agirAvecPlanktons(ArrayList<Plankton> planktons) throws SimulationException {
        if (!estActif()) {
            return nom + " est inactive.";
        }

        depenserEnergie(1);
        if (!estActif()) {
            return nom + " n'a plus d'energie et disparait.";
        }

        Plankton cible = chercherPlanctonLePlusProche(planktons);
        if (cible == null) {
            return nom + " ne trouve pas de plancton.";
        }

        int distance = distanceA(cible);
        int ligCible = cible.getLigne();
        int colCible = cible.getColonne();

        if (distance <= 1) {
            cible.consommer(2);
            gagnerEnergie(2);

            if (cible.estEpuise()) {
                terrain.viderCase(ligCible, colCible);
                planktons.remove(cible);
                return nom + " mange tout le plancton en (" + ligCible + "," + colCible + ").";
            }
            return nom + " mange un peu de plancton en (" + ligCible + "," + colCible + ").";
        }

        boolean deplacee = seDeplacer(ligCible, colCible);
        if (deplacee) {
            return nom + " se deplace vers le plancton.";
        }
        return nom + " est bloquee et ne peut pas avancer.";
    }

    private Plankton chercherPlanctonLePlusProche(ArrayList<Plankton> planktons) {
        Plankton meilleureCible = null;
        int meilleureDistance = Integer.MAX_VALUE;

        for (Plankton p : planktons) {
            if (p == null || p.getLigne() == -1 || p.getColonne() == -1 || p.estEpuise()) {
                continue;
            }
            int d = distanceA(p);
            if (d < meilleureDistance) {
                meilleureDistance = d;
                meilleureCible = p;
            }
        }
        return meilleureCible;
    }
}
