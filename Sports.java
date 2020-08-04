package sportsmanagementsystem;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
class Sports {
    int SportId;
    String sportName;
    String date;
    String venue;
    static Scanner s=new Scanner(System.in);
    public Sports(int SportId,String sportName,String date,String venue){
        this.SportId=SportId;
        this.sportName=sportName;
        this.date=date;
        this.venue=venue;
    }
    
    public static void addSport(Connection con) throws SQLException, ParseException{
        try{
            System.out.println("ADD SPORT :");
            System.out.print("\tSport Id : ");
            int SportId=s.nextInt();
            s.nextLine();
            System.out.print("\tSport Name : ");
            String SportName=s.nextLine();
            System.out.print("\tNumber of Players : ");
            int num=s.nextInt();
            s.nextLine();
            System.out.print("\tDate(MM/DD/YYYY) : ");
            String date=s.nextLine();
            System.out.print("\tVenue : ");
            String venue=s.nextLine();
            SimpleDateFormat format = new SimpleDateFormat( "MM/dd/yyyy" );
            java.util.Date myDate = format.parse( date ); 
            java.sql.Date sqlDate = new java.sql.Date( myDate.getTime() );
            PreparedStatement st=con.prepareStatement("INSERT INTO SPORTS values(?,?,?,?,?)");
            st.setInt(1,SportId);
            st.setString(2,SportName);
            st.setDate(3,sqlDate );
            st.setString(4,venue);
            st.setInt(5,num);
            st.executeUpdate();
            System.out.println("\tSport Added Successfully!!!");
            st.close();
        }
        catch(Exception e){
            System.out.println("\tInvalid Input!!! Please try Again");
        }
    }
    
    public static void deleteSport(Connection con) throws SQLException{
        System.out.println("DELETE SPORT : ");
        try{
            System.out.print("\tSport Id : ");
            int sportId=Integer.parseInt(s.nextLine());
            Statement st=con.createStatement();
            st.executeUpdate("DELETE FROM SPORTS WHERE SPORT_ID="+sportId);
            System.out.println("\tRecord Deleted");
        }
        catch(Exception e){
            System.out.println("\tInvalid Sport Id!!!");
        }
    }
    
