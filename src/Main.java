import java.sql.PreparedStatement.*;
import  java.sql.Connection.*;
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

        
    }
}