import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AptitudeQuizPerformanceAnalysis extends JFrame {
    private JTable table;

    public AptitudeQuizPerformanceAnalysis() {
        setTitle("Aptitude Quiz Performance Analysis");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"User ID", "Quiz Description", "Score", "Rank"}
        ));
        scrollPane.setViewportView(table);
    }

    public void fetchQuizPerformanceData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear previous data

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Connect to the database
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "harika";
            String password = "harika";
            connection = DriverManager.getConnection(url, username, password);

            // Execute the SQL query
            statement = connection.createStatement();
            String query = "SELECT u.userId, q.description, ud.score, " +
                    "CASE ud.score " +
                    "   WHEN (SELECT MAX(score) FROM UsersQuizData WHERE quizId = ud.quizId) THEN 1 " +
                    "   WHEN (SELECT MAX(score) FROM UsersQuizData WHERE quizId = ud.quizId AND score < (SELECT MAX(score) FROM UsersQuizData WHERE quizId = ud.quizId)) THEN 2 " +
                    "   ELSE 3 " +
                    "END AS rank " +
                    "FROM Users u " +
                    "JOIN UsersQuizData ud ON u.userId = ud.userId " +
                    "JOIN Quizzes q ON q.quizId = ud.quizId";

            resultSet = statement.executeQuery(query);

            // Populate the table with the retrieved data
            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = resultSet.getString("userId");
                row[1] = resultSet.getString("description");
                row[2] = resultSet.getString("score");
                row[3] = resultSet.getString("rank");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AptitudeQuizPerformanceAnalysis analysis = new AptitudeQuizPerformanceAnalysis();
                analysis.fetchQuizPerformanceData();
                analysis.setVisible(true);
            }
        });
    }
}
