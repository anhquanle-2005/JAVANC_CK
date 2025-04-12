

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection {
    private static final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLSP;encrypt=true;trustServerCertificate=true";
    private static final String DATABASE_USER = "sa";
    private static final String DATABASE_PASSWORD = "12345"; // Thay đổi mật khẩu này theo mật khẩu SQL Server của bạn

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("Kết nối database thành công!");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
