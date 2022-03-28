import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Formula1ChampionshipManager  implements ChampionshipManager, Serializable {
/*
    referencing code :
    1) save and load files - https://youtu.be/-xW0pBZqpjU
    2) serialization issue - https://stackoverflow.com/questions/27489780/getting-a-java-io-invalidclassexception-when-trying-to-save-and-read-a-file/27489998
    3) creating tables - https://stackoverflow.com/questions/15215326/how-can-i-create-table-using-ascii-in-a-console
    4) object outputstream - https://www.programiz.com/java-programming/objectoutputstream
    5) creating test classes - https://youtu.be/Zug8zYR0SmA
**/

    private static final long serialVersionUID=6L;
//    Arraylist of all the drivers that participate in this championship
    static ArrayList<Formula1Driver> driversList = new ArrayList<>();

//    Arraylist of all the arrays used in this class
    ArrayList [] allTheArrays = new ArrayList[3];

//    To hold sorted drivers on the driverslist arraylist
    ArrayList<Formula1Driver> sortingDrivers = driversList;

//    Attaylist of all the races added in this championship
    ArrayList<Race> allRaces = new ArrayList<>();

/*  ------------------------------------------
    Function -  Constructor of the Formula1ChampionshipManager Class to generate the console menu
    Parameters - none
    Output - Console menu with options to enter
**/
    Formula1ChampionshipManager() {
        boolean execution=true;
        while (execution){
            try {
                execution=consoleMenu(execution);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

/*  ------------------------------------------
    Function -
    Parameters -
    Output -
**/
    public boolean consoleMenu(boolean readyExecute) throws IOException, ClassNotFoundException {

        boolean state=readyExecute;
        System.out.println("\nWelcome to Formula One Championship Manager");

        System.out.println("\n" + "[Please choose the relevant number of the function you want to perform]");
        System.out.println("1 : Add a New Driver");
        System.out.println("2 : Remove a Driver");
        System.out.println("3 : Change a driver's team");
        System.out.println("4 : Display driver statistics");
        System.out.println("5 : Display the Championship Driver Table");
        System.out.println("6 : Add a completed race to the Championship");
        System.out.println("7 : Save the program Data");
        System.out.println("8 : Load the program Data");
        System.out.println("9 : Exit Program");

        System.out.print("Your choice is option number = ");
        Scanner userInput = new Scanner(System.in);
        int userChoice = userInput.nextInt();
        switch (userChoice) {
            case 1:
                System.out.print("Enter the driver's Name : ");
                String newDriverName = userInput.next();
                System.out.print("Enter the driver's Location  : ");
                String newDriverLocation = userInput.next();
                System.out.print("Enter the driver's Team Name : ");
                String newDriverTeam = userInput.next();
                addDriver(newDriverName, newDriverLocation, newDriverTeam);
                break;
            case 2:
                System.out.println("Which driver should be removed? [Enter driver's name]");
                String driverName1 = userInput.next();
                remDriver(driverName1);
                break;
            case 3:
                System.out.println("Which driver's team is getting changed? [Enter driver's name]");
                String driverName = userInput.next();
                System.out.println("Which existing team is getting driver "+driverName+" [Enter team name]");
                String teamName = userInput.next();
                changeTeamName(driverName,teamName);
                break;
            case 4:
                System.out.println("Which driver's statistics should be displayed? [Enter driver's name]");
                String driverName2 = userInput.next();
                displayDriverStat(driverName2);
                break;
            case 5:
                displayDriverTable();
                break;
            case 6:
                System.out.println("Enter the date of this race event? [Enter in the format M/D/YYYY]");
                LocalDate raceDate = dateInput(userInput.next());

                ArrayList<ArrayList<String>> raceposi = new ArrayList<>();
                for (int i = 0; i < sortingDrivers.size(); i++) {
                    System.out.println("What is the race position of the driver "+sortingDrivers.get(i).getDriverName());
                    int position = userInput.nextInt();
                    ArrayList<String> resultDuo = new ArrayList<>();
                    resultDuo.add(sortingDrivers.get(i).getDriverName());
                    resultDuo.add(String.valueOf(position));
                    raceposi.add(resultDuo);
                }
                addRace(raceDate,raceposi);

                Race newrace = new Race(raceDate,raceposi);
                allRaces.add(newrace);
                System.out.println(newrace.allRaces.size());
                break;
            case 7:
                saveInputSummary();
                break;
            case 8:
                loadInputSummary();
                break;
            case 9:
                state=false;
                break;
        }
        return state;
    }

    public static LocalDate dateInput(String userInput) {

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse(userInput, dateFormat);

        return date ;
    }


/*  ------------------------------------------
    Function -  adding new drivers
    Parameters - String value of name,location and the assigning team name
    Output -    New driver would be created and added to the driver arraylist
**/
    public void addDriver(String name, String location, String team) {
        Formula1Driver newDriver = new Formula1Driver(name, location, team);
        sortingDrivers.add(newDriver);

        System.out.println("Successfully Added");
    }

/*  ------------------------------------------
    Function -  Removing a driver
    Parameters - String value of the driver name that needs to be removed
    Output -    driver is selected and remove from the arraylist of drivers and freed it for garbage collector
**/
    public void remDriver(String name) {
        if (!sortingDrivers.isEmpty()) {
            for (int i = 0; i < sortingDrivers.size(); i++) {
                Formula1Driver driver = sortingDrivers.get(i);
                if (driver.getDriverName().equalsIgnoreCase(name)) {
                    driver.removeDriver();
                    sortingDrivers.remove(i);
                }
            }
        } else {
            System.out.println("driver list empty");
        }
    }

/*  ------------------------------------------
    Function -  changes the team name of a particular driver is assigned to a different existing team
    Parameters - String driver name and the string value of the team that needs driver replacement
    Output -    Since there should be one driver for one team, old driver of the selected team is removed and
                new driver is assigned to that team. Drivers arraylist is updated accordingly as well.
**/
    public void changeTeamName(String driverName, String TeamName) {
        if (!sortingDrivers.isEmpty()) {
            for (int i = 0; i < sortingDrivers.size(); i++) {
                Formula1Driver driver = sortingDrivers.get(i);
                if (driver.getDriverName().equalsIgnoreCase(driverName)) {
                    driver.changeTeam(TeamName);

                    System.out.printf("Team Changed " + driverName + "\n");
                } else if (driver.teamName.equalsIgnoreCase(TeamName)) {
                    driver.removeDriver();
                    sortingDrivers.remove(i);
                }
            }
        } else {
            System.out.println("driver list empty");
        }
    }

/*  ------------------------------------------
    Function -  display driver statistics in a table
    Parameters - driver name as a string
    Output -    console output of a table that consists of statistics about the driver
**/
    public void displayDriverStat(String driverName) {
        boolean foundTheDriver = false;
        if (!sortingDrivers.isEmpty()) {
            for (int i = 0; i < sortingDrivers.size(); i++) {
                Formula1Driver driver = sortingDrivers.get(i);
                if (driver.getDriverName().equalsIgnoreCase(driverName)) {
                    foundTheDriver = true;
                    HashMap driverPositionsStat = driver.getPositionsSummary();
                    int points = driver.getTotalPoints();

                    System.out.println(driverName + "'s Statistics");

                    System.out.format("+------------+-----------+%n");
                    System.out.format("| Statistics |  Result   |%n");
                    System.out.format("+------------+-----------+%n");

                    String alignment = "| %-10s | %9d |%n";
                    System.out.format(alignment, "1st Places", driverPositionsStat.get("First"));
                    System.out.format(alignment, "2nd Places", driverPositionsStat.get("Second"));
                    System.out.format(alignment, "3rd Places", driverPositionsStat.get("Third"));
                    System.out.format(alignment, "Points", points);
                    System.out.format("+------------+-----------+%n");
                }
            }
            if (!foundTheDriver) {
                System.out.println("This driver is not in this championship");
            }
        } else {
            System.out.println("There are no drivers registered at the moment");
        }
    }

/*  ------------------------------------------
    Function -  getter method from the interface to read the name list of the participating drivers
    Parameters - None
    Output -    Return a string array of all the driver names
**/
    @Override
    public String[] getDriversList() {
        String[] drivers = new String[sortingDrivers.size()];
        for (int i = 0; i < sortingDrivers.size(); i++) {
            drivers[i] = sortingDrivers.get(i).getDriverName();
        }
        return drivers;
    }

/*  ------------------------------------------
    Function -  display all the drivers in the championship with their statistics
    Parameters - None
    Output -    Return a console table of drivers with their stats
**/
    @Override
    public void displayDriverTable() {
        sortDrivers();
        if (!sortingDrivers.isEmpty()) {
            System.out.format("+----------+------------+------------+------------+------------+------------+%n");
            System.out.format("|  Driver  |    Team    | 1st Places | 2nd Places | 3rd Places |   Points   |%n");
            System.out.format("+----------+------------+------------+------------+------------+------------+%n");
            for (int i = 0; i < sortingDrivers.size(); i++) {
                HashMap driverPositionsStat = sortingDrivers.get(i).getPositionsSummary();

                String alignment = "| %-8s | %10s | %10d | %10d | %10d | %10d |%n";
                System.out.format(alignment, sortingDrivers.get(i).getDriverName(),
                        sortingDrivers.get(i).teamName, driverPositionsStat.get("First"),
                        driverPositionsStat.get("Second"), driverPositionsStat.get("Third"),
                        sortingDrivers.get(i).getTotalPoints());
                System.out.format("+----------+------------+------------+------------+------------+------------+%n");
            }
        } else {
            System.out.println("There are no drivers registered at the moment");
        }
    }

/*  ------------------------------------------
    Function -  sorts the driverslist by points and wins in a case where points are equal
    Parameters - none
    Output -    sort the Arraylist sortingDrivers
**/
    public void sortDrivers() {
        System.out.println("Sorting method");
        sortByPoints sortedPoints = new sortByPoints();
        Comparator operand = Collections.reverseOrder(sortedPoints);
        Collections.sort(sortingDrivers, operand);
        if (sortedPoints.similarities) {
            Comparator operand2 = Collections.reverseOrder(new sortByWins());
            Collections.sort(sortingDrivers, operand2);
        }
    }

/*  ------------------------------------------
    Function -  adds a new race to the championship
    Parameters - LocalDate of race date and 2d array of string consist of driver positions
                in the format {"DriverName","Position"}
    Output -    creates a race object and update driver stats, then add the race to the racelist
**/

    @Override
    public void addRace(LocalDate raceDate, ArrayList<ArrayList<String>> driverPosition) {
        for (int i = 0; i < driverPosition.size(); i++) {
            for (int j = 0; j < sortingDrivers.size(); j++) {
                if (driverPosition.get(i).get(0).equalsIgnoreCase(sortingDrivers.get(j).getDriverName())){
                    sortingDrivers.get(j).addRacePosition(Integer.parseInt(driverPosition.get(i).get(1)));
                }
            }

        }
    }

/*  ------------------------------------------
    Function -  Bundles all the required data of the application to a single hashmap
    Parameters - none
    Output -    Hashmap where the arraylist data is passed to
**/
    public ArrayList [] appDataBundle() {
        allTheArrays[0]=driversList;
        allTheArrays[1]=sortingDrivers;
        allTheArrays[2]=allRaces;
        return allTheArrays;
    }


/*  ------------------------------------------
    Function -  save the bundled applicationdata into a file
    Parameters - none
    Output -    text file named "FormulaOne.txt" is created with the hashmap data
**/
    @Override
    public void saveInputSummary(){
        SaveData allClassData = new SaveData();
        allClassData.driversList=this.driversList;
        allClassData.allRaces=this.allRaces;
        allClassData.sortingDrivers=this.sortingDrivers;
        allClassData.allTheArrays=this.allTheArrays;
//        try {
//            System.out.println("3");
//            ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get("FormulaOne.txt")));
//            outputStream.writeObject(allClassData);
//            System.out.println("4");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
//            appDataBundle();
            File appData = new File("FormulaOne.txt");
            if (appData.createNewFile()) {
                System.out.println("File created: " + appData.getName());
            } else {
                System.out.println("File already exists.");
            }
            try {
                FileOutputStream outputFileStream = new FileOutputStream(appData);
                ObjectOutputStream objectStream = new ObjectOutputStream(outputFileStream);
                objectStream.writeObject(allClassData);
                objectStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }finally {
            System.out.println("Success");
        }
    }

    /*  ------------------------------------------
        Function -  load the bundled applicationdata from a file
        Parameters - filename
        Output -
    **/
    @Override
    public void loadInputSummary() throws IOException, ClassNotFoundException {
        System.out.println("Start to load");
        SaveData oldData = (SaveData) load();
        System.out.println("class done");
        this.driversList=oldData.driversList;
        System.out.println("Assigning");
        this.allRaces=oldData.allRaces;
        this.sortingDrivers=oldData.sortingDrivers;
        this.allTheArrays= oldData.allTheArrays;
        System.out.println("All done");
        displayDriverTable();
    }

    public SaveData load() throws IOException, ClassNotFoundException {

        ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get("FormulaOne.txt")));
        return (SaveData) inputStream.readObject();

//        try {
//            FileInputStream inputFileStream = new FileInputStream("FormulaOne.txt");
//            ObjectInputStream objectinputStream = new ObjectInputStream(inputFileStream);
//
//            Formula1ChampionshipManager oldClass = (Formula1ChampionshipManager) objectinputStream.readObject();
////            Formula1Driver oldDriver = (Formula1Driver)objectinputStream.readObject();
//
////            System.out.println(oldDriver.getDriverName());
//            Formula1ChampionshipManager thisChampionship= new Formula1ChampionshipManager();
//
//            thisChampionship.driversList=oldClass.allTheArrays[0];
//
//            System.out.println(oldClass.allTheArrays);
//            thisChampionship.sortingDrivers=oldClass.allTheArrays[1];
//            thisChampionship.allRaces = oldClass.allTheArrays[2];
//
//            System.out.println("Loading Success");
//            objectinputStream.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }
}

class sortByPoints implements Comparator<Formula1Driver>{

//    boolean variable to check whether there were any drivers with equal points
    boolean similarities=false;

/*  ------------------------------------------
 Function -  interface method implementation to compare points between 2 drivers
 Parameters - Formula1Driver type driver 1 & driver 2 that needs to be compared
 Output -    returns a int of -1 if less than, 0 if equal, +1 if greater than
**/
    @Override
    public int compare(Formula1Driver driver1, Formula1Driver driver2) {
        int result = driver1.getTotalPoints()-driver2.getTotalPoints();
        if (result==0){
            similarities=true;
        }
        return result;
    }
/*  ------------------------------------------
 Function -  Sorting in reverse
 Parameters - none
 Output -    returns super comparison in descending order
**/
    @Override
    public Comparator<Formula1Driver> reversed() {
        return Comparator.super.reversed();
    }

}

class sortByWins implements Comparator<Formula1Driver>{

/*  ------------------------------------------
    Function -  interface method implementation to compare first places wins between 2 drivers
    Parameters - Formula1Driver type driver 1 & driver 2 that needs to be compared
    Output -    returns a int of -1 if less than, 0 if equal, +1 if greater than
**/
    @Override
    public int compare(Formula1Driver driver1, Formula1Driver driver2) {
        return (int) driver1.getPositionsSummary().get("First")-(int)driver2.getPositionsSummary().get("First");
    }

/*  ------------------------------------------
 Function -  Sorting in reverse
 Parameters - none
 Output -    returns super comparison in descending order
**/
    @Override
    public Comparator<Formula1Driver> reversed() {
        return Comparator.super.reversed();
    }
}