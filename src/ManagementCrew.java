
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
	
	public void printDetails(curr) {
		
	}

}
