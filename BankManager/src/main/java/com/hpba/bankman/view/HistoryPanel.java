package com.hpba.bankman.view;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author generalhpba
 */
public final class HistoryPanel extends JPanel {

    JScrollPane scrollPane = new JScrollPane();
    JTable table = new JTable();
    TableModel dtm = new TableModel();

    public HistoryPanel(ArrayList<String[]> al) {
        setLayout(new MigLayout("fill"));
        add(scrollPane, "grow");

        scrollPane.setViewportView(table);
        table.setModel(dtm);
        table.setRowHeight(30);
        dtm.addColumn("Date");
        dtm.addColumn("Operation");
        dtm.addColumn("Counterparty");
        dtm.addColumn("Amount");
        dtm.addColumn("Description");

        table.getColumnModel().getColumn(0).setMinWidth(110);
        table.getColumnModel().getColumn(0).setMaxWidth(110);
        table.getColumnModel().getColumn(2).setMinWidth(122);
        table.getColumnModel().getColumn(2).setMaxWidth(122);
        table.getColumnModel().getColumn(4).setMinWidth(200);
        table.getTableHeader().setReorderingAllowed(false);

        table.setAutoCreateRowSorter(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 3) {
                    if (getText().contains("-")) {
                        setText("-$" + getText().replace("-", ""));
                    } else {
                        setText("$" + getText());
                    }

                }
                return this;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        setVisible(true);
        update(al);
    }

    public void update(ArrayList<String[]> data) {
        if (data == null) {
            return;
        }
        dtm.setRowCount(0);
        for (String[] s : data) {
            dtm.addRow(new Object[]{s[0], s[1], s[2], Double.valueOf(s[3]), s[4]});
        }
    }

    class TableModel extends DefaultTableModel {

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return LocalDateTime.class;
                case 3:
                    return Double.class;
                default:
                    return String.class;
            }
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
