package sportsmanagementsystem;
import java.util.*;
import java.sql.*;

class Team {
    static Scanner s=new Scanner(System.in);

    int teamId;
    int SportId;
    String college;
    int leaguePoints;
    ArrayList<Player> players;
    
    static ArrayList<Team> teams=new ArrayList<Team>();
    
    public Team(int SportId,int teamId,String college,int leaguePoints,ArrayList<Player> players){
        this.SportId=SportId;
        this.teamId=teamId;
        this.college=college;
        this.leaguePoints=leaguePoints;
        this.players=players;
    }
    
    public static ArrayList<Team> team(){
        return teams;
    }
    
    public static void createTeam(String Email,Connection con){
        try{
            System.out.println("REGISTER TEAM : ");
            Sports.displaySport(con);
            System.out.print("Choose the Sport : ");
            int choice=s.nextInt();
            s.nextLine();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT SPORT_ID,NO_OF_PLAYERS FROM SPORTS");
            while(choice>=0){
                rs.next();
                choice--;
            }
            int SportId=rs.getInt(1);
            int NumOfPlayers=rs.getInt(2);
            int TeamId=SportsManagementSystem.generateUniqueId();
            System.out.print("\tCollege : ");
            String college=s.nextLine();
            ArrayList<Player> players=Player.getPlayerDetails(NumOfPlayers);
            teams.add(new Team(SportId,TeamId,college,0,players));
            System.out.println("Team Created Successfully!!!");
        }
        catch(Exception e){
            System.out.println("Invalid Input!!!");
        }
    }
    
    public static boolean displayMyTeam(Connection con) throws SQLException{
        try{
            System.out.println("My Teams : ");
            int i=0;
            if(!teams.isEmpty())
                for(Team team : teams){
                    System.out.println("Team "+i+" : ");
                    Statement st=con.createStatement();
                    ResultSet rs=st.executeQuery("SELECT SPORT_NAME FROM SPORTS WHERE SPORT_ID="+team.SportId);
                    if(rs.next()){
                        System.out.println("\tSport : "+rs.getString(1));
                        System.out.println("\tCollege : "+team.college);
                    }
                    else
                        System.out.println("Invalid Sport");
                    return true;
                }
            else{
                System.out.println("Team List is Empty Please Create a Team for Regsitration!!!");
                return false;
            }
        }
        catch(Exception e){
            System.out.println("Error occured!!!");
        }
       return false;
    }
    
    public static void displayRegisteredTeam(Connection con,String Email) throws SQLException{
        try{
            Statement st1=con.createStatement();
            ResultSet rs1=st1.executeQuery("SELECT* FROM TEAM WHERE EMAIL=\""+Email+"\"");
            int i=1;
            while(rs1.next()){
                Statement st2=con.createStatement();
                ResultSet rs2=st1.executeQuery("SELECT SPORT_NAME FROM SPORTS WHERE SPORT_ID="+rs1.getInt(2));
                System.out.println("Team "+i+" : ");
                System.out.println("\tSport : "+rs2.getString(1));
                System.out.println("\tCollege : "+rs1.getString(4));
                i++;
            }
            if(i==1)
                System.out.println("You didn't registerd for any Sports please register!!!");
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static boolean displayAllTeam(Connection con,int SportId) throws SQLException{
        try{
            Statement st=con.createStatement();
            ResultSet rs1=st.executeQuery("SELECT SPORT_NAME FROM SPORTS WHERE SPORT_ID="+SportId);
            if(rs1.next()){
                String Sport=rs1.getString(1);
                ResultSet rs2=st.executeQuery("SELECT TEAM_ID, COLLEGE FROM TEAM WHERE SPORTS_ID="+SportId);
                System.out.println(Sport.toUpperCase()+" TEAMS : ");
                boolean flag=true;
                int i=1;
                while(rs2.next()){
                    System.out.println("\t"+i+")"+rs2.getInt(1)+" : "+rs2.getString(2));
                    flag=false;
                    i++;
                }
                if(flag){
                    System.out.println("\tNone of the Team is registered for this sport!!!");
                    return false;
                }
                System.out.println();
            }
            else{
                System.out.println("Invalid SportId!!!");
                return false;
            }
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
        return true;
    }
}