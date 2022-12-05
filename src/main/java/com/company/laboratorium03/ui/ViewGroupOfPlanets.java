/**
 * Nazwa: Planety
 * Autor: Valeriia Tykhoniuk (266319)
 * Data utworzenia: 11.10.2022
 */

package com.company.laboratorium03.ui;

import com.company.laboratorium03.model.Planet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.Serial;
import java.util.Vector;

public class ViewGroupOfPlanets extends JScrollPane {

    @Serial
    private static final long serialVersionUID = 1L;

    private Vector<Planet> group;
    private JTable table;
    private DefaultTableModel tableModel;

    public ViewGroupOfPlanets(Vector<Planet> group, int width, int height) {
        this.group = group;
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createTitledBorder("All planets:"));

        String[] tableHeader = {"Name", "Mass", "Radius", "Colour", "Amount of satellites"};
        tableModel = new DefaultTableModel(tableHeader, 0);
        table = new JTable(tableModel) {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        setViewportView(table);
    }

    public void refreshView() {
        tableModel.setRowCount(0);
        for (Planet p : group) {
            String[] row = {p.getName(), String.valueOf(p.getMass()), String.valueOf(p.getRadius()), p.getColour().toString(), String.valueOf(p.getSatellitesCount())};
            tableModel.addRow(row);
        }
    }

    public int getSelectedIndex() {
        int index = table.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "No planet was chosen", "Unexpected error", JOptionPane.ERROR_MESSAGE);
        }
        return index;
    }

}
