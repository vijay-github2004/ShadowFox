import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class StudentInformationSystem extends JFrame {
    private JTextField idField, nameField, courseField;
    private JTable table;
    private DefaultTableModel model;

    public StudentInformationSystem() {
        setTitle("Student Information System");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        idField = new JTextField();
        nameField = new JTextField();
        courseField = new JTextField();

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Course:"));
        inputPanel.add(courseField);

        add(inputPanel, BorderLayout.NORTH);

        // Table Model & Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Course"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Button Action
        addBtn.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String course = courseField.getText();

            if (id.isEmpty() || name.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
            } else {
                model.addRow(new Object[]{id, name, course});
                clearFields();
            }
        });

        // Update Button Action
        updateBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                model.setValueAt(idField.getText(), selected, 0);
                model.setValueAt(nameField.getText(), selected, 1);
                model.setValueAt(courseField.getText(), selected, 2);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to update.");
            }
        });

        // Delete Button Action
        deleteBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                model.removeRow(selected);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
        });

        // Table row click - fill the fields
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selected = table.getSelectedRow();
                if (selected >= 0) {
                    idField.setText(model.getValueAt(selected, 0).toString());
                    nameField.setText(model.getValueAt(selected, 1).toString());
                    courseField.setText(model.getValueAt(selected, 2).toString());
                }
            }
        });

        setVisible(true);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        courseField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentInformationSystem::new);
    }
}
