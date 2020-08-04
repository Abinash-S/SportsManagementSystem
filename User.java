package sportsmanagementsystem;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;
class User {
    String Name;
    String college;
    String city;
    String Email;
    String Password;
    static Scanner s=new Scanner(System.in);
    public User(String Name,String college,String city,String Email,String Password){
        this.Name=Name;
        this.college=college;
        this.city=city;
        this.Email=Email;
        this.Password=Password;
    }
    public User(String Email,String password){
        this.Email=Email;
        this.Password=password;
    }
    static User user;
    public static void signIn(Connection con) throws SQLException{
        try{
            System.out.println("Sign In : ");
            System.out.print("\tEmail : ");
            String email=s.nextLine();
            System.out.print("\tPassword : ");
            String password=s.nextLine();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT PASSWORD FROM \"User\" WHERE EMAIL='"+email+"'");
            if(rs.next()){
                if(password.equals(rs.getString(1))){
                    user=new User(email,password);
                    System.out.println("Login Successfull!!!\n");
                    System.out.println("WELCOME TO SPORTS MANAGEMENT SYSTEM");
                    userOptions(con);
                }
                else{
                    System.out.println("Invalid Email or Password");
                }
            }
            else{
                System.out.println("Invalid Email or Password");
            }
            st.close();
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void signUp(Connection con) throws SQLException{
        try{
            System.out.println("Sign Up : ");
            System.out.print("\tName : ");
            String Name=s.nextLine();
            System.out.print("\tCollege : ");
            String college=s.nextLine();
            System.out.print("\tCity : ");
            String city=s.nextLine();
            System.out.print("\tEmail : ");
            String Email=s.nextLine();
            isValidEmail(Email);
            System.out.print("\tPassword : ");
            String Password=s.nextLine();
            isValidPassword(Password);
            PreparedStatement st=con.prepareStatement("INSERT INTO \"User\" values(?,?,?,?,?)");
            st.setString(1,Name);
            st.setString(2,college);
            st.setString(3,city);
            st.setString(4,Email);
            st.setString(5,Password);
            st.executeUpdate();
            st.close();
            System.out.println("Signed in Successfully!!!");
            signIn(con);
        }
        catch(SQLException e){
            System.out.println("You are already Registered using this EmailId!!!");
            signIn(con);
        }
    }
    
    public static void isValidEmail(String email){
        try{
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$"; 
            Pattern pat = Pattern.compile(emailRegex); 
            while(!pat.matcher(email).matches()){
                System.out.print("Please Enter the valid Email Id : ");
                email=s.nextLine();
            }
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void isValidPassword(String password){
        try{
            String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
            Pattern pat = Pattern.compile(regex); 
            while(!pat.matcher(password).matches()){
                System.out.print("Please Enter the valid Password : ");
                password=s.nextLine();
            }
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    } 
    
    public static void userOptions(Connection con) throws SQLException{
        try{
            
            String choice;
            do{
                System.out.println("USER OPTIONS : ");
                System.out.println("\t1) Display Sports");
                System.out.println("\t2) Create Team");
                System.out.println("\t3) Display My Teams");
                System.out.println("\t4) Register Team");
                System.out.println("\t5) Display Registered Teams");
                System.out.println("\t6) Display Player Details");
                System.out.println("\t7) Display Match Details");
                System.out.println("\t8) Display Points table");
                System.out.println("\t9) Log Out");
                System.out.print("Enter Your Choice : ");
                choice =s.nextLine();
                switch(choice){
                    case "1":
                        Sports.displaySport(con);
                        break;
                    case "2":
                        Team.createTeam(user.Email,con);
                        break;
                    case "3":
                        boolean x=Team.displayMyTeam(con);
                        break;
                    case "4":
                        Sports.registerTeam(con,user.Email);
                        break;
                    case "5":
                        Team.displayRegisteredTeam(con,user.Email);
                        break;
                    case "6":
                        Player.displayMyPlayerDetails(Team.team(),con);
                        break;
                    case "7":
                        Match.displayMyMatchDetails(con, user.Email);
                        break;
                    case "8":
                        Sports.displayPointsTable(con);
                        break;
                    case "9":
                        break;
                    default:
                        System.out.println("Invalid Choice!!!");
                }
            }while(!choice.equals("9"));
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
}
