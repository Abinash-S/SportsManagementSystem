package sportsmanagementsystem;
import java.util.UUID;
import java.sql.*;
import java.util.*;
public class SportsManagementSystem {

    public static void main(String[] args) throws Exception {
        Scanner s=new Scanner(System.in);
        try{
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/MyDatabase","root","abi822080");
            boolean flag1=true;
            while(flag1){
                System.out.println("SPORTS MANAGEMENT SYSTEM");
                System.out.println("\t1) SignIn");
                System.out.println("\t2) SignUp");
                System.out.print("Enter your choice : ");
                String choice=s.nextLine();
                switch(choice){
                    case "1":
                        boolean flag2=true;
                        while(flag2){
                            System.out.println("SIGN IN : ");
                            System.out.println("\t1) User");
                            System.out.println("\t2) Admin");
                            System.out.print("Enter your choice : ");
                            String choice1=s.nextLine();
                            switch(choice1){
                                case "1":
                                    System.out.println("User Login :");
                                    User.signIn(con);
                                    flag2=false;
                                    break;
                                case "2":
                                    System.out.println("Admin Login : ");
                                    Admin.signIn(con);
                                    flag2=false;
                                    break;
                                default:
                                    System.out.println("Invali Choice!!!");
                            }
                        }
                        flag1=false;
                        break;
                    case "2":
                        User.signUp(con);
                        flag1=false;
                        break;
                    default:
                        System.out.println("Invalid Choice!!!");
                }
            }
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static int generateUniqueId() {      
        UUID idOne = UUID.randomUUID();
        String str=""+idOne;        
        int uid=str.hashCode();
        String filterStr=""+uid;
        str=filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }    
}