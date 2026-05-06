# Projet UL2IN002 - Simulation Marine

## 1) Titre du projet
**Simulation marine : chaine alimentaire en mer (plancton, sardines, thons, bateau de peche).**

## 2) Theme et objectif
Le projet respecte le theme **"la Mer"** du sujet UL2IN002.  
La simulation represente une mer sous forme de grille (`Terrain`) avec :
- des ressources fixes (recifs),
- des ressources evolutives (plancton),
- des agents mobiles (sardines, thons, bateau).

Objectif de la simulation :
- observer l'evolution des ressources,
- suivre les interactions proie/predateur,
- mesurer les prises du bateau.

## 3) Classes principales et roles
- `Simulation` : classe centrale (initialisation, boucle d'etapes, affichage, regles).
- `Agent` (abstraite) : base commune des agents mobiles, position et deplacement.
- `Fish` (abstraite) : base des poissons avec gestion d'energie.
- `Sardine` : cherche et consomme le plancton.
- `Tuna` : chasse les sardines.
- `FishingShip` : cherche et capture les thons.
- `Plankton` : ressource qui grandit avec le temps.
- `Movable` : interface de deplacement.
- `SimulationConfig` : singleton de configuration globale.
- `SimulationLogger` : utilitaire statique de logs (terminal + fichier).
- `SimulationException` : exception personnalisee.
- `TestSimulation` : point d'entree `main`, lance plusieurs scenarios.

## 4) Hierarchie d'heritage (3 niveaux minimum)
Hierarchie principale :
- `Agent` -> `Fish` -> `Sardine`
- `Agent` -> `Fish` -> `Tuna`

Autres liens utiles :
- `Agent` -> `FishingShip`
- `Ressource` -> `Plankton`

## 5) Interface utilisee
Interface `Movable` :
- methode `seDeplacer(int ligneCible, int colonneCible)`

Toutes les classes d'agents heritent d'`Agent`, qui implemente `Movable`.

## 6) Singleton utilise
Classe `SimulationConfig` :
- instance unique via `getInstance()`,
- stocke les parametres globaux (taille terrain, nombres d'agents, nombres de ressources, etc.),
- reutilisee pour executer plusieurs scenarios.

## 7) Exception personnalisee
Classe `SimulationException` :
- utilisee pour signaler les erreurs de simulation (creation terrain, placement, logs...).

## 8) Constructeur de copie
Classe `Plankton` :
- constructeur de copie `Plankton(Plankton autre)`.

## 9) Classe utilitaire statique
Classe `SimulationLogger` :
- classe `final` avec constructeur prive,
- methodes statiques `log`, `separateur`, `activerFichier`, `desactiverFichier`.

## 10) Fonctionnement d'une simulation (etape par etape)
1. Creation du terrain.
2. Placement aleatoire des ressources (`Plankton` et `Recif`).
3. Creation et placement aleatoire des agents.
4. A chaque etape :
   - les sardines agissent,
   - les thons agissent,
   - le bateau agit,
   - mise a jour des cases du terrain (croissance du plancton),
   - affichage des logs et de l'etat de la mer.
5. Arret :
   - soit quand le nombre max d'etapes est atteint,
   - soit quand il n'y a plus de thons.

## 11) Arborescence du projet
- `src/` : fichiers `.java` du projet.
- `classes/` : fichiers `.class` compiles (dont `Terrain.class` et `Ressource.class` fournis).
- `doc/` : javadoc.
- `logs.txt` : exemples de simulations (3 scenarios).
- `description.pdf` : compte-rendu du projet.
- `UL2IN002-sujet-projet2026fev.pdf` : sujet officiel.

## 12) Compilation et execution
Depuis la racine du projet :

```bash
javac -cp classes -d classes src/*.java
java -cp classes TestSimulation
```

Le programme affiche les logs dans le terminal et les ecrit aussi dans `logs.txt`.

## 13) Contenu des fichiers importants
- `src/Simulation.java` : moteur de simulation.
- `src/Agent.java` : logique commune des agents + methode `distance(lig,col)` demandee dans le sujet.
- `src/Sardine.java`, `src/Tuna.java`, `src/FishingShip.java` : comportements metier.
- `src/Plankton.java` : ressource evolutive.
- `src/SimulationConfig.java` : parametrage centralise.
- `src/SimulationLogger.java` : gestion des sorties de logs.
- `src/TestSimulation.java` : lance 3 scenarios de test.




```text
Ressource
├── Plankton
└── Agent
    ├── FishingShip
    └── Fish
        ├── Sardine
        └── Tuna
```

<br><hr><br>

```text 

            lancerScenario(
                "SCENARIO 1 - Configuration standard",
                10, 10, 20, // lig, col, steps
                15, 6, // planktons , reefs
                6, 3, // sardin , tunas
                3, 6 // p init, p max
            );
```