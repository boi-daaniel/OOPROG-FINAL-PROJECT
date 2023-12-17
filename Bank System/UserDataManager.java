import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDataManager {
    private static List<User> userList = new ArrayList<>();
    private static File usersFile = new File("users.txt");

    // Load data method
    public static void loadUserDataFromFile() {
        // Load user data from file
        File usersFile = new File("users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(":");
                if (userData.length == 2) {
                    userList.add(new User(userData[0], userData[1]));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Save data method
    public static void saveUserData(User user, File usersFile) {
        // Save user data to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile, true))) {
            writer.write(user.getUsername() + ":" + user.getPassword());
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Save all user data method
    public static void saveAllUserData() {
        // Save all user data to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile))) {
            for (User user : userList) {
                writer.write(user.getUsername() + ":" + user.getPassword());
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
