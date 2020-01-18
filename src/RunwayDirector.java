import java.util.Random;
import java.util.Vector;

public class RunwayDirector implements Runnable {
	private boolean dayEnd; //Has the day ended marker
	private int runwayLength; //Runway length randomly generated in main
	private QueueManager qm;//QueueManager instance for access to all queues
	private Airport ap;
	private int numOfFlightsToday; //Num of flights that are supposed to pass today
	private int numOfFlightsThatPassed;//Num of flights this instance has managed
	private Vector<RunwayDirector> runwayDirectors;//Vector of all rwdirectors for flights passed calculations


	public RunwayDirector(int runwayLength, int numOfFlightsToday, Airport ap, Vector<RunwayDirector> runwayDirectors) {//Basic builder method
		this.dayEnd = false;
		this.runwayLength = runwayLength;
		this.ap = ap;
		this.qm = ap.getQM();
		this.numOfFlightsToday = numOfFlightsToday;
		this.numOfFlightsThatPassed = 0;
		this.runwayDirectors = runwayDirectors;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {/*Will work until all flights have passed, when one thread recognized the day ended, he will
						insert nulls to the queues so the other threads know the day ended*/
		while(!end() && !dayEnd) {
			doWork();
		}
		for(int i=0;i<runwayDirectors.size()-1;i++){
			qm.arrivals.insert(null);
			qm.departures.insert(null);
		}
	}

	public void doWork() {//Will arrivals first, departures only if no arrivals in queue
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

	private void handleArrival(Arrival arrival) {//Lands the plane and forwards to technical q with 25% or to logistics q
		Random rand = new Random();
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

	private void handleDeparture(Departure departure) {//Handles the departure and submits flight details to management
		Random rand = new Random();
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

	public int getNumOfFlightsThatPassed() {//num of flights this instance passed getter
		return this.numOfFlightsThatPassed;
	}

	private boolean end() {//sums all flights that passed and returns true if all passed
		int sumNumOfFlights=0;
		for (int i=0;i<runwayDirectors.size();i++) {
			sumNumOfFlights = sumNumOfFlights + runwayDirectors.get(i).getNumOfFlightsThatPassed();
		}
		return this.numOfFlightsToday == sumNumOfFlights;
	}
}
