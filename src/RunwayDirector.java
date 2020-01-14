import java.util.Random;
import java.util.Vector;

public class RunwayDirector implements Runnable,Stoppable {
	private int runwayLength;
	private Random rand;
	private QueueManager qm;
	private boolean stop; 
	private int numOfFlightsToday;
	private int numOfFlightsThatPassed;
	private Vector<RunwayDirector> runwayDirectors;


	public RunwayDirector(int runwayLength, int numOfFlightsToday, QueueManager qm, Vector<RunwayDirector> runwayDirectors) {
		this.runwayLength = runwayLength;
		this.qm = qm;
		this.stop = false;
		this.numOfFlightsToday = numOfFlightsToday;
		this.numOfFlightsThatPassed = 0;
		this.runwayDirectors = runwayDirectors;
		Thread t = new Thread(this);
		t.start(); //test
	}

	@Override
	public void run() {
		while(!end() && !stop) {
			doWork();
			this.numOfFlightsThatPassed++;
		}
		for(int i=0; i<runwayDirectors.size(); i++) {
			runwayDirectors.get(i).stop();
		}
	}

	public void doWork() {
		if(qm.arrivals.size() == 0) {//no arrivals, can do departures
			Departure curr = qm.departures.extract();
			handleDeparture(curr);
		}
		else {//there are arrivals, must do arrivals
			Arrival curr = qm.arrivals.extract();
			handleArrival(curr);
		}
	}

	private void handleArrival(Arrival arrival) {
		arrival.setLatestTreater(this);
		int depTime = (rand.nextInt(6) + 5);
		try {
			Thread.sleep(depTime*1000);
			arrival.increaseTime(depTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(Math.random()<=0.25) {
			qm.technicalQ.insert(arrival);
		}
		else {
			qm.logisticsQ.insert(arrival);
		}
	}

	private void handleDeparture(Departure departure) {
		departure.setLatestTreater(this);
		int depTime = (rand.nextInt(6) + 5);
		try {
			Thread.sleep(depTime*1000);
			departure.increaseTime(depTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		FlightDetails det = departure.getFlightDetails();
		qm.managementQ.insert(det);
	}

	public void stop() {
		this.stop = true;
	}
	
	public int getNumOfFlightsThatPassed() {
		return this.numOfFlightsThatPassed;
	}

	private boolean end() {
		int sumNumOfFlights=0;
		for(int i=0; i<runwayDirectors.size(); i++) {
			RunwayDirector curr = runwayDirectors.get(i);
			sumNumOfFlights = sumNumOfFlights + curr.getNumOfFlightsThatPassed();
		}
		return this.numOfFlightsToday == sumNumOfFlights;
	}
}
