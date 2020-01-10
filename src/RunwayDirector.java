import java.util.Random;

public class RunwayDirector implements Runnable {
	private int runwayLength;
	private Random rand;
	private Queue<Flight> arrivals;//TODO Queue class for everyone??
	private Queue<Flight> departures;
	private Queue<Arrival> technicalQ;
	private Queue<Arrival> logisticsQ;
	private Queue<FlightDetails> managementQ;
	private boolean stop;
	
	
 	public RunwayDirector(int runwayLength, Queue<Flight> arrivals,	Queue<Flight> departures,
 			Queue<Arrival> technicalQ, Queue<Arrival> logisticsQ, Queue<FlightDetails> managementQ) {
 		this.runwayLength = runwayLength;
 		this.arrivals = arrivals;
 		this.departures = departures;
 		this.technicalQ = technicalQ;
 		this.logisticsQ = logisticsQ;
 		this.managementQ = managementQ;
 		this.stop = false;
 		Thread t = new Thread(this);
 		t.start(); //test
	}

	@Override
	public void run() {
		while(!stop) {
			if(arrivals.size() == 0) {//no arrivals, can do departures
				Flight curr = departures.extract();
				handleDeparture(curr);
			}
			else {//there are arrivals, must do arrivals
				Flight curr = arrivals.extract();
				handleArrival(curr);
			}
		}
	}
	
	private void handleArrival(Flight arrival) {
		arrival.setLatestTreater(this);
		long depTime = (rand.nextInt(6) + 5)*1000;
		try {
			Thread.sleep(depTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(Math.random()<=0.25) {
			handleTechnicalError(arrival);
		}
		else {
			Arrival arr = (Arrival)arrival;
			this.logisticsQ.insert(arr);
		}
	}
	
	private void handleTechnicalError(Flight arrival) {
		Arrival arr = (Arrival)arrival;
		this.technicalQ.insert(arr);
	}
	
	private void handleDeparture(Flight departure) {
		departure.setLatestTreater(this);
		long depTime = (rand.nextInt(6) + 5)*1000;
		try {
			Thread.sleep(depTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		FlightDetails det = departure.getFlightDetails();
		this.managementQ.insert(det);
	}
	
	public void stop() {
		this.stop = true;
	}

}
