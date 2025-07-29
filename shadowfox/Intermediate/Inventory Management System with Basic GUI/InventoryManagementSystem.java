import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class InventoryManagementSystem extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField, qtyField, priceField;

    public InventoryManagementSystem() {
        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the window

        // Panel for inputs
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Quantity:"));
        qtyField = new JTextField();
        panel.add(qtyField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        panel.add(addButton);
        panel.add(updateButton);

        // Table setup
        model = new DefaultTableModel(new String[]{"Item Name", "Quantity", "Price"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Action listeners
        addButton.addActionListener(e -> addItem());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());

        // Bottom button
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteButton);

        // Layout
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addItem() {
        String name = nameField.getText();
        String qty = qtyField.getText();
        String price = priceField.getText();

        if (name.isEmpty() || qty.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled");
            return;
        }

        model.addRow(new Object[]{name, qty, price});
        clearFields();
    }

    private void updateItem() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to update");
            return;
        }

        model.setValueAt(nameField.getText(), row, 0);
        model.setValueAt(qtyField.getText(), row, 1);
        model.setValueAt(priceField.getText(), row, 2);
        clearFields();
    }

    private void deleteItem() {
        int row = table.getSelectedRow();
        if (row != -1) {
            model.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
        }
    }

    private void clearFields() {
        nameField.setText("");
        qtyField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InventoryManagementSystem().setVisible(true);
        });
    }
}
