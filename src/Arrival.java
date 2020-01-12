
public class Arrival extends Flight {
	private int numOfBags;
	private int cost;
	private boolean isSecurityIssue;

	public Arrival(String flightCode, int numOfPassengers, int arrivalTime, QueueManager qm, int numOfBags) {
		super(flightCode, numOfPassengers, arrivalTime, qm);
		this.numOfBags = numOfBags;
		this.cost=0;
		this.isSecurityIssue=false;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(arrivalTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		qm.arrivals.insert(this);
	}
	
	public int getNumOfBags() {
		return this.numOfBags;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public void securityIssue() {
		this.isSecurityIssue = true;
	}
	
	public boolean getSecurityIssue() {
		return this.isSecurityIssue;
	}
	
	public void increaseCost(int c) {
		this.cost = this.cost+c;
	}

	@Override
	public FlightDetails getFlightDetails() {
		return new ArrivalFlightDetails(this.flightCode, this.numOfPassengers, this.arrivalTime,
				this.numOfBags, this.cost, this.isSecurityIssue);
	}
}
