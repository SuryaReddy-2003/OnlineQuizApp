//package db;
//import java.sql.Connection;
//import java.sql.DriverManager;
//public class DBConnection {
//        public static Connection getConnection() {
//            try {
//                return DriverManager.getConnection("jdbc:sqlite:quiz.db");
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//}

package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String DB_URL =
            "jdbc:sqlite:C:/Users/reddy/quiz.db";

    public static Connection getConnection() {
        try {
            System.out.println("Connecting to DB: " + DB_URL);
            return DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

