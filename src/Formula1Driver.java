import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Formula1Driver extends Drivers implements Serializable {

    private static final long serialVersionUID=6L;

    // Hashmap data collection of driver stats such as places won by the particular drier
    private HashMap <String, Integer>positionsSummary;

    // total points that the driver has throughout the season
    private int totalPoints=0;

    // No. of races that the driver participated
    private int races=0;

    // arraylist of all the teams that are registered in the season to race
    protected static ArrayList<String>allTeams = new ArrayList<>();

    // team of the formula 1 driver
    protected String teamName;

/*  ------------------------------------------
    Function - Constructor that will create an object of formula 1 driver everytime a new driver gets registered
    Parameters - String value of the Driver Name, String value of the driver location, String value of the team that the driver is registering with
    Output - creates an instance of Formula 1 Driver
**/
    Formula1Driver(String name, String location, String team) {
        super(name, location);
        this.positionsSummary = new HashMap<>();
        this.positionsSummary.put("First",0);
        this.positionsSummary.put("Second",0);
        this.positionsSummary.put("Third",0);
        addIntoTeam(team);
    }

    /*  ------------------------------------------
        Function - addIntoTeam method will check whether team already exist in the arraylist and add it to the arraylist if it not there.
                   And it will assign the new team string value to the driver team variable
        Parameters - New team name as a String
        Output - assign the new team value as the driver's team
    **/
    public void addIntoTeam(String newTeam) {
        if (allTeams.isEmpty()){
            allTeams.add(newTeam);
        }else{
            for (int i = 0; i < allTeams.size(); i++) {
                if (!allTeams.get(i).equalsIgnoreCase(newTeam)) {
                    this.allTeams.add(newTeam);
                }
            }
        }
        this.teamName=newTeam;
    }

/*  ------------------------------------------
    Function - changeTeam method will check whether team already exist in the allTeams arraylist and change the team of the driver
                referenced, if and only if that team exists on the arraylist.
    Parameters - New team name as a String and the old driver of that team
    Output - replace the team driver
**/
    public void changeTeam(String newTeam) {
        if (allTeams.isEmpty()){
            System.out.println("There are no teams registered here");
        }else{
            for (int i = 0; i < allTeams.size(); i++) {
                if (allTeams.get(i).equalsIgnoreCase(newTeam)) {
                    this.teamName=newTeam;
                }
            }
        }
    }

/*  ------------------------------------------
    Function - getter method to read the stats summary of the particular driver
    Parameters - None
    Output - Hashmap of the particular driver's stats
**/
    public int getFirstPlacesWon(){
        int ret = positionsSummary.get("First");
        return ret;
    }

/*  ------------------------------------------
    Function - getter method to read the stats summary of the particular driver
    Parameters - None
    Output - Hashmap of the particular driver's stats
**/
    public HashMap getPositionsSummary() {
        return positionsSummary;
    }

/*  ------------------------------------------
    Function - setter method to record the wins of that driver
    Parameters - integer value of position driver took on the race
    Output - Records the wins in the Hashmap variable if it's a valid record
**/
    public void setPositionsSummaryPosition(int wonPosition) {
            addRacePosition(wonPosition);
    }

/*  ------------------------------------------
    Function - Records the position of the driver at the end of a race
    Parameters - integer value of position driver took on the race
    Output - Record the race position as a win on the hashmap variable if the driver is on the top 3 positions.
             And also it calculates and record the total points scored according to the position.
             It will print out a statement on the console in a case where the driver is not in the Top 10.
             It will record the race as completed by adding it to the races variable.
**/
    public void addRacePosition(int position) {
        switch (position){
            case 1 :
                this.totalPoints += 25;
                this.positionsSummary.put("First",(positionsSummary.get("First")+1));
                raceCompleted();
                break;
            case 2:
                this.totalPoints += 18;
                this.positionsSummary.put("Second",(positionsSummary.get("Second")+1));
                raceCompleted();
                break;
            case 3:
                this.totalPoints += 15;
                this.positionsSummary.put("Third",(positionsSummary.get("Third")+1));
                raceCompleted();
                break;
            case 4:
                this.totalPoints += 12;
                raceCompleted();
                break;
            case 5:
                this.totalPoints += 10;
                raceCompleted();
                break;
            case 6:
                this.totalPoints += 8;
                raceCompleted();
                break;
            case 7:
                this.totalPoints += 6;
                raceCompleted();
                break;
            case 8:
                this.totalPoints += 4;
                raceCompleted();
                break;
            case 9:
                this.totalPoints += 2;
                raceCompleted();
                break;
            case 10:
                this.totalPoints += 1;
                raceCompleted();
                break;
            default:
                raceCompleted();
                System.out.println("Driver is not on the Top 10.");
        }

    }

/*  ------------------------------------------
    Function - Recording the completion of a race
    Parameters - none
    Output - Increment the no. of races value on the races variable by 1
**/
    public void raceCompleted() {
        this.races += 1;
    }

/*  ------------------------------------------
    Function - Getter method to read the totals points scored by the driver in the season
    Parameters - none
    Output - integer value of the totalPoints variable
**/
    public int getTotalPoints() {
        return totalPoints;
    }

/*  ------------------------------------------
Function - Remove a driver
Parameters - none
Output - facilitate the garbage collector to collect data under this driver
**/
    public void removeDriver() {
        super.removeLocation();
        super.removeDriverName();
        this.positionsSummary=null;
        for (int i = 0; i < allTeams.size(); i++) {
            allTeams.remove(i);
        }
    }

}
