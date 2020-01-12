public class QueueManager {//This object will hold all relevant queues - all public for free access
    public Queue<Arrival> arrivals;
    public Queue<Departure> departures;
    public Queue<Arrival> technicalQ;
    public BoundedQueue<Arrival> fuelingQ;
    public Queue<FlightDetails> managementQ;
    public Queue<Arrival> logisticsQ;
    public Queue<Arrival> securityQ;

    public QueueManager(){
        this.arrivals = new Queue<Arrival>();
        this.departures = new Queue<Departure>();
        this.technicalQ = new Queue<Arrival>();
        this.fuelingQ = new BoundedQueue<Arrival>();
        this.managementQ = new Queue<FlightDetails>();
        this.logisticsQ = new Queue<Arrival>();
        this.securityQ = new Queue<Arrival>();
    }

}
