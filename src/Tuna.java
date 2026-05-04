/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                              File: Tuna.java                               */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

import java.util.ArrayList;

/**
 * Tuna : poisson predateur qui chasse les sardines.
 */
public class Tuna extends Fish {
    public Tuna(String nom, Terrain terrain) {
        super("Tuna", nom, terrain, 8);
    }

    @Override
    public String agir(Simulation simulation) throws SimulationException {
        return agirAvecSardines(simulation.getSardines());
    }

    private String agirAvecSardines(ArrayList<Sardine> sardines) throws SimulationException {
        if (!estActif()) {
            return nom + " est inactif.";
        }

        depenserEnergie(1);
        if (!estActif()) {
            return nom + " n'a plus d'energie et disparait.";
        }

        Sardine cible = chercherSardineLaPlusProche(sardines);
        if (cible == null) {
            return nom + " ne trouve pas de sardine.";
        }

        int distance = distanceA(cible);
        int ligCible = cible.getLigne();
        int colCible = cible.getColonne();

        if (distance <= 1) {
            cible.desactiverEtRetirer();
            sardines.remove(cible);
            gagnerEnergie(3);

            seDeplacer(ligCible, colCible);
            return nom + " mange " + cible.getNom() + ".";
        }

        boolean deplace = seDeplacer(ligCible, colCible);
        if (deplace) {
            return nom + " se deplace vers une sardine.";
        }
        return nom + " est bloque.";
    }

    private Sardine chercherSardineLaPlusProche(ArrayList<Sardine> sardines) {
        Sardine meilleureCible = null;
        int meilleureDistance = Integer.MAX_VALUE;

        for (Sardine s : sardines) {
            if (s == null || !s.estActif() || s.getLigne() == -1 || s.getColonne() == -1) {
                continue;
            }
            int d = distanceA(s);
            if (d < meilleureDistance) {
                meilleureDistance = d;
                meilleureCible = s;
            }
        }
        return meilleureCible;
    }
}


