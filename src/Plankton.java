/******************************************************************************/
/*                                                                            */
/*                            Sorbonne Universite                             */
/*                       UL2IN002 - Programmation Objet                       */
/*                                                                            */
/*                            File: Plankton.java                             */
/*                     Etudiant: Mohamad Abobaker NASIMI                      */
/*                                                                            */
/******************************************************************************/

public class Plankton extends Ressource {
    private int quantiteMax;

    public Plankton(int quantiteInitiale, int quantiteMax) {
        super("Plancton", quantiteInitiale);
        this.quantiteMax = quantiteMax;
    }

    public Plankton(Plankton autre) {
        super("Plancton", autre.getQuantite());
        this.quantiteMax = autre.quantiteMax;
    }

    public void grandir() {
        if (getQuantite() < quantiteMax) {
            setQuantite(getQuantite() + 1);
        }
    }

    public void consommer(int quantite) {
        int nouvelleQuantite = getQuantite() - quantite;
        if (nouvelleQuantite < 0) {
            nouvelleQuantite = 0;
        }
        setQuantite(nouvelleQuantite);
    }

    public boolean estEpuise() {
        return getQuantite() <= 0;
    }
}
