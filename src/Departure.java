
public class Departure extends Flight {
	
	private String dest;

	public Departure(String flightCode, int numOfPassengers, int arrivalTime, QueueManager qm , String dest) {
		super(flightCode, numOfPassengers, arrivalTime, qm);
		this.dest=dest;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {
		try {
			Thread.sleep(arrivalTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		qm.departures.insert(this);
	}
	
	public String getDestination() {
		return this.dest;
	}

	@Override
	public FlightDetails getFlightDetails() {
		return new DepartureFlightDetails(this.flightCode, this.numOfPassengers, this.arrivalTime, this.dest);
	}
	


}
