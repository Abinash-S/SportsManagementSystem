package sportsmanagementsystem;
import java.util.*;
import java.sql.*;
class Player {
    
    int PlayerId;
    String Name;
    String year;
    static Scanner s=new Scanner(System.in);
    
    public Player(int PlayerId,String Name,String year){
        this.PlayerId=PlayerId;
        this.Name=Name;
        this.year=year;
    }
    public static ArrayList<Player> getPlayerDetails(int NumOfPlayers){
        System.out.println("Player Details : ");
        ArrayList<Player> players=new ArrayList<Player>();
        players.clear();
        for(int i=1;i<=NumOfPlayers;){
            try{
                System.out.println("Player "+i+" Details : ");
                System.out.print("\tName : ");
                String Name=s.nextLine();
                System.out.print("\tYear : ");
                String year=s.nextLine();
                players.add(new Player(SportsManagementSystem.generateUniqueId(),Name,year));
                i++;
            }
            catch(Exception e){
                System.out.println("Invalid Player Details!!!");
            }
        }
        return players;
    }
    
    public static void displayPlayerDetails(Connection con) throws SQLException{
        try{
            System.out.println("DISPLAY PLAYER DETAILS : ");
            System.out.print("\tEnter TeamId : ");
            int TeamId=s.nextInt();
            s.nextLine();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT PLAYER_ID, NAME, \"Year\" FROM PLAYERS WHERE TEAM_ID="+TeamId);
            System.out.println("\tPlayers : ");
            while(rs.next()){
                System.out.println("\t\tPlayer Id : "+rs.getInt(1));
                System.out.println("\t\tPlayer Name : "+rs.getString(2));
                System.out.println("\t\tPlayer Year : "+rs.getString(3));
                System.out.println();
            }
            System.out.println();
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void displayMyPlayerDetails(ArrayList<Team> team,Connection con) throws SQLException{
        try{
            System.out.println("PLAYER DETAILS : ");
            Team.displayMyTeam(con);
            System.out.print("Enter team number : ");
            int n=s.nextInt();
            s.nextLine();
            ArrayList<Player> players=team.get(n-1).players;
            int i=1;
            for(Player player : players){
                System.out.println("Player "+i+" : ");
                System.out.println("\t\tPlayer Id : "+player.PlayerId);
                System.out.println("\t\tPlayer Name : "+player.Name);
                System.out.println("\t\tPlayer Year : "+player.year);
                System.out.println();
                i++;
            }
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
}