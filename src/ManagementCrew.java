import java.util.Vector;

public class ManagementCrew extends Crew {
	
	private int numOfFlightsToday;
	private int numOfFlightsThatPassed;
	private Vector<Stoppable> workers;
	
	public ManagementCrew (String name, QueueManager qm, int numOfFlightsToday, Vector<Stoppable> workers) {
		super(name,qm);
		this.numOfFlightsToday = numOfFlightsToday;
		this.numOfFlightsThatPassed = 0;
		this.workers=workers;
	}

	@Override
	public void run() {
		while (!end()) {
			doWork();
		}
		for(int i=0; i<workers.size(); i++) {
			workers.get(i).stop();
		}	
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
}
