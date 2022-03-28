import java.io.Serializable;

public abstract class Drivers implements Serializable {
    private static final long serialVersionUID=6L;
// common info to be stored for any driver is stored in these variables (name & location)
    private String driverName, location;

/*  ------------------------------------------
    Function - constructor which will initialize the variables with values passed on
    Parameters - Driver Name & Driver location
    Output - gives a blueprint to the child class object creation
**/
    Drivers(String name, String location){
        this.driverName=name;
        this.location=location;
    }

    /*  ------------------------------------------
        Function - getter for string location variable
        Parameters - None
        Output - returns a string variable with the value stored in location variable
    **/
    public String getLocation() {
        return location;
    }

/*  ------------------------------------------
    Function - Making the location variable available for Java Garbage Collector to collect
    Parameters - None
    Output - set the location variable value to null
**/
    public void removeLocation() {
        this.location=null;
    }

/*  ------------------------------------------
    Function - getter for string driverName variable
    Parameters - none
    Output - returns a string variable with the value stored in driverName variable
    **/
    public String getDriverName() {
        return driverName;
    }

/*  ------------------------------------------
    Function - Making the driverName variable available for Java Garbage Collector to collect
    Parameters - None
    Output - set the driverName variable value to null
**/
    public void removeDriverName() {
        this.driverName=null;
    }
}
