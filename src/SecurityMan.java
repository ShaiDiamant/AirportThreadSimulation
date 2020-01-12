
public class SecurityMan implements Runnable{
	
	private String rank;
	private int checkTime;
	private boolean stop;
	private QueueManager qm;
	
	public SecurityMan (String rank, int checkTime, QueueManager qm) {
		this.rank=rank;
		this.checkTime=checkTime;
		this.stop=false;
		this.qm = qm;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {
		Arrival curr;
		while(!stop) {
			curr = qm.securityQ.extract();
			suspiciousObjectCheck(curr);
			forwardPlane(curr);
		}
	}
	
	public void suspiciousObjectCheck(Arrival curr) {
		try {
			Thread.sleep(checkTime*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(Math.random()<=0.05) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void forwardPlane(Arrival curr) {
		curr.setLatestTreater(this);
		qm.fuelingQ.insert(curr);
	}
	
	public void stop() {
		this.stop = true;
	}

}
