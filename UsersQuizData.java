import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.table.DefaultTableModel;

public class UsersQuizData extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel userQuizIDLabel, userIDLabel, quizIDLabel, scoreLabel;
    private JTextField userQuizIDField, userIDField, quizIDField, scoreField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private JTable table;

    public UsersQuizData() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        userQuizIDLabel = new JLabel("User Quiz ID:");
        userIDLabel = new JLabel("User ID:");
        quizIDLabel = new JLabel("Quiz ID:");
        scoreLabel = new JLabel("Score:");

        userQuizIDField = new JTextField(10);
        userIDField = new JTextField(10);
        quizIDField = new JTextField(10);
        scoreField = new JTextField(10);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertUserQuizData();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyUserQuizData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUserQuizData();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayUserQuizData();
            }
        });

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectUserQuizData();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setVisible(true);
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        add(userQuizIDLabel, c);

        c.gridx = 1;
        add(userQuizIDField, c);

        c.gridx = 0;
        c.gridy = 1;
        add(userIDLabel, c);

        c.gridx = 1;
        add(userIDField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(quizIDLabel, c);

        c.gridx = 1;
        add(quizIDField, c);

        c.gridx = 0;
        c.gridy = 3;
        add(scoreLabel, c);

        c.gridx = 1;
        add(scoreField, c);

        c.gridx = 0;
        c.gridy = 4;
        add(insertButton, c);

        c.gridx = 1;
        add(modifyButton, c);

        c.gridx = 0;
        c.gridy = 5;
        add(deleteButton, c);

        c.gridx = 1;
        add(displayButton, c);

        c.gridx = 0;
        c.gridy = 6;
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

        setTitle("Users Quiz Data Management");
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

    private void insertUserQuizData() {
        try {
            String query = "INSERT INTO usersquizdata (userquizid, userid, quizid, score) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userQuizIDField.getText());
            preparedStatement.setString(2, userIDField.getText());
            preparedStatement.setString(3, quizIDField.getText());
            preparedStatement.setInt(4, Integer.parseInt(scoreField.getText()));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Quiz Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifyUserQuizData() {
        try {
            String query = "UPDATE usersquizdata SET userid=?, quizid=?, score=? WHERE userquizid=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userIDField.getText());
            preparedStatement.setString(2, quizIDField.getText());
            preparedStatement.setInt(3, Integer.parseInt(scoreField.getText()));
            preparedStatement.setString(4, userQuizIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Quiz Data modified successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteUserQuizData() {
        try {
            String query = "DELETE FROM usersquizdata WHERE userquizid=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userQuizIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Quiz Data deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayUserQuizData() {
        try {
            String query = "SELECT * FROM usersquizdata";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"User Quiz ID", "User ID", "Quiz ID", "Score"});

            while (resultSet.next()) {
                String userQuizID = resultSet.getString("userquizid");
                String userID = resultSet.getString("userid");
                String quizID = resultSet.getString("quizid");
                int score = resultSet.getInt("score");

                model.addRow(new Object[]{userQuizID, userID, quizID, score});
            }

            table.setModel(model);
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectUserQuizData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String userQuizID = table.getValueAt(selectedRow, 0).toString();
            String userID = table.getValueAt(selectedRow, 1).toString();
            String quizID = table.getValueAt(selectedRow, 2).toString();
            String score = table.getValueAt(selectedRow, 3).toString();

            userQuizIDField.setText(userQuizID);
            userIDField.setText(userID);
            quizIDField.setText(quizID);
            scoreField.setText(score);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UsersQuizData();
            }
        });
    }
}
