
public class LogisticsCrew extends Crew {

	private int cargoCapacity;
	private QueueManager qm;

	public LogisticsCrew(String name, int cargoCapacity, QueueManager qm) {
		super(name,qm);
		this.cargoCapacity=cargoCapacity;
		this.qm = qm;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void doWork() {
		Arrival curr;
		curr = qm.logisticsQ.extract();
		capacityCheck(curr);
		forwardPlane(curr);
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
			qm.technicalQ.insert(curr);
		}
		else {
			qm.securityQ.insert(curr);
		}
	}

}
