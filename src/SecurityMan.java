
public class SecurityMan implements Runnable{

	private boolean dayEnd;
	private String rank;
	private int checkTime;
	private QueueManager qm;

	public SecurityMan (String rank, int checkTime, QueueManager qm) {
		this.dayEnd = false;
		this.rank=rank;
		this.checkTime=checkTime;
		this.qm = qm;
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		while (!dayEnd){
			doWork();
		}
	}

	public void doWork() {
		Arrival curr;
		curr = qm.securityQ.extract();
		if(curr == null){
			this.dayEnd = true;
			return;
		}
		suspiciousObjectCheck(curr);
		forwardPlane(curr);
	}

	public void suspiciousObjectCheck(Arrival curr) {
		try {
			Thread.sleep(checkTime*1000);
			curr.increaseTime(checkTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(Math.random()<=0.05) {
			try {
				Thread.sleep(2000);
				curr.increaseTime(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void forwardPlane(Arrival curr) {
		curr.setLatestTreater(this);
		qm.fuelingQ.insert(curr);
	}

}
