package sportsmanagementsystem;
import java.util.*;
import java.sql.*;
class Match {
    int MatchId;
    int SportId;
    int Team1Id;
    int Team2Id;
    int winner;
    static Scanner s=new Scanner(System.in);
    public Match(int MatchId,int SportId,int Team1Id,int Team2Id,int winner){
        this.MatchId=MatchId;
        this.SportId=SportId;
        this.Team1Id=Team1Id;
        this.Team2Id=Team2Id;
        this.winner=winner;
    }
    
    public static void addMatch(Connection con){
        try{
            int SportId=Sports.getSportId();
            if(Team.displayAllTeam(con,SportId)){
                System.out.print("Enter How many Matches you are going to Register : ");
                int n=s.nextInt();
                s.nextLine();
                for(int i=0;i<n;i++){
                    System.out.print("\tEnter Team1 Id : ");
                    int team1Id = s.nextInt();
                    s.nextLine();
                    System.out.print("\tEnter Team2 Id : ");
                    int team2Id=s.nextInt();
                    s.nextLine();
                    System.out.print("\tTime : ");
                    String time=s.nextLine();
                    PreparedStatement st=con.prepareStatement("INSERT INTO \"Match\" values(?,?,?,?,?,?)");
                    st.setInt(1,SportsManagementSystem.generateUniqueId());
                    st.setInt(2,SportId);
                    st.setInt(3,team1Id );
                    st.setInt(4,team2Id);
                    st.setInt(5,0);
                    st.setString(6,time);
                    st.executeUpdate();
                    System.out.println("\tMatch Added Successfully!!!\n");
                }
            }
        }
        catch(Exception e){
            System.out.println("\tInvalid Input!!! Please try Again");
        }
    }
    
