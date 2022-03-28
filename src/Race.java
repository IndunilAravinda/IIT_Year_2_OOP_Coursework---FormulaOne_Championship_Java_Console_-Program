import java.time.LocalDate;
import java.util.ArrayList;

public class Race extends Formula1ChampionshipManager{
    LocalDate raceDate;
    ArrayList<ArrayList<String>> positions = new  ArrayList<>();

    Race(LocalDate date,ArrayList<ArrayList<String>> driversPositionsWon){
        this.raceDate=date;
        this.positions=driversPositionsWon;

    }
}
