package com.company.laboratorium02.ui;

import com.company.laboratorium02.model.Planet;
import com.company.laboratorium02.model.PlanetColour;
import com.company.laboratorium02.model.PlanetException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

public class PlanetWindowDialog extends JDialog implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;

    private Planet planet;


    JLabel nameLabel = new JLabel("Name: ");
    JLabel colourLabel = new JLabel("Color: ");
    JLabel massLabel = new JLabel("Mass: ");
    JLabel radiusLabel = new JLabel("Radius: ");
    JLabel satellitesLabel = new JLabel("Number of satellites: ");

    JTextField nameField = new JTextField(10);
    JComboBox<PlanetColour> colourField = new JComboBox<PlanetColour>(PlanetColour.values());
    JTextField massField = new JTextField(10);
    JTextField radiusField = new JTextField(10);
    JTextField satellitesField = new JTextField(10);

    JButton OKButton = new JButton("  OK  ");
    JButton CancelButton = new JButton("Cancel");

    public PlanetWindowDialog(Window parent, Planet planet) {
        super(parent, Dialog.ModalityType.DOCUMENT_MODAL);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(220, 200);
        setLocationRelativeTo(parent);

        this.planet = planet;

        if (planet == null) {
            setTitle("New planet");
        } else {
            setTitle(planet.toString());
            nameField.setText(planet.getName());
            colourField.setSelectedItem(planet.getColour());
            massField.setText("" + planet.getMass());
            radiusField.setText("" + planet.getRadius());
            satellitesField.setText("" + planet.getSatellitesCount());
        }
        OKButton.addActionListener(this);
        CancelButton.addActionListener(this);

        JPanel panel = new JPanel();

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(colourLabel);
        panel.add(colourField);

        panel.add(massLabel);
        panel.add(massField);

        panel.add(radiusLabel);
        panel.add(radiusField);

        panel.add(satellitesLabel);
        panel.add(satellitesField);

        panel.add(OKButton);
        panel.add(CancelButton);

        setContentPane(panel);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventsource = e.getSource();

        if (eventsource == OKButton) {
            try {
                if (planet == null) {
                    planet = new Planet(nameField.getText());
                } else {
                    planet.setName(nameField.getText());
                }
                planet.setColour((PlanetColour) colourField.getSelectedItem());
                planet.setMass(Integer.parseInt(massField.getText()));
                planet.setRadius(Integer.parseInt(radiusField.getText()));
                planet.setSatellitesCount(Integer.parseInt(satellitesField.getText()));

                dispose();
            } catch (PlanetException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Unexpected error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (eventsource == CancelButton) {
            dispose();
        }

    }

    public static Planet createPlanet(Window parent) {
        PlanetWindowDialog dialog = new PlanetWindowDialog(parent, null);
        return dialog.planet;
    }

    public static void changePlanet(Window parent, Planet planet) {
        new PlanetWindowDialog(parent, planet);
    }
}
