
public class Arrival extends Flight {
	private int numOfBags;
	private int totalCost;
	private boolean isSecurityIssue;
	private int totalTime;

	public Arrival(String flightCode, int numOfPassengers, int arrivalTime, QueueManager qm, int numOfBags) {
		super(flightCode, numOfPassengers, arrivalTime, qm);
		this.numOfBags = numOfBags;
		this.totalCost=0;
		this.totalTime=0;
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
		return this.totalCost;
	}
	
	public void securityIssue() {
		this.isSecurityIssue = true;
	}
	
	public boolean getSecurityIssue() {
		return this.isSecurityIssue;
	}
	
	public void increaseCost(int c) {
		this.totalCost = this.totalCost+c;
	}
	
	public void increaseTime(int t) {
		this.totalTime=this.totalTime+t;
	}

	@Override
	public FlightDetails getFlightDetails() {
		return new ArrivalFlightDetails(this.flightCode, this.numOfPassengers, this.arrivalTime,
				this.numOfBags, this.totalCost, this.isSecurityIssue);
	}
}
