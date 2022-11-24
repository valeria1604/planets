/**
 * Nazwa: Planety
 * Autor: Valeriia Tykhoniuk (266319)
 * Data utworzenia: 11.10.2022
 */

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
        setSize(170, 250);
        setLocationRelativeTo(parent);
        setResizable(false);

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

        nameLabel.setForeground(Color.white);
        colourLabel.setForeground(Color.white);
        massLabel.setForeground(Color.white);
        radiusLabel.setForeground(Color.white);
        satellitesLabel.setForeground(Color.white);

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

        panel.setBackground(new java.awt.Color(6, 6, 28));

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
                planet.setRadius(Float.parseFloat(radiusField.getText()));
                planet.setSatellitesCount(Integer.parseInt(satellitesField.getText()));

                dispose();
            } catch (PlanetException exx) {
                JOptionPane.showMessageDialog(this, exx.getMessage(), "Unexpected error", JOptionPane.ERROR_MESSAGE);
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Number format exception", JOptionPane.ERROR_MESSAGE);
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
