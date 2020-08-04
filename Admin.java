package sportsmanagementsystem;

import java.util.*;
import java.sql.*;
import java.text.ParseException;
class Admin {
    
    static Scanner s=new Scanner(System.in);
    public static void signIn(Connection con) throws SQLException, ParseException{
        try{
            System.out.print("\tAdmin Id  : ");
            String adminId=s.nextLine();
            System.out.print("\tPassword : ");
            String password=s.nextLine();
            if(adminId.equals("admin619@SportsManagementSystem") && password.equals("adminPassword")){
                adminOptions(con);
            }
            else{
                System.out.println("Invalid Id or Password!!!");
            }
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void adminOptions(Connection con) throws SQLException, ParseException{
        try{
            String choice;
            do{
                System.out.println("\t1) Sports");
                System.out.println("\t2) Teams");
                System.out.println("\t3) Match");
                System.out.println("\t4) Log Out");
                System.out.print("Enter Your choice : ");
                choice =s.nextLine();
                switch(choice){
                    case "1":
                        sportsOptions(con);
                        break;
                    case "2":
                        teamOptions(con);
                        break;
                    case "3":
                        matchOptions(con);
                        break;
                    case "4":
                        break;
                    default:
                        System.out.println("Invalid Choice!!!");
                }
            }while(!choice.equals("4"));
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void sportsOptions(Connection con) throws SQLException, ParseException{
        try{
            String choice;
            do{
                System.out.println("Sports : ");
                System.out.println("\t1)Add Sport");
                System.out.println("\t2)Delete Sport");
                System.out.println("\t3)Edit Sport");
                System.out.println("\t4)Display Sport");
                System.out.println("\t5)Display Points Table");
                System.out.println("\t5)Back");
                System.out.print("Enter your Choice : ");
                choice=s.nextLine();
                switch(choice){
                    case "1":
                        Sports.addSport(con);
                        break;
                    case "2":
                        Sports.deleteSport(con);
                        break;
                    case "3":
                        Sports.editSport(con);
                        break;
                    case "4":
                        Sports.displaySport(con);
                        break;
                    case "5":
                        Sports.displayPointsTable(con);
                        break;
                    default:
                        System.out.println("Invalid choice!!!");
                }
            }while(!choice.equals("5"));
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void teamOptions(Connection con) throws SQLException{
        try{
            String choice;
            do{
                System.out.println("Teams : ");
                System.out.println("\t1)Display Teams");
                System.out.println("\t2)Display Players");
                System.out.println("\t3)Back");
                System.out.print("Enter your Choice : ");
                choice=s.nextLine();
                switch(choice){
                    case "1":
                        Team.displayAllTeam(con,Sports.getSportId());
                        break;
                    case "2":
                        Player.displayPlayerDetails(con);
                        break;
                    case "3":
                        break;
                    default:
                        System.out.println("Invalid choice!!!");
                }
            }while(!choice.equals("3"));
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void matchOptions(Connection con) throws SQLException{
        try{
            
            String choice;
            do{
                System.out.println("Match : ");
                System.out.println("\t1)Add Match");
                System.out.println("\t2)Delete Match");
                System.out.println("\t3)Edit Match");
                System.out.println("\t4)Display Match");
                System.out.println("\t5)Update Winner");
                System.out.println("\t6)Back");
                System.out.print("Enter your Choice : ");
                choice=s.nextLine();
                switch(choice){
                    case "1":
                        Match.addMatch(con);
                        break;
                    case "2":
                        Match.deleteMatch(con);
                        break;
                    case "3":
                        Match.editMatchShedule(con);
                        break;
                    case "4":
                        Match.displayMatchDetails(con);
                        break;
                    case "5":
                        Match.updateWinner(con);
                        break;
                    case "6":
                        break;
                    default:
                        System.out.println("Invalid choice!!!");
                }
            }while(!choice.equals("6"));
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
}
