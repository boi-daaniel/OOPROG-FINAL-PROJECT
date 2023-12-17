import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    // Variables
    private String email;
    private String address;
    private String phone;
    private String username;
    private String password;
    private double balance;
    private List<String> transactionHistory;

    // Constructor
    public User(String username, String password) {
        // Initialize User object
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        this.email = "";
        this.address = "";
        this.phone = "";
    }

    // Getters and setters for user properties
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    // Methods for Deposit, Withdraw

    public void deposit(double amount) {
        // Deposit money into the account
        balance += amount;
        recordTransaction("[DEPOSIT]", amount);
    }

    public boolean withdraw(double amount) {
        // Withdraw money from the account
        if (amount > balance) {
            return false; // Insufficient funds
        }
        balance -= amount;
        recordTransaction("[WITHDRAWAL]", amount);
        return true;
    }

    // Record, Perform, Load, Save Transaction History Methods

    public void recordTransaction(String type, double amount) {
        // Record transaction history
        String transaction = type + " - Amount: P" + amount;
        transactionHistory.add(transaction);
    }

    public void performTransaction(String type, double amount) {
        // Perform a transaction
        if (transactionHistory == null) {
            loadTransactionHistory();
        }
        recordTransaction(type, amount);
    }

    public void loadTransactionHistory() {
        // Load transaction history from file
        String transactionHistoryFileName = getUsername() + "_transactions.txt";
        File transactionHistoryFile = new File(transactionHistoryFileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(transactionHistoryFile))) {
            transactionHistory.clear(); // Clear existing transaction history

            String line;
            while ((line = reader.readLine()) != null) {
                transactionHistory.add(line); // Add each line as a transaction
            }
        } catch (FileNotFoundException e) {
            // File not found, but it's okay. Just proceed without displaying an error
            // message.
        } catch (IOException e) {
            // Other IOException, print the stack trace for debugging purposes
            e.printStackTrace();
        }
    }

    public void saveTransactionHistory() {
        // Save transaction history to file
        File transactionHistoryFile = new File(getUsername() + "_transactions.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionHistoryFile))) {
            for (String transaction : getTransactionHistory()) {
                writer.write(transaction);
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Profile Methods

    public void loadProfileInfo() {
        // Load user profile information from file
        String profileFileName = getUsername() + "_profile.txt";
        File profileFile = new File(profileFileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(profileFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into label and value
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String label = parts[0].trim();
                    String value = parts[1].trim();

                    // Set profile information based on label
                    if (label.equals("Email")) {
                        setEmail(value);
                    } else if (label.equals("Address")) {
                        setAddress(value);
                    } else if (label.equals("Phone")) {
                        setPhone(value);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File not found, but it's okay. Just proceed without displaying an error
            // message.
        } catch (IOException e) {
            // Other IOException, print the stack trace for debugging purposes
            e.printStackTrace();
        }
    }

    public void saveProfileInfo() {
        // Save user profile information to file
        File profileFile = new File(getUsername() + "_profile.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(profileFile))) {
            // Write additional profile information to the file
            writer.write("Email: " + getEmail());
            writer.newLine();
            writer.write("Address: " + getAddress());
            writer.newLine();
            writer.write("Phone: " + getPhone());
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}// End of Class
