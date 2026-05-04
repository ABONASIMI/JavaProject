/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                       File: SimulationException.java                       */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

/**
 * Exception personnalisee pour la simulation.
 */
public class SimulationException extends Exception {
    public SimulationException(String message) {
        super(message);
    }

    public SimulationException(String message, Throwable cause) {
        super(message, cause);
    }
}
