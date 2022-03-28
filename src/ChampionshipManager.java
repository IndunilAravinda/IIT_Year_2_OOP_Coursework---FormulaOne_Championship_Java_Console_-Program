import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ChampionshipManager {

//    To receive the arraylist of drivers registered in the season
    String[] getDriversList();

//    To display all the drivers competing in the Formula 1 championship, the team they belong to and some of their statistics
    void displayDriverTable();

//    TO add a new race in the season
    void addRace(LocalDate raceDate, ArrayList<ArrayList<String>> driverPosition);

//    To save the summary of the user inputs
    void saveInputSummary();

//    To load the user inputs from the saved file
    void loadInputSummary() throws IOException, ClassNotFoundException;
}
