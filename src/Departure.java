
public class Departure extends Flight {
	
	private String dest;

	public Departure(String flightCode, int numOfPassengers, int arrivalTime, Queue<Flight> flightsQ , String dest) {
		super(flightCode, numOfPassengers, arrivalTime, flightsQ);
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
		flightsQ.insert(this);
	}
	
	public String getDestination() {
		return this.dest;
	}

	@Override
	public FlightDetails getFlightDetails() {
		return new DepartureFlightDetails(this.flightCode, this.numOfPassengers, this.arrivalTime, this.dest);
	}
	


}
