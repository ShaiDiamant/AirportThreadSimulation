
abstract public class Crew implements Runnable {
	
	protected String nameCrew;
	protected boolean stop;
	
	public Crew (String name) {
		this.nameCrew=name;
		this.stop=false;
	}
	public void stop() {
		this.stop = true;
	}
	public void run(){
		while(!stop){
			doWork();
		}
	}
	abstract public void doWork();

	

}
