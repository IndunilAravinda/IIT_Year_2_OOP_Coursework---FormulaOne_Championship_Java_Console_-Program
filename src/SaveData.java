import java.io.Serializable;
import java.util.ArrayList;

public class SaveData implements Serializable {
    private static final long serialVersionUID=6L;

    ArrayList<Formula1Driver> driversList = new ArrayList<>();
    ArrayList [] allTheArrays = new ArrayList[3];
    ArrayList<Formula1Driver> sortingDrivers = driversList;
    ArrayList<Race> allRaces = new ArrayList<>();
}
