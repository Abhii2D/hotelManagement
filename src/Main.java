import java.io.*;
import java.sql.*;
import java.sql.PreparedStatement.*;
import java.sql.Connection.*;
import java.sql.DriverManager.*;
import java.sql.ResultSet.*;

import java.util.Scanner;

public class Main  {
    public static void main(String[] args) throws FileNotFoundException {
        String url = "jdbc:mysql://localhost:3306/Image_database";
        String user = "root";
        String pass = "abhijeet@2002";
        String path = "C:\\Users\\Abhijeet Dhekane\\Pictures\\";
        //------ connection with mysql --------
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection done with Database");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Scanner sc = new Scanner(System.in);
        String sql = "select img_data from Image_table where img_id=?";
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection Established Successfully !!");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,1);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                byte[] imgdate = rs.getBytes("img_data");
                String imgpath = path+"NEww.png";
                OutputStream os = new FileOutputStream(imgpath);
                os.write(imgdate);
                System.out.println("Image RetrivedSuccesfully..........");
            }else{
                System.out.println("Image not found");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}