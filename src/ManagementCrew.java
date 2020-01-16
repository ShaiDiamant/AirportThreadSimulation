
public class ManagementCrew extends Crew {
	
	private int numOfFlightsToday;
	private int numOfFlightsThatPassed;
	
	public ManagementCrew (String name, QueueManager qm, int numOfFlightsToday) {
		super(name,qm);
		this.numOfFlightsToday = numOfFlightsToday;
		this.numOfFlightsThatPassed = 0;
	}

	@Override
	public void run() {
		while (!end()) {
			doWork();
		}
		endDayForAll();
	}

	public void doWork() {
		FlightDetails curr;
		curr = qm.managementQ.extract();
		enterInfo();
		printDetails(curr);
		this.numOfFlightsThatPassed++;
	}
	
	private void enterInfo() { //TODO: SQL
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void printDetails(FlightDetails curr) {
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
	
	private boolean end() {
		return this.numOfFlightsThatPassed == this.numOfFlightsToday;
	}
	
	private void endDayForAll() {
		endDayForTechnical();
		endDayForSecurity();
		endDayForLogistics();
		endDayForFuels();
	}
	
	private void endDayForTechnical() {
		for(int i=0; i<=GUI.numOfTechCrews; i++) {
			qm.technicalQ.insert(null);
		}
	}
	
	private void endDayForSecurity() {
		for(int i=0; i<=GUI.numForSecurityDuration; i++) {
			qm.technicalQ.insert(null);
		}
	}
	
	private void endDayForLogistics() {
		for(int i=0; i<=GUI.numOfLogisticsCrews; i++) {
			qm.technicalQ.insert(null);
		}
	}
	
	private void endDayForFuels() {
		for(int i=0; i<=GUI.numOfFuelingCrews; i++) {
			qm.technicalQ.insert(null);
		}
	}
	
	
	
}
