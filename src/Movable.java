/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                             File: Movable.java                             */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

/**
 * Interface simple pour les objets qui peuvent se deplacer.
 */
public interface Movable {
    /**
     * Deplace l'agent d'une case vers la cible.
     * 
     * @param ligneCible ligne visee
     * @param colonneCible colonne visee
     * @return true si le deplacement a eu lieu, false sinon
     * @throws SimulationException si la cible est invalide
     */
    boolean seDeplacer(int ligneCible, int colonneCible) throws SimulationException;
}
