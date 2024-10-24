import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
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

        //------- Project 1 of JDBC ----------


        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection is done");
            while (true) {
                System.out.println();
                System.out.println("Hotel Management System");
                Scanner sc = new Scanner(System.in);
                System.out.println("1 : Reserve the Room");
                System.out.println("2 : View the Reservation");
                System.out.println("3 : Get Room Number");
                System.out.println("4 : UPdate the Reservation");
                System.out.println("5 : Delete Reservation");
                System.out.println("0 : Exit()");
                System.out.println("Enter the Choice : " );
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom( con,  sc);
                        break;
                    case 2:
                        viewReservation( con,  sc);
                        break;
                    case 3:
                        getReroom( con,  sc);
                        break;
                    case 4:
                        updateroom( con,  sc);
                        break;
                    case 5:
                        deleteReservation( con,  sc);
                        break;
                    case 0:
                        exit();
                        return;
                    default:
                        System.out.println("Enter valid choice : ");

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    private static void updateroom(Connection con, Scanner sc) {
        // Reservation_id, Guest_name, Room_number, Contact_number, Reservation_Date from reservation
        System.out.println("Enter Reservation Id: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (!restexit(con, id)) {
            System.out.println("Reservation not found!!");
            return;
        }

        System.out.println("Enter the Guest Name: ");
        String Name = sc.next(); // Make sure the name doesn't contain special characters

        System.out.println("Enter the New Room Number: ");
        int room = sc.nextInt();

        System.out.println("Enter Customer Number: ");
        String contact = sc.next();

        // Correct the SQL string with single quotes around strings
        String sql = "UPDATE reservation SET Guest_name = '" + Name + "', Room_number = " + room + ", Contact_number = '" + contact + "' WHERE Reservation_id = " + id;

        try (Statement st = con.createStatement()) {
            int row = st.executeUpdate(sql);
            if (row > 0) {
                System.out.println("Row Affected: " + row);
            } else {
                System.out.println("Row not affected, failed to update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


private static void deleteReservation(Connection con, Scanner sc){
//Reservation_id ,Guest_name,Room_number,Contact_number,Reservation_Date from reservation
try{
    System.out.println("Enter the Reservation id  : ");
    int id = sc.nextInt();
    if(!restexit(con,id)){
        return;
    }
    String sql = "delete from reservation where Reservation_id = "+ id;
    try(Statement st = con.createStatement()){
        st.executeUpdate(sql);
    }
}catch (SQLException e){
    e.printStackTrace();
}
    }

private static void getReroom(Connection con, Scanner sc){
  //  Reservation_id ,Guest_name,Room_number,Contact_number,Reservation_Date from reservation
    System.out.println("Enter the Reservation ID : ");
    int id = sc.nextInt();
    String sql = "select Guest_name ,Room_number,Contact_number,Reservation_Date from reservation where Reservation_id = "+ id;
    if(!restexit(con,id)){
        return;
    }
    try(Statement st = con.createStatement()){
       ResultSet set =  st.executeQuery(sql);
       while (set.next()){
           System.out.println("Reservation id : "+id+" Guest_name : " + set.getString("Guest_name")+" Room Number : " +set.getInt("Room_number")+ "  Contact : "
                   +set.getString("Contact_number")+" Reservation Dates : "+ set.getTimestamp("Reservation_Date").toString() +" ");
       }

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }

private static void viewReservation(Connection con, Scanner sc){
    String sql = "select Reservation_id ,Guest_name,Room_number,Contact_number,Reservation_Date from reservation;";
    try(Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)){
        System.out.println("Current Reservations are......");
        System.out.println("+-----------------------------------------------------------------------------------------------------------");
        System.out.println("|Reservation Id | Guest Name  | Reserved Room | Guest Contact | Reservation Date and Time");
        while(rs.next()){
           int id = rs.getInt("Reservation_id");
           String name = rs.getString("Guest_name");
           String contact = rs.getString("Contact_number");
           int room = rs.getInt("Room_number");
           String date = rs.getTimestamp("Reservation_Date").toString();

           System.out.printf("|%-14d|%-17s|%-15d|%-22s|%-20s|\n" , id,name,room,contact,date);
       }
        System.out.println("+-----------------------------------------------------------------------------------------------------------");
    }catch (SQLException e){
        e.printStackTrace();
    }
}

private static void reserveRoom(Connection con, Scanner sc) {

    try {
        System.out.println("Enter the Gues Name :");
        String name = sc.next();
        sc.nextLine();
        System.out.println("Enter the Room Number : ");
        int RoomNumber = sc.nextInt();
        System.out.println("Enter the Contact Number : ");
        String Contact = sc.next();
  //      String sql = "insert into reservation(Reservation_id,Guest_name,Room_number,Contact_number,Reservation_Date) values(" + name + "," + RoomNumber + "," + Contact + ",);";
        String sql = "INSERT INTO reservation (Guest_name, Room_number, Contact_number, Reservation_Date) " +
                "VALUES ('" + name + "', " + RoomNumber + ", '" + Contact + "', CURRENT_DATE());";

        try (Statement st = con.createStatement()) {
             st.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

private static boolean restexit(Connection con,int id){
        try{
            String sql = " select Reservation_id from reservation where Reservation_id = "+ id;
            try(Statement st = con.createStatement()){
                ResultSet r = st.executeQuery(sql);
                return  r.next();
            }catch (SQLException e){
                e.printStackTrace();
            return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
}

public static void exit()throws InterruptedException{
    System.out.print("Exiting System");
   int i = 5;
   while(i!=0){
       System.out.print(".");
       Thread.sleep(450);
       i--;
   }
    System.out.println();
    System.out.println("Thank You!");
}


// -------------- first Create the hotel datbase in mysql and use it after that create table and save it
// ------------- later add sql java connector jar file to project external library
//    create database hotel;
//    use hotel;
//    create table reservation(
//            Reservation_id int auto_increment primary key,
//            Guest_name varchar(30) not null,
//    Room_number int not null,
//    Contact_number varchar(40) not null,
//    Reservation_Date timestamp
//);
//    select * from reservation;


}
