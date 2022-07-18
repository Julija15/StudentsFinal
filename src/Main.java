import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    private static final String host = "127.0.0.1";
    private static final String port = "5432";
    private static final String base = "postgres";
    private static final String user = "postgres";
    private static final String pass = "root";
    private static final String url = "jdbc:postgresql://" + host + ":" + port + "/" + base;


    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(url, user, pass);

        while (true) {

            System.out.println("Enter the transaction number: " + "\n" + "1. Add student");
            System.out.println("2. Deleted student");
            System.out.println("3. Edit data");
            System.out.println("4. Find a student");
            System.out.println("5. Exit the program ");

            int command = sc.nextInt();

            if (command == 1) {
                System.out.println("Enter the name of the student");
                sc.nextLine();
                String name = sc.nextLine();
                System.out.println("Enter the surname of the student");
                String surname = sc.nextLine();
                System.out.println("Add a grade");
                int grade1 = sc.nextInt();
                System.out.println("Add a grade");
                int grade2 = sc.nextInt();
                System.out.println("Add a grade");
                int grade3 = sc.nextInt();
                PreparedStatement st = connection.prepareStatement("INSERT INTO students (name , surname,grade1,grade2,grade3 ) VALUES (?, ?,?,?,?)");
                st.setString(1, name);
                st.setString(2, surname);
                st.setInt(3, grade1);
                st.setInt(4, grade2);
                st.setInt(5, grade3);
                st.executeUpdate();
                st.close();

            } else if (command == 2) {
                System.out.println("Enter the surname of the student whose data you want to deleted");
                sc.nextLine();
                String surname = sc.nextLine();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM students WHERE surname = ?");
                ps.setString(1, surname);
                ps.execute();
                ps.close();

            } else if (command == 3) {
                System.out.println("Enter the surname of the student whose data you want to edit");
                sc.nextLine();
                String surname = sc.nextLine();
                PreparedStatement ps1 = connection.prepareStatement("UPDATE students SET name  = ?, grade1 = ?, grade2 = ?, grade3 = ? WHERE surname = ? ");
                System.out.println("Enter the name of the student whose data you want to edit");
                String name = sc.nextLine();
                System.out.println("Enter the grade1 of the student whose data you want to edit");
                Integer grade1 = sc.nextInt();
                System.out.println("Enter the grade2 of the student whose data you want to edit");
                Integer grade2 = sc.nextInt();
                System.out.println("Enter the grade3 of the student whose data you want to edit");
                Integer grade3 = sc.nextInt();
                ps1.setString(1, name);
                ps1.setInt(2, grade1);
                ps1.setInt(3, grade2);
                ps1.setInt(4, grade3);
                ps1.setString(5, surname);
                ps1.executeUpdate();


            } else if (command == 4) {

                System.out.println("Enter the name of the student whose details you want to find");
                sc.nextLine();
                String surname = sc.nextLine();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE surname =?");
                statement.setString(1, surname);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    surname = resultSet.getString("surname");
                    String grade1 = resultSet.getString("grade1");
                    String grade2 = resultSet.getString("grade2");
                    String grade3 = resultSet.getString("grade3");
                    System.out.printf("%s %s %s %s %s %s \n\r", id, name, surname, grade1, grade2, grade3);
                }
            } else if (command == 5) {
                System.exit(0);
            } else {
                System.err.println("Command unrecognized");

            }
        }
    }
}