    public static void displaySport(Connection con) throws SQLException{
        try{
            System.out.println("DISPLAY SPORTS : ");
            System.out.println("\tList of Sports : ");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT SPORT_NAME FROM SPORTS");
            int i=1;
            while(rs.next()){
                System.out.println("\t\t"+(i++)+" : "+rs.getString(1));
            }
            System.out.println();
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static void editSport(Connection con) throws SQLException, ParseException{
        try{
            
            System.out.println("EDIT SPORT : ");
            System.out.print("\tEnter SportId : ");
            int sportId=s.nextInt();
            s.nextLine();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT SPORT_ID FROM SPORTS WHERE SPORT_ID="+sportId);
            if(rs.next()){
                String choice;
                do{
                    System.out.println("\tEditing Options : ");
                    System.out.println("\t\t1) Sport Name");
                    System.out.println("\t\t2) Date");
                    System.out.println("\t\t3) Venue");
                    System.out.println("\t\t4) Number of Players");
                    System.out.println("\t\t5) Back");
                    System.out.print("\tEnter your Choice : ");
                    choice=s.nextLine();
                    switch(choice){
                        case "1":
                            try{
                                System.out.print("\tEnter Sports Name : ");
                                String sportName=s.nextLine();
                                st.executeUpdate("UPDATE SPORTS SET SPORT_NAME='"+sportName+"' WHERE SPORT_ID="+sportId);
                                System.out.println("\tSport Name Updated Successfully!!!");
                            }
                            catch(Exception e){
                                System.out.println("\tUnable to update Sport Name!!!");
                            }
                            break;
                        case "2":
                            try{
                                System.out.print("\tEnter Date(MM/DD/YYYY) : ");
                                String date=s.nextLine();
                                SimpleDateFormat format = new SimpleDateFormat( "MM/dd/yyyy" );
                                java.util.Date myDate = format.parse( date ); 
                                java.sql.Date sqlDate = new java.sql.Date( myDate.getTime() );
                                PreparedStatement pst=con.prepareStatement("UPDATE SPORTS SET DATE=? WHERE SPORT_ID="+sportId);
                                pst.setDate(1, sqlDate);
                                pst.executeUpdate();
                                System.out.println("\tDate Updated Successfully!!!");
                            }
                            catch(Exception e){
                                System.out.println("\tUnable to update Date!!!");
                            }
                            break;
                        case "3":
                            try{
                                System.out.print("\tEnter Venue : ");
                                String venue=s.nextLine();
                                st.executeUpdate("UPDATE SPORTS SET VENUE='"+venue+"' WHERE SPORT_ID="+sportId);
                                System.out.println("\tVenue Updated Successfully!!!");
                            }
                            catch(Exception e){
                                System.out.println("\tUnable to update Venue!!!");
                            }
                            break;
                        case "4":
                            try{
                                System.out.print("\tEnter Number of Players : ");
                                int num=s.nextInt();
                                s.nextLine();
                                st.executeUpdate("UPDATE SPORTS SET NO_OF_PLAYERS="+num+" WHERE SPORT_ID="+sportId);
                                System.out.println("\tSport Name Updated Successfully!!!");
                            }
                            catch(Exception e){
                                System.out.println("\tUnable to update Numner of Players!!!");
                            }
                            break;
                        case "5":
                            break;
                        default:
                            System.out.println("\tInvalid Choice!!!");
                    }
                }while(!choice.equals("5"));
            }
            else
                System.out.println("Invalid SportId!!!");
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
    
    public static int getSportId(){
        int SportId=0;
        try{
            System.out.print("Enter Sport Id : ");
            SportId=s.nextInt();
            s.nextLine();
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
        return SportId;
    }
    
    static ArrayList<Team> teams=new ArrayList<>();
    public static void getTeams(){
        teams=Team.team();
    }
    
    public static void registerTeam(Connection con,String Email) throws SQLException{
        if(Team.displayMyTeam(con)){
            try{
                System.out.print("Enter Team Number : ");
                int n=s.nextInt();
                s.nextLine();
                getTeams();
                Team team=teams.get(n-1);
                PreparedStatement st1=con.prepareStatement("INSERT INTO TEAM values(?,?,?,?,?)");
                st1.setInt(1,team.teamId);
                st1.setInt(2,team.SportId);
                st1.setInt(3,team.leaguePoints);
                st1.setString(4,team.college);
                st1.setString(5,Email);
                ArrayList<Player> players=team.players;
                for(Player x : players){
                    PreparedStatement st2=con.prepareStatement("INSERT INTO PLAYERS values(?,?,?,?)");
                    st2.setInt(1,team.teamId);
                    st2.setInt(2,x.PlayerId);
                    st2.setString(3,x.Name);
                    st2.setString(4,x.year);
                    st2.executeUpdate();
                }
                st1.executeUpdate();
                System.out.println("Team Registered Succesfully!!!");
            }
            catch(Exception e){
                System.out.println("Error Occured!!!");
            }
        }
    }
    
    public static void displayPointsTable(Connection con) throws SQLException{
        try{
            Sports.displaySport(con);
            System.out.print("Choose the Sport : ");
            int choice=s.nextInt();
            s.nextLine();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT SPORT_ID FROM SPORTS");
            while(choice>=0){
                rs.next();
                choice--;
            }
            int sportId=rs.getInt(1);
            Statement st1=con.createStatement();
            ResultSet rs1=st1.executeQuery("SELECT COLLEGE, LEAGUE_POINTS FROM TEAM WHERE SPORT_ID="+sportId+"ORDER BY LEAGUE_POINTS ASC ");
            Statement st2=con.createStatement();
            ResultSet rs2=st2.executeQuery("SELECT SPORT_NAME FROM SPORTS WHERE SPORT_ID="+sportId);
            if(rs2.next()){
                System.out.println("SPORT : "+rs2.getString(1));
                int i=1;
                while(rs1.next()){
                    System.out.println("\t"+i+")"+rs2.getString(1)+" : "+rs2.getInt(2));
                    i++;
                }
            }
            else{
                System.out.println("Invalid Sport Id!!!");
            }
        }
        catch(Exception e){
            System.out.println("Error Occured!!!");
        }
    }
}