import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Airport {//Input file location
    private String dataFileAddress;
    private int numOfTechCrews;//Number of technical crews in system from GUI
    private int numForSecurityDuration;//Duration of security check from GUI
    private int numOfSecurityCrews;//Number of security crews, dictated
    private int numOfLogisticsCrews;//Number of logistics crews, dictated
    private int numOfFuelingCrews;//Number of fueling crews dictated
    private int numOfManagementCrews;//Number of management crews, dictated
    private int numOfRunwayDirectors;//Number of runway directors, dictated
    private QueueManager qm;//Queue Manager holds all the queues
    private Vector<Flight> flightsVector;//Flights vector for all departures and arrivals
    private Vector<Runnable> workersVector;//Workers vector for all types of workers except runway directors

    public Airport(String dataFileAddress, int numOfTechCrews, int numForSecurityDuration){//Basic builder method
        System.out.println("Airport initiating");
        this.dataFileAddress = dataFileAddress;
        this.numOfTechCrews = numOfTechCrews;
        this.numForSecurityDuration = numForSecurityDuration;
        this.numOfSecurityCrews = 2;
        this.numOfLogisticsCrews = 3;
        this.numOfFuelingCrews = 2;
        this.numOfManagementCrews = 1;
        this.numOfRunwayDirectors =3 ;
        this.qm = new QueueManager();
        this.flightsVector = new Vector<Flight>();
        this.workersVector = new Vector<Runnable>();
        initializeAirport();
    }

    private void initializeAirport(){//Initializes the airport procedures
        readFile();
        initializeWorkers();
    }

    private void initializeWorkers(){//Initializes all instances of crews
        initializeRunwayDirectors();
        initializeFuelingCrews();
        initializeLogisticsCrews();
        initializeTechnicalCrews();
        initializeSecurityMans();
        initializeManagementCrews();
    }

    private void initializeRunwayDirectors(){//Initializes all instances of runway directors
        Vector<RunwayDirector> runwayDirectors = new Vector<RunwayDirector>();
        for(int i=0;i<numOfRunwayDirectors;i++){
            RunwayDirector newRD = new RunwayDirector((int) (Math.random()*10000), flightsVector.size(), this, runwayDirectors);
            workersVector.add(newRD);
            runwayDirectors.add(newRD);
        }
    }

    private void initializeFuelingCrews(){//Initializes all instances of fueling crews
        FuelCrew FC1 = new FuelCrew("Fueling1", 10000, this);
        FuelCrew FC2 = new FuelCrew("Fueling2", 5000, this);
        workersVector.add(FC1);
        workersVector.add(FC2);
    }

    private void initializeLogisticsCrews(){//Initializes all instances of logistics crews
        LogisticsCrew LC1 = new LogisticsCrew("Logistics1", 90, this);
        LogisticsCrew LC2 = new LogisticsCrew("Logistics2", 70, this);
        LogisticsCrew LC3 = new LogisticsCrew("Logistics3", 50, this);
        workersVector.add(LC1);
        workersVector.add(LC2);
        workersVector.add(LC3);
    }

    private void initializeTechnicalCrews(){//Initializes all instances of technical crews
        String crewName;
        for(int i=0;i<numOfTechCrews;i++){
            crewName = "Technical"+(i+1);
            TechnicalCrew TC = new TechnicalCrew(crewName, this);
            workersVector.add(TC);
        }
    }

    private void initializeSecurityMans(){//Initializes all instances of security men
        for(int i=0;i<numOfSecurityCrews;i++){
            SecurityMan SM;
            if(Math.random() <= 0.5){
                SM = new SecurityMan("Senior", numForSecurityDuration, this);
            }
            else{
                SM = new SecurityMan("Junior", numForSecurityDuration, this);
            }
            workersVector.add(SM);
        }
    }

    private void initializeManagementCrews(){//Initializes all instances of management crews
        for(int i=0;i<numOfManagementCrews;i++){
            String crewName = "Management"+(i+1);
            ManagementCrew MC = new ManagementCrew(crewName, this);
        }
    }

    private void readFile(){//Reads the file in the address of the static string, creates all according flights
        BufferedReader inFile=null;
        try
        {
            FileReader fr = new FileReader (this.dataFileAddress);
            inFile = new BufferedReader (fr);
            inFile.readLine();
            String[] separatedLine = inFile.readLine().split("\t");
            Flight tempF;
            do{
                if(isNumeric(separatedLine[3])) {//if this is a number, its an incoming flight
                    tempF = new Arrival(separatedLine[0], Integer.parseInt(separatedLine[1]), Integer.parseInt(separatedLine[2]), this, Integer.parseInt(separatedLine[3]));
                }
                else{
                    tempF = new Departure(separatedLine[0], Integer.parseInt(separatedLine[1]), Integer.parseInt(separatedLine[2]), this, separatedLine[3]);

                }
                this.flightsVector.add(tempF);
                separatedLine = inFile.readLine().split("\t");
            }while(inFile.ready() && separatedLine.length == 4);
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("The file " + this.dataFileAddress + " was not found.");
        }
        catch (IOException e) {
            System.out.println(e);
        }
        finally {
            try {
                inFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isNumeric(final String str) {//to check if string is a number, for flight identification

        if (str == null || str.length() == 0) {
            return false;
        }

        try {

            Integer.parseInt(str);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }

    }

    public QueueManager getQM(){//QueueManager getter
        return this.qm;
    }

    public int getNumOfFlightsToday(){//Number of flights to pass through the airport today getter
        return this.flightsVector.size();
    }

    public int getNumOfTechCrews(){//Number of tech crews in airport getter
        return this.numOfTechCrews;
    }

    public int getNumOfSecurityCrews(){//Number of security crews in airport getter
        return this.numOfSecurityCrews;
    }

    public int getNumOfLogisticsCrews(){//Number of logistics crews in airport getter
        return this.numOfLogisticsCrews;
    }

    public int getNumOfFuelingCrews(){//Number of fueling crews in airport getter
        return this.numOfFuelingCrews;
    }

    public Vector<Runnable> getWorkersVector() {//Workers vector getterW
        return workersVector;
    }
}
