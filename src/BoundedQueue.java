
public class BoundedQueue<T> extends Queue<T> {

	private final int maxSize=8;

	public BoundedQueue() {
	}

	public synchronized boolean insert(T t) {
		while(queue.size() >= maxSize) {
			try {
				wait();
			}
			catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		boolean status = this.queue.add(t);
		notifyAll();
		return status;
	}
	
	public synchronized T extract() {
		while(queue.isEmpty()) {
			try {
				wait();
			}
			catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		T t = queue.get(0);
		queue.remove(t);
		notifyAll();
		return t;
	}

}
