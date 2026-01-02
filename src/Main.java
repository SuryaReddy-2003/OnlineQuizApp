//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Welcome to Online Quiz Application");
//    }
//}
//import dao.UserDAO;
//import model.User;
//
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//
//        Scanner sc = new Scanner(System.in);
//        UserDAO userDAO = new UserDAO();
//
//        System.out.println("1. Register");
//        System.out.println("2. Login");
//        System.out.print("Choose option: ");
//        int choice = sc.nextInt();
//        sc.nextLine(); // consume newline
//
//        if (choice == 1) {
//            User user = new User();
//
//            System.out.print("Enter username: ");
//            user.setUsername(sc.nextLine());
//
//            System.out.print("Enter password: ");
//            user.setPassword(sc.nextLine());
//
//            user.setRole("USER");
//
//            userDAO.register(user);
//
//        } else if (choice == 2) {
//
//            System.out.print("Enter username: ");
//            String username = sc.nextLine();
//
//            System.out.print("Enter password: ");
//            String password = sc.nextLine();
//
//            if (userDAO.login(username, password)) {
//                System.out.println("Login successful!");
//            } else {
//                System.out.println("Invalid username or password");
//            }
//        } else {
//            System.out.println("Invalid choice");
//        }
//    }
//}

/*
import java.sql.*;
import java.util.Scanner;

public class Main {

    // 🔹 CHANGE THIS PATH ONLY IF YOUR DB LOCATION IS DIFFERENT
    private static final String DB_URL =
            "jdbc:sqlite:C:/Users/reddy/IdeaProjects/OnlineQuizApp/quiz.db";

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in);
             Connection conn = DriverManager.getConnection(DB_URL)) {

            System.out.println("Connecting to DB: " + DB_URL);

            // 🔍 DEBUG: SHOW ALL TABLES (VERY IMPORTANT)
            printTables(conn);

            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (choice == 2) {
                login(conn, sc);
            } else {
                System.out.println("Register not implemented yet.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 PRINT ALL TABLES (DEBUG METHOD)
    static void printTables(Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT name FROM sqlite_master WHERE type='table'"
        );

        System.out.println("---- TABLES IN DB ----");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        System.out.println("----------------------");
    }

    // 🔹 LOGIN METHOD
    static void login(Connection conn, Scanner sc) throws SQLException {

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.println("DEBUG username=[" + username + "]");
        System.out.println("DEBUG password=[" + password + "]");

        String sql =
                "SELECT id, role FROM users WHERE username=? AND password=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int userId = rs.getInt("id");
            String role = rs.getString("role");

            if ("ADMIN".equalsIgnoreCase(role)) {
                System.out.println("Welcome ADMIN");
                adminMenu(conn, sc, userId);
            } else {
                System.out.println("Welcome USER");
            }
        } else {
            System.out.println("Invalid username or password");
        }
    }

    // 🔹 ADMIN MENU
    static void adminMenu(Connection conn, Scanner sc, int adminId)
            throws SQLException {

        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Create Quiz");
            System.out.println("2. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                createQuiz(conn, sc, adminId);
            } else {
                break;
            }
        }
    }

    // 🔹 CREATE QUIZ
    static void createQuiz(Connection conn, Scanner sc, int adminId)
            throws SQLException {

        System.out.print("Enter Quiz Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Quiz Description: ");
        String desc = sc.nextLine();

        String sql =
                "INSERT INTO quizzes (title, description, created_by) VALUES (?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setInt(3, adminId);

        ps.executeUpdate();

        System.out.println("✅ Quiz Created Successfully!");
    }
}
*/



