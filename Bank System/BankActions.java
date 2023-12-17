import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class BankActions {
    private static JFrame mainFrame;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;
    private static List<User> userList = new ArrayList<>();
    private static User currentUser;
    private static File usersFile = new File("users.txt");

    public static boolean validateLogin(String username, String password) {
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

    public static boolean isUsernameTaken(String username) {
        // Check if username already exists
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean registerUser(String username, String password) {
        // Register a new user
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile, true))) {
            // Writing user data to the file
            writer.write(username + ":" + password);
            writer.newLine();

            // Add the new user to the userList
            userList.add(new User(username, password));

            // Close the writer to save changes
            writer.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Financial Transaction Methods
    public static void performDeposit() {
        // Perform deposit action
        if (currentUser != null) {
            String amountString = JOptionPane.showInputDialog(mainFrame, "Enter amount to deposit:");
            if (amountString != null && !amountString.trim().isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountString);
                    if (amount > 0) {
                        currentUser.deposit(amount); // Update balance with deposited amount
                        JOptionPane.showMessageDialog(mainFrame, "Deposited amount: P" + amount);
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Please enter a valid positive amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Please enter a valid numeric amount.");
                }
            } else if (amountString != null && amountString.trim().isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter an amount.");
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No user logged in.");
        }
    }

    public static void performWithdraw() {
        // Perform withdraw action
        if (currentUser != null) {
            String amountString = JOptionPane.showInputDialog(mainFrame, "Enter amount to withdraw:");
            if (amountString != null && !amountString.trim().isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountString);
                    if (amount > 0) {
                        if (amount <= currentUser.getBalance()) {
                            currentUser.withdraw(amount); // Update balance with withdrawn amount
                            JOptionPane.showMessageDialog(mainFrame, "Withdrawn amount: P" + amount);
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Insufficient funds.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Please enter a valid positive amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Please enter a valid numeric amount.");
                }
            } else if (amountString != null && amountString.trim().isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter an amount.");
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No user logged in.");
        }
    }

    public static void showAccountBalance() {
        // Display account balance
        JOptionPane.showMessageDialog(mainFrame, "Current Balance: P" + currentUser.getBalance());
    }

    // User Profile Methods
    public static void showProfile() {
        if (currentUser != null) {
            JPanel profilePanel = new JPanel(new GridLayout(6, 2, 10, 10));
            profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            Font labelFont = new Font("Monaco", Font.BOLD, 14);

            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField(currentUser.getUsername());
            JLabel emailLabel = new JLabel("Email:");
            JTextField emailField = new JTextField();
            JLabel addressLabel = new JLabel("Address:");
            JTextField addressField = new JTextField();
            JLabel phoneLabel = new JLabel("Phone:");
            JTextField phoneField = new JTextField();

            nameField.setText(currentUser.getUsername());
            emailField.setText(currentUser.getEmail());
            addressField.setText(currentUser.getAddress());
            phoneField.setText(currentUser.getPhone());

            // Set Fonts
            nameLabel.setFont(labelFont);
            emailLabel.setFont(labelFont);
            addressLabel.setFont(labelFont);
            phoneLabel.setFont(labelFont);

            // Set Font Color
            nameLabel.setForeground(Color.BLACK);
            emailLabel.setForeground(Color.BLACK);
            addressLabel.setForeground(Color.BLACK);
            phoneLabel.setForeground(Color.BLACK);

            JButton editButton = new JButton("Edit");
            JButton saveButton = new JButton("Save");
            JButton backButton = new JButton("Back to Menu");

            // BUTTON FONTS
            editButton.setFont(labelFont);
            saveButton.setFont(labelFont);
            backButton.setFont(labelFont);

            // BUTTON FONT COLORS
            editButton.setForeground(Color.WHITE);
            saveButton.setForeground(Color.WHITE);
            backButton.setForeground(Color.BLACK);

            Color editButtonColor = new Color(0, 74, 173);
            Color saveButtonColor = new Color(179, 115, 5);
            Color backButtonColor = new Color(153, 153, 153);

            editButton.setBackground(editButtonColor);
            saveButton.setBackground(saveButtonColor);
            backButton.setBackground(backButtonColor);

            editButton.setFocusable(false);
            saveButton.setFocusable(false);
            backButton.setFocusable(false);

            editButton.setBorder(BorderFactory.createEtchedBorder());
            saveButton.setBorder(BorderFactory.createEtchedBorder());
            backButton.setBorder(BorderFactory.createEtchedBorder());

            nameField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    nameField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    nameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            });

            emailField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    emailField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    emailField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            });

            addressField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    addressField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    addressField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            });

            phoneField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    phoneField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    phoneField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            });

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Enable editing of profile fields
                    nameField.setEditable(true);
                    emailField.setEditable(true);
                    addressField.setEditable(true);
                    phoneField.setEditable(true);
                    // Show the save button
                    saveButton.setVisible(true);
                }
            });

            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Save changes and display updated profile
                    currentUser.setUsername(nameField.getText());
                    currentUser.setEmail(emailField.getText());
                    currentUser.setAddress(addressField.getText());
                    currentUser.setPhone(phoneField.getText());

                    // Save the updated profile info
                    currentUser.saveProfileInfo();

                    // Display updated profile details
                    String updatedInfo = "Name: " + currentUser.getUsername() + "\n"
                            + "Email: " + currentUser.getEmail() + "\n"
                            + "Address: " + currentUser.getAddress() + "\n"
                            + "Phone: " + currentUser.getPhone();
                    JOptionPane.showMessageDialog(mainFrame, "Profile updated!");

                    // Disable editing of profile fields and hide the save button after saving
                    nameField.setEditable(false);
                    emailField.setEditable(false);
                    addressField.setEditable(false);
                    phoneField.setEditable(false);
                    saveButton.setVisible(false);
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(mainPanel, "MainMenu");
                }
            });

            // Restrict phoneField to accept only numeric input
            phoneField.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                        e.consume();
                    }
                }
            });

            // Initially set fields as uneditable
            nameField.setEditable(false);
            emailField.setEditable(false);
            addressField.setEditable(false);
            phoneField.setEditable(false);
            // Initially hide the save button
            saveButton.setVisible(false);

            profilePanel.add(nameLabel);
            profilePanel.add(nameField);
            profilePanel.add(emailLabel);
            profilePanel.add(emailField);
            profilePanel.add(addressLabel);
            profilePanel.add(addressField);
            profilePanel.add(phoneLabel);
            profilePanel.add(phoneField);
            profilePanel.add(editButton);
            profilePanel.add(saveButton);
            profilePanel.add(backButton);

            mainPanel.add(profilePanel, "ProfilePanel");
            cardLayout.show(mainPanel, "ProfilePanel");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No user logged in.");
        }
    }

    // Logout and Clearing User Profile
    public static void performLogout() {
        if (currentUser != null) {
            int result = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to log out?",
                    "Logout Confirmation", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                currentUser.saveTransactionHistory();
                currentUser.saveProfileInfo(); // Save additional profile information
                UserDataManager.saveAllUserData();
                currentUser = null;
                cardLayout.show(mainPanel, "Login");

                // Clear the profile information of the current user when logging out
                clearCurrentUserProfileInfo();
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No user logged in.");
        }
    }

    public static void clearCurrentUserProfileInfo() {
        // Clear current user's profile info
        if (currentUser != null) {
            currentUser.setEmail("");
            currentUser.setAddress("");
            currentUser.setPhone("");
        }
    }
}
