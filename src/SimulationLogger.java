/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                        File: SimulationLogger.java                         */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

public final class SimulationLogger {
    private SimulationLogger() {
    }

    public static void log(String message) {
        System.out.println(message);
    }

    public static void separateur() {
        log("--------------------------------------------------");
    }
}
