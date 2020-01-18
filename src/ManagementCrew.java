
public class ManagementCrew extends Crew {

	private int numOfFlightsToday;//Total num of flights that are coming in or out today
	private int numOfFlightsThatPassed;//Total num of flights that passed so far

	public ManagementCrew (String name, Airport ap) {//Basic builder method
		super(name,ap);
		this.numOfFlightsToday = ap.getNumOfFlightsToday();
		this.numOfFlightsThatPassed = 0;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {//Will check if all flights passed, if not, will do work. if yes, will end day for all workers
		while (!end()) {
			doWork();
		}
		endDayForAll();
		System.out.println("Manager thread has ended");
	}

	public void doWork() {//Takes a flight detail from the q, enters info to SQL and prints flight details to console
		FlightDetails curr;
		curr = qm.managementQ.extract();
		enterInfo();
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

	private void printDetails(FlightDetails curr) {//Prints flight details to console
		System.out.println("Flight Details:");
		System.out.println("Flight Code: " +curr.getFlightCode() );
		System.out.println("Total Time In Airfield: " + curr.getTimeInAirfield());
		if(curr instanceof ArrivalFlightDetails) {
			System.out.println("Cost For Technical Treatment: " + ((ArrivalFlightDetails)curr).getCost());
		}
		else {
			System.out.println("Cost For Technical Treatment: 0");
		}
	}

	private void printFlightsDetails() {//Prints the summary of today's flights
		int sumOfPassangers = 	findNumOfPassangers();
		System.out.println("The number of passengers during ths day: " + sumOfPassangers);
		int sumOfCargo = findNumOfCargo();
		System.out.println("The total amount of cargo during the day: " + sumOfCargo);
		int sumCost = findCostOfFlights();
		System.out.println("The cost of technical treatments to flights today: " + sumCost);
		int sumOfGas = findSumOfGas();
		System.out.println("The amount of gas during the day: " + sumOfGas);
	}	

	private int findNumOfPassangers() {//Calculates number of passengers passed today
		int sumOfPassangers = 0;
		for(int i=0; i<qm.managementQ.size(); i++) {
			FlightDetails curr = qm.managementQ.extract();
			sumOfPassangers = sumOfPassangers+curr.getNumOfPassengers();
		}
		return sumOfPassangers;
	}

	private int findNumOfCargo() {//Calculates number of bags that airport unloaded today
		int sumOfCargo = 0;
		for(int i=0; i<qm.managementQ.size(); i++) {
			FlightDetails curr = qm.managementQ.extract();
			if(curr instanceof ArrivalFlightDetails) {
				sumOfCargo = sumOfCargo + ((ArrivalFlightDetails)curr).getCargo();
			}
		}
		return sumOfCargo;
	}

	private int findCostOfFlights() {//Calculates total cost of flights today
		int sumCost = 0;
		for(int i=0; i<qm.managementQ.size(); i++) {
			FlightDetails curr = qm.managementQ.extract();
			if(curr instanceof ArrivalFlightDetails) {
				sumCost = sumCost + ((ArrivalFlightDetails)curr).getCost();
			}
		}
		return sumCost;
	}
	
	private int findSumOfGas() {//Calculates total amount of fuel spent today
		int sumGas = 0;
		for(int i=0; i<qm.managementQ.size(); i++) {
			FlightDetails curr = qm.managementQ.extract();
			if(curr instanceof ArrivalFlightDetails) {
				sumGas = sumGas + ((ArrivalFlightDetails)curr).getAmountOfFuel();
			}
		}
		return sumGas;
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



}
