import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FenetreSimulation extends JFrame implements ActionListener {
    private JButton boutonLancer;
    private JButton boutonStop;
    private JTextArea zoneTexte;
    private PanneauSimulation panneau;
    private Simulation sim;
    private Timer timer;
    private int toursMax;

    public FenetreSimulation() {
        setTitle("Simulation Marine");
        setSize(1000, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        boutonLancer = new JButton("Lancer la simulation");
        boutonStop = new JButton("Stop");
        boutonStop.setEnabled(false);

        boutonLancer.addActionListener(this);
        boutonStop.addActionListener(this);

        JPanel panneauHaut = new JPanel();
        panneauHaut.add(boutonLancer);
        panneauHaut.add(boutonStop);

        zoneTexte = new JTextArea(8, 30);
        zoneTexte.setEditable(false);
        JScrollPane scrollTexte = new JScrollPane(zoneTexte);

        sim = new Simulation(10, 10, 18, 10, 2, 2);
        panneau = new PanneauSimulation(sim);
        JScrollPane scrollSimulation = new JScrollPane(panneau);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollSimulation, scrollTexte);
        split.setResizeWeight(0.82);

        add(panneauHaut, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);

        toursMax = 30;

        timer = new Timer(700, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sim.estFinie() && sim.getTour() < toursMax) {
                    sim.etape();
                    panneau.repaint();

                    zoneTexte.append(
                        "Tour " + sim.getTour()
                        + " | Poissons : " + sim.compterPoissons()
                        + " | Plastiques : " + sim.compterPlastiques()
                        + "\n"
                    );
                } else {
                    timer.stop();
                    zoneTexte.append("===== FIN DE SIMULATION =====\n");
                    zoneTexte.append("Nombre de tours : " + sim.getTour() + "\n");
                    zoneTexte.append("Poissons restants : " + sim.compterPoissons() + "\n");
                    zoneTexte.append("Plastiques restants : " + sim.compterPlastiques() + "\n");
                    boutonLancer.setEnabled(true);
                    boutonStop.setEnabled(false);
                }
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonLancer) {
            sim = new Simulation(10, 10, 18, 10, 2, 2);
            panneau.setSimulation(sim);
            zoneTexte.setText("Demarrage de la simulation...\n");
            boutonLancer.setEnabled(false);
            boutonStop.setEnabled(true);
            timer.start();
        } else if (e.getSource() == boutonStop) {
            timer.stop();
            zoneTexte.append("Simulation arretee.\n");
            boutonLancer.setEnabled(true);
            boutonStop.setEnabled(false);
        }
    }
}
