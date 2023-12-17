import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BankManagementSystemGUI {
    private static JFrame mainFrame;
    static JPanel mainPanel;
    private static CardLayout cardLayout;
    private static List<User> userList = new ArrayList<>();
    private static User currentUser;
    private static File usersFile = new File("users.txt");

    public static void createAndShowGUI() {
        mainFrame = new JFrame("Bank Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPANEL to set the background color of each sides of the logo blue
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        Color color = new Color(0, 74, 173);
        backgroundPanel.setBackground(color);

        mainFrame.setContentPane(backgroundPanel);

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Create panels for login, registration, and main menu
        createLoginPanel();
        createRegisterPanel();
        createMainMenu();

        ImageIcon icon = new ImageIcon("logo.png");
        Image img = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel imageLabel = new JLabel(scaledIcon);

        // Create a separate panel for holding the image label
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false); // Set panel to be transparent
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Create a container panel to hold both the image panel and the main panel with
        // CardLayout
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setOpaque(false); // Set panel to be transparent
        containerPanel.add(imagePanel, BorderLayout.NORTH);
        containerPanel.add(mainPanel, BorderLayout.CENTER);

        backgroundPanel.add(containerPanel);

        mainFrame.setSize(650, 530);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // Show the login panel by default
        cardLayout.show(mainPanel, "LoginPanel");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (currentUser != null) {
                currentUser.saveTransactionHistory();
                currentUser.saveProfileInfo();
            }
            saveAllUserData();
        }));
    }

    private static void saveAllUserData() {
    }

    // LOGIN Method
    private static void createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        Color loginPanelColor = new Color(0, 74, 173);
        loginPanel.setBackground(loginPanelColor);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        Font labelFont = new Font("Monaco", Font.BOLD, 17);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(labelFont);
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(labelFont);
        JPasswordField passwordField = new JPasswordField(20);

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        Color registerColor = new Color(0, 41, 96);
        Color loginColor = new Color(179, 115, 5);
        Font buttonFont = new Font("Monaco", Font.BOLD, 15);

        // LOGIN BUTTON
        loginButton.setFont(buttonFont);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(loginColor);
        loginButton.setFocusable(false);
        loginButton.setBorder(BorderFactory.createEtchedBorder());

        // REGISTER BUTTON
        registerButton.setFont(buttonFont);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(registerColor);
        registerButton.setFocusable(false);
        registerButton.setBorder(BorderFactory.createEtchedBorder());

        Dimension buttonSize = new Dimension(150, 150);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);

        // Adding FocusListener to change JTextField's border color
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                usernameField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(username, password)) {
                cardLayout.show(mainPanel, "MainMenu"); // Switch to the main menu
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Invalid username/password combination.");
            }
        });

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(registerButton);
        loginPanel.add(loginButton);

        mainPanel.add(loginPanel, "Login");
    }

    private static boolean validateLogin(String username, String password) {
        // Validate user login credentials
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                currentUser.loadTransactionHistory();
                currentUser.loadProfileInfo(); // Load additional profile information
                return true;
            }
        }
        return false;
    }

    // Register Method

    private static void createRegisterPanel() {
        // Create register panel with components and actions
        JPanel registerPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Font labelFont = new Font("Monaco", Font.BOLD, 14);

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField();

        // Set Font
        idLabel.setFont(labelFont);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        confirmPasswordLabel.setFont(labelFont);

        // Set Font Color
        idLabel.setForeground(Color.BLACK);
        usernameLabel.setForeground(Color.BLACK);
        passwordLabel.setForeground(Color.BLACK);
        confirmPasswordLabel.setForeground(Color.BLACK);

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Go Back to Login");

        Color registerColor = new Color(179, 115, 5);
        Color loginColor = new Color(0, 74, 173);
        Font buttonFont = new Font("Monaco", Font.BOLD, 15);

        // LOGIN BUTTON
        loginButton.setFont(buttonFont);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(loginColor);
        loginButton.setFocusable(false);
        loginButton.setBorder(BorderFactory.createEtchedBorder());

        // REGISTER BUTTON
        registerButton.setFont(buttonFont);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(registerColor);
        registerButton.setFocusable(false);
        registerButton.setBorder(BorderFactory.createEtchedBorder());

        idField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                idField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                idField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });

        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                usernameField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });

        confirmPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                confirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                confirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (id.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()
                        || confirmPassword.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Please fill in all fields.");
                    return;
                }

                // Check if the username already exists
                if (isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "Username already exists. Please choose a different username.");
                } else {
                    // Register the new user
                    if (registerUser(username, password)) {
                        JOptionPane.showMessageDialog(mainFrame, "Registration successful!");
                        cardLayout.show(mainPanel, "Login");
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Error registering user.");
                    }
                }
            }

            private boolean registerUser(String username, String password) {
                return false;
            }

            private boolean isUsernameTaken(String username) {
                return false;
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
            }
        });

        registerPanel.add(idLabel);
        registerPanel.add(idField);
        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(confirmPasswordLabel);
        registerPanel.add(confirmPasswordField);
        registerPanel.add(registerButton);
        registerPanel.add(loginButton);

        mainPanel.add(registerPanel, "Register");
    }

    // Main Menu Method
    private static void createMainMenu() {
        // Create main menu panel with components and actions
        JPanel mainMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        mainMenuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transactionHistoryButton = new JButton("Transaction History");
        JButton accountBalanceButton = new JButton("Account Balance");
        JButton profileButton = new JButton("Profile");
        JButton logoutButton = new JButton("Logout");

        // Set font for buttons
        Font buttonFont = new Font("Monaco", Font.BOLD, 15);
        depositButton.setFont(buttonFont);
        withdrawButton.setFont(buttonFont);
        transactionHistoryButton.setFont(buttonFont);
        accountBalanceButton.setFont(buttonFont);
        profileButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

        // Set background color for buttons
        depositButton.setBackground(Color.LIGHT_GRAY);
        withdrawButton.setBackground(Color.LIGHT_GRAY);
        transactionHistoryButton.setBackground(Color.LIGHT_GRAY);
        accountBalanceButton.setBackground(Color.LIGHT_GRAY);
        profileButton.setBackground(Color.LIGHT_GRAY);
        logoutButton.setBackground(Color.LIGHT_GRAY);

        // Set focusable to false to remove text border in button
        depositButton.setFocusable(false);
        withdrawButton.setFocusable(false);
        transactionHistoryButton.setFocusable(false);
        accountBalanceButton.setFocusable(false);
        profileButton.setFocusable(false);
        logoutButton.setFocusable(false);

        // Set border
        depositButton.setBorder(BorderFactory.createEtchedBorder());
        withdrawButton.setBorder(BorderFactory.createEtchedBorder());
        transactionHistoryButton.setBorder(BorderFactory.createEtchedBorder());
        accountBalanceButton.setBorder(BorderFactory.createEtchedBorder());
        profileButton.setBorder(BorderFactory.createEtchedBorder());
        logoutButton.setBorder(BorderFactory.createEtchedBorder());

        // Set preferred sizes for the buttons
        int buttonWidth = 200; // Adjust the width as needed
        int buttonHeight = 50; // Adjust the height as needed

        depositButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        withdrawButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        transactionHistoryButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        accountBalanceButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        profileButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        logoutButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        // Set the icon for the depositButton and fit it within the button
        depositButton.setMargin(new Insets(10, 30, 10, 30));

        // Action listeners for menu buttons
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action for deposit button
                performDeposit();
            }

            private void performDeposit() {
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action for withdraw button
                performWithdraw();
            }

            private void performWithdraw() {
            }
        });

        transactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null) {
                    currentUser.loadTransactionHistory();
                    List<String> transactions = currentUser.getTransactionHistory();

                    // Display transactions in a dialog
                    JTextArea textArea = new JTextArea();
                    for (String transaction : transactions) {
                        textArea.append(transaction + "\n");
                    }

                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(300, 200));

                    JOptionPane.showMessageDialog(mainFrame, scrollPane, "Transaction History",
                            JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "No user logged in.");
                }
            }
        });

        accountBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action for account balance button
                showAccountBalance();
            }

            private void showAccountBalance() {
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action for profile button
                showProfile();
            }

            private void showProfile() {
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action for logout button
                performLogout();
            }

            private void performLogout() {

            }
        });

        mainMenuPanel.add(depositButton);
        mainMenuPanel.add(withdrawButton);
        mainMenuPanel.add(transactionHistoryButton);
        mainMenuPanel.add(accountBalanceButton);
        mainMenuPanel.add(profileButton);
        mainMenuPanel.add(logoutButton);

        mainPanel.add(mainMenuPanel, "MainMenu");
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Center the frame on the screen
        mainFrame.setVisible(true);
    }
}
