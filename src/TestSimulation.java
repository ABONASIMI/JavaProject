/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                         File: TestSimulation.java                          */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

/**
 * Point d'entree du projet.
 */
public class TestSimulation {
    public static void main(String[] args) {
        try {
            Simulation simulation = new Simulation();
            simulation.initialiser();
            simulation.lancer();
        } catch (SimulationException e) {
            System.out.println("Erreur de simulation: " + e.getMessage());
        }
    }
}
