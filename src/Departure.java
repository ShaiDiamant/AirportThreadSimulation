
public class Departure extends Flight {
	
	private String dest;//Destination of this flight

	public Departure(String flightCode, int numOfPassengers, int arrivalTime, QueueManager qm , String dest) {//Basic builder method
		super(flightCode, numOfPassengers, arrivalTime, qm);
		this.dest=dest;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {//All departures will wait the arrivalTime and then insert themselves into the departures queue
		try {
			Thread.sleep(arrivalTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		qm.departures.insert(this);
		System.out.println("Thread of flight "+this.flightCode+" ended");
	}

	@Override
	public FlightDetails getFlightDetails() {//Will make an instance of DepartureFlightDetails and return it
		return new DepartureFlightDetails(this.flightCode, this.numOfPassengers, this.totalTime, this.dest);
	}
	


}
