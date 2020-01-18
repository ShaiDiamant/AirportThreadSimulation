import java.util.TreeMap;
import java.util.Vector;

public class ManagementCrew extends Crew {

	private int numOfFlightsToday;//Total num of flights that are coming in or out today
	private int numOfFlightsThatPassed;//Total num of flights that passed so far
	private int totalNumOfPassengers;
	private int totalNumOfBags;
	private int totalCostOfTreatments;
	private int numOfSecurityIssues;
	TreeMap<String, Integer> destMap;

	public ManagementCrew (String name, Airport ap) {//Basic builder method
		super(name,ap);
		this.numOfFlightsToday = ap.getNumOfFlightsToday();
		this.numOfFlightsThatPassed = 0;
		this.destMap = new TreeMap<String, Integer>();
		this.totalNumOfBags = 0;
		this.totalNumOfPassengers = 0;
		this.totalCostOfTreatments = 0;
		this.numOfSecurityIssues = 0;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {//Will check if all flights passed, if not, will do work. if yes, will end day for all workers
		while (!end()) {
			doWork();
		}
		endDayForAll();
		printFlightsDetails();
	}

	public void doWork() {//Takes a flight detail from the q, enters info to SQL and prints flight details to console
		FlightDetails curr;
		curr = qm.managementQ.extract();
		enterInfo();
		sumInformation(curr);
		printDetails(curr);
		this.numOfFlightsThatPassed++;
	}

	private void enterInfo() { //Inserts flight information to SQL TODO: SQL
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sumInformation(FlightDetails curr){//Sums the current flight details information into the total amounts
		if(curr instanceof ArrivalFlightDetails){
			ArrivalFlightDetails arr = (ArrivalFlightDetails)curr;
			totalNumOfPassengers += arr.getNumOfPassengers();
			totalNumOfBags += arr.getCargo();
			totalCostOfTreatments += arr.getCost();
			if(arr.getSecurityIssue()){
				numOfSecurityIssues ++;
			}
		}
		else{
			DepartureFlightDetails dep = (DepartureFlightDetails)curr;
			totalNumOfPassengers += dep.getNumOfPassengers();
			String currDest = dep.getDestination();
			updateMap(currDest);
		}
	}

	private void updateMap(String dest){//Updates the destinations value in the treemap
		if(destMap.containsKey(dest)){
			int currVal = destMap.get(dest);
			destMap.put(dest, currVal+1);
		}
		else{
			destMap.put(dest, 1);
		}
	}

	private void printDetails(FlightDetails curr) {//Prints flight details to console
		System.out.println();
		System.out.println("Flight Details:");
		System.out.println("Flight Code: " +curr.getFlightCode() );
		System.out.println("Total Time In Airfield: " + curr.getTimeInAirfield() + " seconds");
		if(curr instanceof ArrivalFlightDetails) {
			System.out.println("Cost For Technical Treatment: " + ((ArrivalFlightDetails)curr).getCost());
		}
		else {
			System.out.println("Cost For Technical Treatment: 0");
		}
		System.out.println("--------------------------------------------------------------");
	}

	private void printFlightsDetails() {//Prints the summary of today's flights
		System.out.println("Summary of today's information:");
		System.out.println("--------------------------------------------------------------");
		System.out.println("The number of passengers during ths day: " + totalNumOfPassengers);
		System.out.println("The total amount of cargo during the day: " + totalNumOfBags);
		String mostCommonDest = findMostCommonDestination();
		if(mostCommonDest != null) {
			System.out.println("The most common destination for departures today was: " + mostCommonDest);
		}
		else{
			System.out.println("There were no departures today so there was no most common destination");
		}
		System.out.println("The cost of technical treatments to flights today: " + totalCostOfTreatments);
		int sumOfGas = findSumOfGas();
		System.out.println("The amount of gas spent during the day: " + sumOfGas);
		System.out.println("The number of security issues during the day: " + numOfSecurityIssues);
		int sumOfTrucks = findSumOfTrucks();
		System.out.println("The number of logistics special trucks during the day: "+sumOfTrucks);
	}

	private int findSumOfGas() {//Calculates total amount of fuel spent today
		int sumGas = 0;
		Vector<Runnable> workersVector = ap.getWorkersVector();
		for(int i=0; i<workersVector.size(); i++) {
			if(workersVector.get(i) instanceof FuelCrew) {
				FuelCrew curr = ((FuelCrew)workersVector.get(i));
				sumGas = sumGas + curr.getNumOfFuels()*1000;
			}
		}
		return sumGas;
	}

	private int findSumOfTrucks() {//Finds the amount of special logistics trucks that were called today
		int sumTruck = 0;
		Vector<Runnable> workersVector = ap.getWorkersVector();
		for(int i=0; i<workersVector.size(); i++) {
			if(workersVector.get(i) instanceof LogisticsCrew) {
				LogisticsCrew curr = ((LogisticsCrew)workersVector.get(i));
					sumTruck = sumTruck + curr.getNumOfCargoTrucks();
			}
		}
		return sumTruck;
	}

	private boolean end() {//Checks if all planes that are supposed to pass already passed
		return this.numOfFlightsThatPassed == this.numOfFlightsToday;
	}

	private void endDayForAll() {//Ends day for all workers
		endDayForTechnical();
		endDayForSecurity();
		endDayForLogistics();
		endDayForFuels();
	}

	private void endDayForTechnical() {//Ends day for all technical crews by inserting nulls to their queue according to amount of crews
		for(int i=0; i<=ap.getNumOfTechCrews(); i++) {
			qm.technicalQ.insert(null);
		}
	}

	private void endDayForSecurity() {//Ends day for all security men by inserting nulls to their queue according to amount of men
		for(int i=0; i<=ap.getNumOfSecurityCrews(); i++) {
			qm.securityQ.insert(null);
		}
	}

	private void endDayForLogistics() {//Ends day for all logistics crews by inserting nulls to their queue according to amount of crews
		for(int i=0; i<=ap.getNumOfLogisticsCrews(); i++) {
			qm.logisticsQ.insert(null);
		}
	}

	private void endDayForFuels() {//Ends day for all fueling crews by inserting nulls to their queue according to amount of crews
		for(int i=0; i<=ap.getNumOfFuelingCrews(); i++) {
			qm.fuelingQ.insert(null);
		}
	}

	private String findMostCommonDestination(){//Finds the most common destination in the treemap and returns it
		int max = 0;
		String maxDest = null;
		for(String dest : destMap.keySet()){
			if(destMap.get(dest) > max){
				max = destMap.get(dest);
				maxDest = dest;
			}
		}
		return maxDest;
	}



}
