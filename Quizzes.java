import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.table.DefaultTableModel;

public class Quizzes extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel quizIDLabel, descriptionLabel, noOfQuestionsLabel;
    private JTextField quizIDField, descriptionField, noOfQuestionsField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private JTable table;

    public Quizzes() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        quizIDLabel = new JLabel("Quiz ID:");
        descriptionLabel = new JLabel("Description:");
        noOfQuestionsLabel = new JLabel("No. of Questions:");

        quizIDField = new JTextField(10);
        descriptionField = new JTextField(10);
        noOfQuestionsField = new JTextField(10);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertQuiz();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyQuiz();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteQuiz();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayQuizzes();
            }
        });

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectQuiz();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setVisible(true);
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        add(quizIDLabel, c);

        c.gridx = 1;
        add(quizIDField, c);

        c.gridx = 0;
        c.gridy = 1;
        add(descriptionLabel, c);

        c.gridx = 1;
        add(descriptionField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(noOfQuestionsLabel, c);

        c.gridx = 1;
        add(noOfQuestionsField, c);

        c.gridx = 0;
        c.gridy = 3;
        add(insertButton, c);

        c.gridx = 1;
        add(modifyButton, c);

        c.gridx = 0;
        c.gridy = 4;
        add(deleteButton, c);

        c.gridx = 1;
        add(displayButton, c);

        c.gridx = 0;
        c.gridy = 5;
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

        setTitle("Quiz Management");
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

    private void insertQuiz() {
        try {
            String query = "INSERT INTO quizzes (quizid, description, noofquestions) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, quizIDField.getText());
            preparedStatement.setString(2, descriptionField.getText());
            preparedStatement.setInt(3, Integer.parseInt(noOfQuestionsField.getText()));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Quiz inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifyQuiz() {
        try {
            String query = "UPDATE quizzes SET description=?, noofquestions=? WHERE quizid=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, descriptionField.getText());
            preparedStatement.setInt(2, Integer.parseInt(noOfQuestionsField.getText()));
            preparedStatement.setString(3, quizIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Quiz modified successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteQuiz() {
        try {
            String query = "DELETE FROM quizzes WHERE quizid=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, quizIDField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Quiz deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayQuizzes() {
        try {
            String query = "SELECT * FROM quizzes";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Quiz ID", "Description", "No. of Questions"});

            while (resultSet.next()) {
                String quizID = resultSet.getString("quizid");
                String description = resultSet.getString("description");
                int noOfQuestions = resultSet.getInt("noofquestions");

                model.addRow(new Object[]{quizID, description, noOfQuestions});
            }

            table.setModel(model);
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectQuiz() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String quizID = table.getValueAt(selectedRow, 0).toString();
            String description = table.getValueAt(selectedRow, 1).toString();
            String noOfQuestions = table.getValueAt(selectedRow, 2).toString();

            quizIDField.setText(quizID);
            descriptionField.setText(description);
            noOfQuestionsField.setText(noOfQuestions);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Quizzes();
            }
        });
    }
}