import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:sqlite:database/quiz.db";


    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in);
             Connection conn = DriverManager.getConnection(DB_URL)) {

            conn.createStatement().execute("PRAGMA busy_timeout = 10000");

            System.out.println("Connecting to DB: " + DB_URL);
            printTables(conn);

            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 2) {
                login(conn, sc);
            } else {
                System.out.println("Register not implemented.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= PRINT TABLES =================
    static void printTables(Connection conn) throws SQLException {
        ResultSet rs = conn.createStatement()
                .executeQuery("SELECT name FROM sqlite_master WHERE type='table'");
        System.out.println("---- TABLES IN DB ----");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        System.out.println("----------------------");
    }

    // ================= LOGIN =================
    static void login(Connection conn, Scanner sc) throws SQLException {

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement(
                "SELECT id, role FROM users WHERE username=? AND password=?"
        );
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int userId = rs.getInt("id");
            String role = rs.getString("role");

            if ("ADMIN".equalsIgnoreCase(role)) {
                System.out.println("Welcome ADMIN");
                adminMenu(conn, sc, userId);
            } else {
                System.out.println("Welcome USER");
                userMenu(conn, sc, userId);
            }
        } else {
            System.out.println("Invalid username or password");
        }
    }

    // ================= ADMIN MENU =================
    static void adminMenu(Connection conn, Scanner sc, int adminId)
            throws SQLException {

        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Create Quiz");
            System.out.println("2. Add Questions");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                createQuiz(conn, sc, adminId);
            } else if (choice == 2) {
                addQuestions(conn, sc);
            } else {
                break;
            }
        }
    }

    // ================= CREATE QUIZ =================
    static void createQuiz(Connection conn, Scanner sc, int adminId)
            throws SQLException {

        System.out.print("Enter Quiz Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Quiz Description: ");
        String desc = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO quizzes (title, description, created_by) VALUES (?, ?, ?)"
        );

        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setInt(3, adminId);

        ps.executeUpdate();

        System.out.println("✅ Quiz Created Successfully!");
    }

    // ================= ADD MULTIPLE QUESTIONS =================
    static void addQuestions(Connection conn, Scanner sc)
            throws SQLException {

        ResultSet rs = conn.createStatement()
                .executeQuery("SELECT id, title FROM quizzes");

        System.out.println("\nAvailable Quizzes:");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + ". " + rs.getString("title"));
        }

        System.out.print("Enter Quiz ID: ");
        int quizId = sc.nextInt();
        sc.nextLine();

        while (true) {
            System.out.print("\nEnter Question: ");
            String question = sc.nextLine();

            System.out.print("Option A: ");
            String optionA = sc.nextLine();

            System.out.print("Option B: ");
            String optionB = sc.nextLine();

            System.out.print("Option C: ");
            String optionC = sc.nextLine();

            System.out.print("Option D: ");
            String optionD = sc.nextLine();

            System.out.print("Correct Answer (A/B/C/D): ");
            String correct = sc.nextLine().toUpperCase();

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO questions (quiz_id, question, optionA, optionB, optionC, optionD, correctAnswer) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            ps.setInt(1, quizId);
            ps.setString(2, question);
            ps.setString(3, optionA);
            ps.setString(4, optionB);
            ps.setString(5, optionC);
            ps.setString(6, optionD);
            ps.setString(7, correct);

            ps.executeUpdate();

            System.out.println("✅ Question Added Successfully!");

            System.out.print("Add another question? (y/n): ");
            String choice = sc.nextLine();

            if (!choice.equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    // ================= USER MENU =================
    static void userMenu(Connection conn, Scanner sc, int userId)
            throws SQLException {

        while (true) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. Take Quiz");
            System.out.println("2. Logout");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 1) {
                takeQuiz(conn, sc, userId);
            } else {
                break;
            }
        }
    }

    // ================= TAKE QUIZ =================
    static void takeQuiz(Connection conn, Scanner sc, int userId)
            throws SQLException {

        ResultSet rsQuiz = conn.createStatement()
                .executeQuery("SELECT id, title FROM quizzes");

        System.out.println("\nAvailable Quizzes:");
        while (rsQuiz.next()) {
            System.out.println(rsQuiz.getInt("id") + ". " + rsQuiz.getString("title"));
        }

        System.out.print("Enter Quiz ID: ");
        int quizId = sc.nextInt();
        sc.nextLine();

        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM questions WHERE quiz_id=?"
        );
        ps.setInt(1, quizId);

        ResultSet rs = ps.executeQuery();

        int score = 0;
        int total = 0;

        while (rs.next()) {
            total++;

            System.out.println("\n" + rs.getString("question"));
            System.out.println("A. " + rs.getString("optionA"));
            System.out.println("B. " + rs.getString("optionB"));
            System.out.println("C. " + rs.getString("optionC"));
            System.out.println("D. " + rs.getString("optionD"));

            System.out.print("Your Answer: ");
            String ans = sc.nextLine().toUpperCase();

            if (ans.equals(rs.getString("correctAnswer"))) {
                score++;
            }
        }

        System.out.println("\n🎯 Your Score: " + score + "/" + total);

        PreparedStatement save = conn.prepareStatement(
                "INSERT INTO results (user_id, quiz_id, score) VALUES (?, ?, ?)"
        );
        save.setInt(1, userId);
        save.setInt(2, quizId);
        save.setInt(3, score);
        save.executeUpdate();

        System.out.println("✅ Result Saved Successfully!");
    }
}
