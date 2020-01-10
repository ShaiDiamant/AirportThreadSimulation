
public class LogisticsCrew extends Crew {

	private int cargoCapacity;
	private Queue<Arrival> logisticsQ;
	private Queue<Arrival> technicalQ;
	private Queue<Arrival> securityQ;

	public LogisticsCrew(String name, int cargoCapacity) {
		super(name);
		this.cargoCapacity=cargoCapacity;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		Arrival curr;
		while(!stop) {
			curr = logisticsQ.extract();
			capacityCheck(curr);
			forwardPlane(curr);
		}
	}
	
	private void capacityCheck(Arrival curr) {
		if(cargoCapacity >= curr.getNumOfBags()){
			int timeWait= curr.getNumOfBags();
			try {
				Thread.sleep(timeWait*100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			int timeWait= curr.getNumOfBags();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(timeWait*100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	private void forwardPlane(Arrival curr) {
		curr.setLatestTreater(this);
		if(Math.random()<=0.1) {
			technicalQ.insert(curr);
		}
		else {
			securityQ.insert(curr);
		}
	}

}
