
public class Arrival extends Flight {
	private int numOfBags; //number of bags on this arrival flight
	private int totalCost; //total cost of the flight for our crews
	private boolean isSecurityIssue; //did this flight have a security issue


	public Arrival(String flightCode, int numOfPassengers, int arrivalTime, QueueManager qm, int numOfBags) {//Base builder method - no other type of arrival flights, so no other builders
		super(flightCode, numOfPassengers, arrivalTime, qm);
		this.numOfBags = numOfBags;
		this.totalCost=0;
		this.isSecurityIssue=false;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {//arrival flight will wait it's arrival time and then insert itself into the queue
		try {
			Thread.sleep(arrivalTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		qm.arrivals.insert(this);
		System.out.println("Thread of flight "+this.flightCode+" ended");
	}

	public int getNumOfBags() {//num of bags getter
		return this.numOfBags;
	}

	public void securityIssue() {//alerts that this flight had a security issue
		this.isSecurityIssue = true;
	}

	public void increaseCost(int c) {//increases this flight's cost by c
		this.totalCost = this.totalCost+c;
	}

	@Override
	public FlightDetails getFlightDetails() {//returns a FlightDetails object of this flight's details
		return new ArrivalFlightDetails(this.flightCode, this.numOfPassengers, this.totalTime,
				this.numOfBags, this.totalCost, this.isSecurityIssue);
	}
}
