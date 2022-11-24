/**
 * Nazwa: Planety
 * Autor: Valeriia Tykhoniuk (266319)
 * Data utworzenia: 11.10.2022
 */

package com.company.laboratorium02.run;

import com.company.laboratorium02.model.Planet;
import com.company.laboratorium02.model.PlanetException;
import com.company.laboratorium02.ui.PlanetWindowDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serial;

public class PlanetWindowApp extends JFrame implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String AUTHOR = "Nazwa: Planety. Autor: Valeriia Tykhoniuk (266319). Data utworzenia: 15.11.2022";

    public static void main(String[] args) {
        new PlanetWindowApp();
    }

    private Planet currentPlanet;


    //etykiety
    JLabel nameLabel = new JLabel("Name: ");
    JLabel colourLabel = new JLabel("Color: ");
    JLabel massLabel = new JLabel("Mass: ");
    JLabel radiusLabel = new JLabel("Radius: ");
    JLabel satellitesLabel = new JLabel("Number of satellites: ");

    //pola
    JTextField nameField = new JTextField(10);
    JTextField colourField = new JTextField(10);
    JTextField massField = new JTextField(10);
    JTextField radiusField = new JTextField(10);
    JTextField satellitesField = new JTextField(10);

    //przyciski
    JButton newPlanetButton = new JButton("New planet");
    JButton deletePlanetButton = new JButton("Delete planet");
    JButton editPlanetButton = new JButton("Change planet");
    JButton loadFromDocumentButton = new JButton("Read data from document");
    JButton saveToDocumentButton = new JButton("Write data to the document");
    JButton infoButton = new JButton("About author");
    JButton exitButton = new JButton("End program");
    JButton serializableWriteButton = new JButton("Write serializable data to the document");
    JButton serializableReadButton = new JButton("Read serializable data from the document");

    public PlanetWindowApp() {
        setTitle("PlanetWindowsApp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        nameLabel.setForeground(Color.white);
        colourLabel.setForeground(Color.white);
        massLabel.setForeground(Color.white);
        radiusLabel.setForeground(Color.white);
        satellitesLabel.setForeground(Color.white);

        nameField.setEditable(false);
        colourField.setEditable(false);
        massField.setEditable(false);
        radiusField.setEditable(false);
        satellitesField.setEditable(false);

        newPlanetButton.addActionListener(this);
        deletePlanetButton.addActionListener(this);
        editPlanetButton.addActionListener(this);
        loadFromDocumentButton.addActionListener(this);
        saveToDocumentButton.addActionListener(this);
        infoButton.addActionListener(this);
        exitButton.addActionListener(this);
        serializableWriteButton.addActionListener(this);
        serializableReadButton.addActionListener(this);

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

        panel.add(newPlanetButton);
        panel.add(deletePlanetButton);
        panel.add(editPlanetButton);
        panel.add(loadFromDocumentButton);
        panel.add(saveToDocumentButton);
        panel.add(infoButton);
        panel.add(exitButton);
        panel.add(serializableWriteButton);
        panel.add(serializableReadButton);

        panel.add(new JLabel(new ImageIcon("src/main/java/com/company/laboratorium02/01.jpg")));

        panel.setBackground(new java.awt.Color(6, 6, 28));

        setContentPane(panel);

        showCurrentPlanet();

        setVisible(true);
    }

    void showCurrentPlanet() {
        if (currentPlanet == null) {
            nameField.setText("");
            colourField.setText("");
            massField.setText("");
            radiusField.setText("");
            satellitesField.setText("");
        } else {
            nameField.setText(currentPlanet.getName());
            colourField.setText("" + currentPlanet.getColour());
            massField.setText("" + currentPlanet.getMass());
            radiusField.setText("" + currentPlanet.getRadius());
            satellitesField.setText("" + currentPlanet.getSatellitesCount());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        try {
            if (eventSource == newPlanetButton) {
                currentPlanet = PlanetWindowDialog.createPlanet(this);
            }
            if (eventSource == deletePlanetButton) {
                currentPlanet = null;
            }
            if (eventSource == saveToDocumentButton) {
                String fileName = JOptionPane.showInputDialog("Write the name of document");
                if (fileName == null || fileName.equals("")) return;
                Planet.writeToTheDocument(fileName, currentPlanet);
            }

            if (eventSource == serializableWriteButton) {
                String fileName = JOptionPane.showInputDialog("Write the name of document");
                if (fileName == null || fileName.equals("")) return;
                Planet.writeObjectToTheDocument(fileName, currentPlanet);
            }

            if (eventSource == loadFromDocumentButton) {
                JFileChooser fc = new JFileChooser();
                int i = fc.showOpenDialog(this);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    currentPlanet = Planet.readFromFile(f);
                }
            }

            if (eventSource == serializableReadButton) {
                JFileChooser fc = new JFileChooser();
                int i = fc.showOpenDialog(this);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    currentPlanet = Planet.readObjectToTheDocument(f);
                }
            }

            if (eventSource == editPlanetButton) {
                if (currentPlanet == null) throw new PlanetException("Any planet wasn't changed");
                PlanetWindowDialog.changePlanet(this, currentPlanet);
            }

            if (eventSource == infoButton) {
                JOptionPane.showMessageDialog(this, AUTHOR);
            }

            if (eventSource == exitButton) {
                System.exit(0);
            }

        } catch (PlanetException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Unexpected error", JOptionPane.ERROR_MESSAGE);
        }

        showCurrentPlanet();
    }
}


