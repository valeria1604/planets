package com.company.laboratorium02.run;


import com.company.laboratorium02.ui.ViewGroupOfPlanets;
import com.company.laboratorium02.model.Planet;
import com.company.laboratorium02.model.PlanetColour;
import com.company.laboratorium02.model.PlanetException;
import com.company.laboratorium02.ui.PlanetWindowDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

public class GroupOfPlanetsWindowApp extends JDialog implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final String AUTHOR = "Nazwa: Planety. Autor: Valeriia Tykhoniuk (266319). Data utworzenia: 15.11.2022";

    public static void main(String[] args) {
        Vector<Planet> group = new Vector<Planet>();
        try {
            Planet availablePlanet;
            availablePlanet = new Planet("Earth");
            availablePlanet.setColour(PlanetColour.BLUE);
            availablePlanet.setMass(60);
            availablePlanet.setRadius(400);
            availablePlanet.setSatellitesCount(1);
            group.add(availablePlanet);

            availablePlanet = new Planet("Mars");
            availablePlanet.setColour(PlanetColour.RED);
            availablePlanet.setMass(70);
            availablePlanet.setRadius(530);
            availablePlanet.setSatellitesCount(80);
            group.add(availablePlanet);

            availablePlanet = new Planet("Saturn");
            availablePlanet.setColour(PlanetColour.ORANGE);
            availablePlanet.setMass(79);
            availablePlanet.setRadius(3488);
            availablePlanet.setSatellitesCount(83);
            group.add(availablePlanet);

        } catch (PlanetException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Unexpected error", JOptionPane.ERROR_MESSAGE);
        }

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GroupOfPlanetsWindowApp(null, group);
            }
        });
    }


    private final Vector<Planet> currentGroupOfPlanets;
    JButton newPlanetButton = new JButton("New planet");
    JButton deletePlanetButton = new JButton("Delete planet");
    JButton editPlanetButton = new JButton("Change planet");
    JButton loadFromDocumentButton = new JButton("Read data from document");
    JButton saveToDocumentButton = new JButton("Write data to the document");
    JButton infoButton = new JButton("About author");

    JButton buttonSortName = new JButton("Sort by the name");
    JButton buttonSortMass = new JButton("Sort by the mass");
    JButton buttonSortColour = new JButton("Sort by the colour");

    ViewGroupOfPlanets viewList;

    public GroupOfPlanetsWindowApp(Window parent) {
        this(parent, new Vector<Planet>());
    }

    public GroupOfPlanetsWindowApp(Window parent, Vector<Planet> group) {

        super(parent, ModalityType.DOCUMENT_MODAL);

        setTitle("Changing the group");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(450, 450);
        setResizable(false);
        if (parent != null) {
            Point location = parent.getLocation();
            location.translate(100, 100);
            setLocation(location);
        } else setLocationRelativeTo(null);

        currentGroupOfPlanets = group;

        // Utwotrzenie tabeli z list� os�b nale��cych do grupy
        viewList = new ViewGroupOfPlanets(currentGroupOfPlanets, 400, 250);
        viewList.refreshView();

        // Dodanie s�uchaczy zdarze� do wszystkich przycisk�w.
        // UWAGA: s�uchaczem zdarze� b�dzie metoda actionPerformed
        // zaimplementowana w tej klasie i wywo�ana dla
        // bie��cej instancji okna aplikacji - referencja this
        newPlanetButton.addActionListener(this);
        deletePlanetButton.addActionListener(this);
        editPlanetButton.addActionListener(this);
        loadFromDocumentButton.addActionListener(this);
        saveToDocumentButton.addActionListener(this);
        infoButton.addActionListener(this);

        buttonSortName.addActionListener(this);
        buttonSortMass.addActionListener(this);
        buttonSortColour.addActionListener(this);


        // Utworzenie g��wnego panelu okna aplikacji.
        // Domy�lnym mened�erem rozk�adu dla panelu b�dzie
        // FlowLayout, kt�ry uk�ada wszystkie komponenty jeden za drugim.
        JPanel panel = new JPanel();

        // Dodanie i rozmieszczenie na panelu wszystkich
        // komponent�w GUI.

        panel.add(viewList);

        panel.add(newPlanetButton);
        panel.add(deletePlanetButton);
        panel.add(editPlanetButton);
        panel.add(loadFromDocumentButton);
        panel.add(saveToDocumentButton);
        panel.add(infoButton);

        panel.add(buttonSortName);
        panel.add(buttonSortMass);
        panel.add(buttonSortColour);

        setContentPane(panel);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        try {
            if (source == newPlanetButton) {
                Planet newPlanet = PlanetWindowDialog.createPlanet(this);
                if (newPlanet != null) currentGroupOfPlanets.add(newPlanet);
            }

            if (source == editPlanetButton) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Planet> iterator = currentGroupOfPlanets.iterator();
                    while (index-- > 0)
                        iterator.next();
                    PlanetWindowDialog.changePlanet(this, iterator.next());
                }
            }

            if (source == deletePlanetButton) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Planet> iterator = currentGroupOfPlanets.iterator();
                    while (index-- >= 0)
                        iterator.next();
                    iterator.remove();
                }
            }

            if (source == loadFromDocumentButton) {
                String fileName = JOptionPane.showInputDialog("Write the name of document");
                if (fileName == null || fileName.equals("")) return;
                Planet planet = Planet.readFromFile(fileName);
                currentGroupOfPlanets.add(planet);
            }

            if (source == saveToDocumentButton) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Planet> iterator = currentGroupOfPlanets.iterator();
                    while (index-- > 0)
                        iterator.next();
                    Planet planet = iterator.next();

                    String fileName = JOptionPane.showInputDialog("Write the name of document");
                    if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
                    Planet.writeToTheDocument(fileName, planet);
                }
            }

//            if (source == buttonSortName) {
//                currentGroupofPlanets.sort( new Comparator<Planet>() {
//                    TODO
//                    @Override
//                    public int compare(Planet o1, Planet o2) {
//                        int result = o1.getLastName().compareTo(o2.getLastName());
//                        if (result == 0) result = o1.getFirstName().compareTo(o2.getFirstName());
//                        return result;
//                    }
//                });
//            }

            if (source == buttonSortMass) {
                currentGroupOfPlanets.sort(new Comparator<Planet>() {

                    @Override
                    public int compare(Planet o1, Planet o2) {
                        if (o1.getMass() < o2.getMass())
                            return -1;
                        if (o1.getMass() > o2.getMass())
                            return 1;
                        return 0;
                    }
                });
            }

            if (source == buttonSortColour) {
                currentGroupOfPlanets.sort(new Comparator<Planet>() {

                    @Override
                    public int compare(Planet o1, Planet o2) {
                        return o1.getColour().toString().compareTo(o2.getColour().toString());
                    }
                });
            }

            if (source == infoButton) {
                JOptionPane.showMessageDialog(this, AUTHOR);
            }

        } catch (PlanetException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Unexpected error", JOptionPane.ERROR_MESSAGE);
        }

        // Aktualizacja zawarto�ci tabeli z list� os�b.
        viewList.refreshView();
    }
}

