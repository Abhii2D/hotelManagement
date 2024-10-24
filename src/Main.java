import java.sql.*;
import java.sql.PreparedStatement.*;
import java.sql.Connection.*;
import java.sql.DriverManager.*;
import java.sql.ResultSet.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hotel";
        String user = "root";
        String pass = "abhijeet@2002";

        //------ connection with mysql --------
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection done with Database");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Scanner sc = new Scanner(System.in);
        //String sql = "insert into reservation
        // (Reservation_id,Guest_name,Room_number,Contact_number,Reservation_Date)
        // values(?,?,?,?,?);";
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = con.prepareStatement("sql");




        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}