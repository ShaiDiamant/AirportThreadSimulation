
public class ManagementCrew extends Crew {
	
	public ManagementCrew (String name, QueueManager qm) {
		super(name,qm);
	}

	@Override
	public void run() {
		while (!stop && !end) {
			doWork();
		}

	}
	
	public void doWork() {
		FlightDetails curr;
		curr = qm.managementQ.extract();
		enterInfo();
		printDetails(curr);
	}
	
	public void enterInfo() { //TODO: SQL
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void printDetails(FlightDetails curr) {
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

}
