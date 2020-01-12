import java.util.Random;

public class TechnicalCrew extends Crew {
	private int totalCost;
	private Queue<Arrival> technicalQ;
	private Queue<Arrival> logisticsQ;
	private Queue<Arrival> securityQ;
	private Queue<FlightDetails> managementQ;

	public TechnicalCrew(String name, Queue<Arrival> technicalQ, Queue<Arrival> logisticsQ,
			Queue<Arrival> securityQ, Queue<FlightDetails> managementQ) {
		super(name);
		totalCost = 0;
		this.technicalQ = technicalQ;
		this.logisticsQ = logisticsQ;
		this.securityQ = securityQ;
		this.managementQ = managementQ;
	}

	@Override
	public void doWork() {
		Random rand = new Random();
		Arrival curr = technicalQ.extract();
		fixFlight(rand.nextInt(3)+3);
		this.totalCost = this.totalCost + (rand.nextInt(501)+500);
		forwardPlane(curr);
	}

	private void forwardPlane(Arrival curr) {
		curr.setLatestTreater(this);
		Object latestTreater = curr.getLatestTreater();
		if(latestTreater instanceof RunwayDirector) {
			this.logisticsQ.insert(curr);
		}
		else if(latestTreater instanceof LogisticsCrew) {
			this.securityQ.insert(curr);
		}
		else {
			FlightDetails f = curr.getFlightDetails();
			this.managementQ.insert(f);
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
