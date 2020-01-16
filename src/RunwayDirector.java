import java.util.Random;
import java.util.Vector;

public class RunwayDirector implements Runnable {
	private boolean dayEnd;
	private int runwayLength;
	private Random rand;
	private QueueManager qm;
	private int numOfFlightsToday;
	private int numOfFlightsThatPassed;
	private Vector<RunwayDirector> runwayDirectors;


	public RunwayDirector(int runwayLength, int numOfFlightsToday, QueueManager qm, Vector<RunwayDirector> runwayDirectors) {
		this.dayEnd = false;
		this.runwayLength = runwayLength;
		this.qm = qm;
		this.numOfFlightsToday = numOfFlightsToday;
		this.numOfFlightsThatPassed = 0;
		this.runwayDirectors = runwayDirectors;
		this.rand = new Random();
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		while(!end() && !dayEnd) {
			doWork();
		}
		for(int i=0;i<runwayDirectors.size()-1;i++){
			qm.arrivals.insert(null);
			qm.departures.insert(null);
		}
	}

	public void doWork() {
		if(qm.arrivals.size() == 0) {//no arrivals, can do departures
			Departure curr = qm.departures.extract();
			if(curr == null){
				this.dayEnd = true;
				return;
			}
			handleDeparture(curr);
		}
		else {//there are arrivals, must do arrivals
			Arrival curr = qm.arrivals.extract();
			if(curr == null){
				this.dayEnd = true;
				return;
			}
			handleArrival(curr);
		}
		this.numOfFlightsThatPassed++;
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

	public int getNumOfFlightsThatPassed() {
		return this.numOfFlightsThatPassed;
	}

	private boolean end() {
		int sumNumOfFlights=0;
		for (RunwayDirector curr : runwayDirectors) {
			sumNumOfFlights = sumNumOfFlights + curr.getNumOfFlightsThatPassed();
		}
		return this.numOfFlightsToday == sumNumOfFlights;
	}
}
