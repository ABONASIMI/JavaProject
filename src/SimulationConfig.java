/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                        File: SimulationConfig.java                         */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

/**
 * Singleton de configuration.
 */
public class SimulationConfig {
    private static SimulationConfig instance;

    private int nbLignes;
    private int nbColonnes;
    private int nombreEtapesMax;
    private int nombrePlanktons;
    private int nombreRecifs;
    private int nombreSardines;
    private int nombreTunas;
    private int quantiteInitialePlankton;
    private int quantiteMaxPlankton;

    private SimulationConfig() {
        nbLignes = 10;
        nbColonnes = 10;
        nombreEtapesMax = 20;
        nombrePlanktons = 15;
        nombreRecifs = 6;
        nombreSardines = 6;
        nombreTunas = 3;
        quantiteInitialePlankton = 3;
        quantiteMaxPlankton = 6;
    }

    public static SimulationConfig getInstance() {
        if (instance == null) {
            instance = new SimulationConfig();
        }
        return instance;
    }

    public int getNbLignes() {
        return nbLignes;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public int getNombreEtapesMax() {
        return nombreEtapesMax;
    }

    public int getNombrePlanktons() {
        return nombrePlanktons;
    }

    public int getNombreSardines() {
        return nombreSardines;
    }

    public int getNombreRecifs() {
        return nombreRecifs;
    }

    public int getNombreTunas() {
        return nombreTunas;
    }

    public int getQuantiteInitialePlankton() {
        return quantiteInitialePlankton;
    }

    public int getQuantiteMaxPlankton() {
        return quantiteMaxPlankton;
    }
}
