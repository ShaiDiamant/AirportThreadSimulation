import java.util.Random;

public class RunwayDirector implements Runnable {
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
			Flight curr = qm.departures.extract();
			handleDeparture(curr);
		}
		else {//there are arrivals, must do arrivals
			Flight curr = qm.arrivals.extract();
			handleArrival(curr);
		}
	}
	
	private void handleArrival(Flight arrival) {
		arrival.setLatestTreater(this);
		long depTime = (rand.nextInt(6) + 5)*1000;
		try {
			Thread.sleep(depTime);
			arrival.increaseTime(depTime/1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(Math.random()<=0.25) {
			handleTechnicalError(arrival);
		}
		else {
			Arrival arr = (Arrival)arrival;
			qm.logisticsQ.insert(arr);
		}
	}
	
	private void handleTechnicalError(Flight arrival) {
		Arrival arr = (Arrival)arrival;
		qm.technicalQ.insert(arr);
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
		qm.managementQ.insert(det);
	}
	
	public void stop() {
		this.stop = true;
	}

}
