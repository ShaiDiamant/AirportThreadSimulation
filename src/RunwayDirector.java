import java.util.Random;

public class RunwayDirector implements Runnable,Stoppable {
	private int runwayLength;
	private Random rand;
	private QueueManager qm;
	private boolean stop;
	
	
 	public RunwayDirector(int runwayLength, QueueManager qm) {
 		this.runwayLength = runwayLength;
		this.qm = qm;
 		this.stop = false;
 		Thread t = new Thread(this);
 		t.start(); //test
	}

	@Override
	public void run() {
		while(!stop) {
			doWork();
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

}
