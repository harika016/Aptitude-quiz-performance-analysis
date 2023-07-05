import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.table.DefaultTableModel;

public class Users extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel userIDLabel, nameLabel;
    private JTextField userIDField, nameField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private JTable table;

    public Users() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        userIDLabel = new JLabel("User ID:");
        nameLabel = new JLabel("Name:");

        userIDField = new JTextField(10);
        nameField = new JTextField(10);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertUser();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayUsers();
            }
        });

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectUser();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setVisible(true);
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        add(userIDLabel, c);

        c.gridx = 1;
        add(userIDField, c);

        c.gridx = 0;
        c.gridy = 1;
        add(nameLabel, c);

        c.gridx = 1;
        add(nameField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(insertButton, c);

        c.gridx = 1;
        add(modifyButton, c);

        c.gridx = 0;
        c.gridy = 3;
        add(deleteButton, c);

        c.gridx = 1;
        add(displayButton, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setTitle("User Management");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "harika", "harika");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertUser() {
        try {
            String query = "INSERT INTO users (userid, name) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userIDField.getText());
            preparedStatement.setString(2, nameField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifyUser() {
        try {
            String query = "UPDATE users SET name=? WHERE userid=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameField.getText());
            preparedStatement.setString(2, userIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User modified successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteUser() {
        try {
            String query = "DELETE FROM users WHERE userid=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayUsers() {
        try {
            String query = "SELECT * FROM users";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"User ID", "Name"});

            while (resultSet.next()) {
                String userID = resultSet.getString("userid");
                String name = resultSet.getString("name");

                model.addRow(new Object[]{userID, name});
            }

            table.setModel(model);
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String userID = table.getValueAt(selectedRow, 0).toString();
            String name = table.getValueAt(selectedRow, 1).toString();

            userIDField.setText(userID);
            nameField.setText(name);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Users();
            }
        });
    }
}

