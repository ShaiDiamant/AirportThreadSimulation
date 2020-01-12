import java.util.Random;

public class TechnicalCrew extends Crew {
	private int totalCost;
	
	public TechnicalCrew(String name, QueueManager qm) {
		super(name, qm);
		totalCost = 0;
	}

	@Override
	public void run() {
		Random rand = new Random();
		while(!stop) {
			Arrival curr = qm.technicalQ.extract();
			fixFlight(rand.nextInt(3)+3);
			this.totalCost = this.totalCost + (rand.nextInt(501)+500);
			forwardPlane(curr);
		}
	}
	
	private void forwardPlane(Arrival curr) {
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
	
	public void fixFlight(int time) {
		try {
			Thread.sleep(time*3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