    public static void displayMyMatchDetails(Connection con,String Email) throws SQLException{
        try{
            Statement st1=con.createStatement();
            ResultSet rs1=st1.executeQuery("SELECT TEAM_ID FROM TEAM WHERE EMAIL='"+Email+"'");
            while(rs1.next()){
                int i=1;
                Statement st2=con.createStatement();
                ResultSet rs2=st2.executeQuery("SELECT SPORT_ID, TEAM1_ID, TEAM2_ID,MATCH_TIME FROM \"Match\" WHERE TEAM1_ID="+rs1.getInt(1)+" OR TEAM2_ID="+rs1.getInt(1));
                if(rs2.next()){
                    Statement st3=con.createStatement();
                    ResultSet rs3=st3.executeQuery("SELECT SPORT_NAME FROM SPORTS WHERE SPORT_ID="+rs2.getInt(1));
                    Statement st4=con.createStatement();
                    ResultSet rs4=st4.executeQuery("SELECT COLLEGE FROM TEAM WHERE TEAM_ID="+rs2.getInt(2));
                    Statement st5=con.createStatement();
                    ResultSet rs5=st5.executeQuery("SELECT COLLEGE FROM TEAM WHERE TEAM_ID="+rs2.getInt(3));
                    if(rs4.next() && rs5.next()&&rs3.next()){
                        System.out.println("Match "+(i++)+" : ");
                        System.out.println("\tSport : "+rs3.getString(1));
                        System.out.print("\tMatch : "+rs4.getString(1)+" VS "+rs5.getString(1));
                        System.out.println("\tTime : "+rs2.getString(4));
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void displayMatchDetails(Connection con){
        try{
            int SportId=Sports.getSportId();
            Statement st1=con.createStatement();
            ResultSet rs1=st1.executeQuery("SELECT SPORT_NAME FROM SPORTS WHERE SPORT_ID="+SportId);
            if(rs1.next()){
                System.out.println("SPORT : "+rs1.getString(1));
                Statement st2=con.createStatement();
                ResultSet rs2=st2.executeQuery("SELECT* FROM \"Match\" WHERE SPORT_ID="+SportId);
                while(rs2.next()){
                    int i=1;
                    Statement st3=con.createStatement();
                    ResultSet rs3=st3.executeQuery("SELECT COLLEGE FROM TEAM WHERE TEAM_ID="+rs2.getInt(3));
                    Statement st4=con.createStatement();
                    ResultSet rs4=st4.executeQuery("SELECT COLLEGE FROM TEAM WHERE TEAM_ID="+rs2.getInt(4));
                    if(rs3.next() && rs4.next()){
                        System.out.println("Match "+(i++)+" : ");
                        System.out.println("\tMatch Id : "+rs2.getInt(1));
                        System.out.print("\tMatch : "+rs3.getString(1)+" VS "+rs4.getString(1));
                        System.out.println("\tMatch Time : "+rs2.getString(5));
                        System.out.println("\tTime : "+rs2.getString(6));
                        System.out.println();
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void editMatchShedule(Connection con){
        try{
            System.out.println("Enter Match Id : ");
            int MatchId=s.nextInt();
            s.nextLine();
            String choice;
            Statement st=con.createStatement();
            do{
                System.out.println("\tEditing Options : ");
                System.out.println("\t\t1) Sport Id");
                System.out.println("\t\t2) Team1 Id");
                System.out.println("\t\t3) Team2 Id");
                System.out.println("\t\t4) Winner");
                System.out.println("\t\t5) Match Time");
                System.out.println("\t\t6) Back");
                System.out.print("\tEnter your Choice : ");
                choice=s.nextLine();
                switch(choice){
                    case "1":
                        try{
                            System.out.print("\tEnter Sports Id : ");
                            int sportId=s.nextInt();
                            st.executeUpdate("UPDATE \"Match\" SET SPORT_ID='"+sportId+"' WHERE MATCH_ID="+MatchId);
                            System.out.println("\tSport Id Updated Successfully!!!");
                        }
                        catch(Exception e){
                            System.out.println("\tUnable to update Sport Id!!!");
                        }
                        break;
                    case "2":
                        try{
                            System.out.print("\tEnter Team1 Id : ");
                            int team1Id=s.nextInt();
                            s.nextLine();
                            st.executeUpdate("UPDATE \"Match\" SET TEAM1_ID='"+team1Id+"' WHERE MATCH_ID="+MatchId); 
                            System.out.println("\tTeam1 Id Updated Successfully!!!");
                        }
                        catch(Exception e){
                            System.out.println("\tUnable to update Team1 Id!!!");
                        }
                        break;
                    case "3":
                        try{
                            System.out.print("\tEnter Team2 Id : ");
                            int team2Id=s.nextInt();
                            s.nextLine();
                            st.executeUpdate("UPDATE \"Match\" SET TEAM2_ID='"+team2Id+"' WHERE MATCH_ID="+MatchId); 
                            System.out.println("\tTeam2 Id Updated Successfully!!!");
                        }
                        catch(Exception e){
                            System.out.println("\tUnable to update Team2 Id!!!");
                        }
                        break;
                    case "4":
                        try{
                            System.out.print("\tEnter Winner Id : ");
                            int WinId=s.nextInt();
                            s.nextLine();
                            st.executeUpdate("UPDATE \"Match\" SET WINNER='"+WinId+"' WHERE MATCH_ID="+MatchId); 
                            System.out.println("\tWinner Id Updated Successfully!!!");
                        }
                        catch(Exception e){
                            System.out.println("\tUnable to update Winner Id!!!");
                        }
                        break;
                    case "5":
                        try{
                            System.out.print("\tEnter Match Time : ");
                            String time=s.nextLine();
                            st.executeUpdate("UPDATE \"Match\" SET MATCH_TIME='"+time+"' WHERE MATCH_ID="+MatchId); 
                            System.out.println("\tMatch Time Updated Successfully!!!");
                        }
                        catch(Exception e){
                            System.out.println("\tUnable to update Match Time!!!");
                        }
                        break;
                    case "6":
                        break;
                    default:
                        System.out.println("\tInvalid Choice!!!");
                }
            }while(!choice.equals("5"));
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void deleteMatch(Connection con){
        System.out.println("DELETE MATCH : ");
        try{
            System.out.print("\tEnter Match Id : ");
            int matchId=Integer.parseInt(s.nextLine());
            Statement st=con.createStatement();
            st.executeUpdate("DELETE FROM \"Match\" WHERE MATCH_ID="+matchId);
            System.out.println("\tRecord Deleted");
        }
        catch(Exception e){
            System.out.println("\tInvalid Match Id!!!");
        }
    }
    
    public static void updateWinner(Connection con){
        try{
            System.out.print("Enter Match Id : ");
            int MatchId=s.nextInt();
            s.nextLine();
            System.out.print("Enter Winner Id : ");
            int winner =s.nextInt();
            s.nextLine();
            Statement st=con.createStatement();
            st.executeUpdate("UPDATE \"Match\" SET WINNER='"+winner+"' WHERE MATCH_ID="+MatchId);
            ResultSet rs=st.executeQuery("SELECT LEAGUE_POINTS FROM TEAM WHERE TEAM_ID="+winner);
            rs.next();
            st.executeUpdate("UPDATE TEAM SET LEAGUE_POINTS="+(rs.getInt(1)+2)+" WHERE TEAM_ID="+winner);
            System.out.println("\tWinner Updated Successfully!!!");
        }
        catch(Exception e){
            System.out.println("\tUnable to update Match Time!!!");
        }
    }
    
}
