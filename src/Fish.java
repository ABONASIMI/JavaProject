/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                              File: Fish.java                               */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

public abstract class Fish extends Agent {
    private int energie;
    private int energieMax;

    public Fish(String type, String nom, Terrain terrain, int energieMax) {
        super(type, nom, terrain);
        this.energieMax = energieMax;
        this.energie = energieMax;
    }

    protected void depenserEnergie(int cout) {
        if (!estActif()) {
            return;
        }
        energie -= cout;
        if (energie <= 0) {
            energie = 0;
            desactiverEtRetirer();
        }
    }

    protected void gagnerEnergie(int gain) {
        if (!estActif()) {
            return;
        }
        energie += gain;
        if (energie > energieMax) {
            energie = energieMax;
        }
    }
}
