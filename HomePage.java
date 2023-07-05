import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JButton retrieveMarksButton;

    public HomePage() {
        // Set frame properties
        setTitle("APTITUDE QUIZ PERFORMANCE ANALYSIS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create label
        JLabel welcomeLabel = new JLabel("Welcome to Aptitude Quiz Performance Analysis");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        // Create panel for the button
        /*JPanel buttonPanel = new JPanel();
        retrieveMarksButton = new JButton("");
        buttonPanel.add(retrieveMarksButton);*/

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create menus
        JMenu userMenu = new JMenu("Users Details");
        JMenu quizMenu = new JMenu("Quiz Details");
        JMenu usersquizdataMenu = new JMenu("User's Quiz Details");
        //JMenu semesterMenu = new JMenu("Semester Details");
       // JMenu gradeMenu = new JMenu("Grade Details");

        // Create menu item for student menu
        JMenuItem viewuserDetails = new JMenuItem("View Users Details");
        viewuserDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Users();
            }
        });

        // Create menu item for course menu
        JMenuItem viewquizDetails = new JMenuItem("View Quiz Details");
        viewquizDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Quizzes();
            }
        });

        // Create menu item for enrollment menu
        JMenuItem viewusersquizdataDetails = new JMenuItem("View Users Quiz Data Details");
        viewusersquizdataDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UsersQuizData();
            }
        });

        // Create menu item for semester menu
        /*JMenuItem viewSemesterDetails = new JMenuItem("View Semester Details");
        viewSemesterDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MoocsSemesterGUI();
            }
        });*/

        // Create menu item for grade menu
        /*JMenuItem viewGradeDetails = new JMenuItem("View Grade Details");
        viewGradeDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MoocsGradeManagement();
            }
        });*/

        // Add menu items to respective menus
        userMenu.add(viewuserDetails);
        quizMenu.add(viewquizDetails);
        usersquizdataMenu.add(viewusersquizdataDetails);
        //semesterMenu.add(viewSemesterDetails);
        //gradeMenu.add(viewGradeDetails);

        // Add menus to the menu bar
        menuBar.add(userMenu);
        menuBar.add(quizMenu);
        menuBar.add(usersquizdataMenu);
        //menuBar.add(semesterMenu);
        //menuBar.add(gradeMenu);

        // Set the menu bar
        setJMenuBar(menuBar);

        // Add the button panel to the frame
        //add(buttonPanel, BorderLayout.CENTER);

        // Set button action for "Retrieve Marks"
       /* retrieveMarksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Retreive();
            }
        });*/

        // Add window listener to handle maximizing the window
        addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                    System.out.println("Window maximized");
                } else {
                    System.out.println("Window not maximized");
                }
            }
        });

        // Set frame size and visibility
        setSize(800, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
