import java.util.Random;

public class TechnicalCrew extends Crew {


	public TechnicalCrew(String name, Airport ap) {//Basic builder method
		super(name, ap);
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void doWork() {//pulls an arrival flight from tech q, if null ends day, if not, treats and forwards
		Random rand = new Random();
		Arrival curr = qm.technicalQ.extract();
		if(curr == null) {
			this.dayEnd=true;
			return;
		}
		fixFlight(rand.nextInt(3)+3, curr);
		curr.increaseCost(rand.nextInt(501)+500);
		forwardPlane(curr);
	}

	private void forwardPlane(Arrival curr) {//checks who was the latest treater and forwards the plane to the appropriate queue
		curr.setLatestTreater(this);
		Object latestTreater = curr.getLatestTreater();
		if(latestTreater instanceof RunwayDirector) {
			qm.logisticsQ.insert(curr);
		}
		else if(latestTreater instanceof LogisticsCrew) {
			qm.securityQ.insert(curr);
		}
		else {
			FlightDetails f = curr.getFlightDetails();
			qm.managementQ.insert(f);
		}
	}

	public void fixFlight(int time,Arrival curr) {//handles technical issues
		try {
			Thread.sleep(time*1000);
			curr.increaseTime(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
