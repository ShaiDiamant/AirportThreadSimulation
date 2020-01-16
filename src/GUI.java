import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	public static Vector<Flight> flightsVector;
	public static Vector<Runnable> workersVector;
	public static QueueManager qm = new QueueManager();
	public static int numOfTechCrews;
	public static int numForSecurityDuration;
	public static final int numOfSecurityCrews = 2;
	public static final int numOfLogisticsCrews = 3;
	public static final int numOfFuelingCrews = 2;
	public static final int numOfManagementCrews = 1;
	public static final int numOfRunwayDirectors = 3;
	public static String flightsFileLocation = "FlightsData.txt";


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(199, 21, 133));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(54, 184, 97, 25);
		frame.getContentPane().add(btnStart);
		
		JButton btnExit = new JButton("Exit");

		btnExit.setBounds(239, 184, 97, 25);
		frame.getContentPane().add(btnExit);
		
		JLabel labelSecurityDuration = new JLabel("\u05DE\u05E9\u05DA \u05E2\u05D1\u05D5\u05D3\u05D4 \u05E9\u05DC \u05DE\u05D0\u05D1\u05D8\u05D7");
		labelSecurityDuration.setHorizontalAlignment(SwingConstants.CENTER);
		labelSecurityDuration.setFont(new Font("Arial", Font.BOLD, 15));
		labelSecurityDuration.setBounds(115, 104, 166, 16);
		frame.getContentPane().add(labelSecurityDuration);
		
		JLabel labelNumOfTechs = new JLabel("\u05DE\u05E1\u05E4\u05E8 \u05E6\u05D5\u05D5\u05EA\u05D9\u05DD \u05D8\u05DB\u05E0\u05D9\u05D9\u05DD \u05D1\u05E9\u05D3\u05D4");
		labelNumOfTechs.setHorizontalAlignment(SwingConstants.CENTER);
		labelNumOfTechs.setFont(new Font("Arial", Font.BOLD, 15));
		labelNumOfTechs.setBounds(115, 13, 177, 16);
		frame.getContentPane().add(labelNumOfTechs);
		textField = new JTextField();

		textField.setText("1");
		textField.setBounds(115, 42, 177, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("2");
		textField_1.setBounds(115, 133, 177, 22);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel labelNumsOnly1 = new JLabel("\u05DE\u05E1\u05E4\u05E8\u05D9\u05DD \u05D1\u05DC\u05D1\u05D3");
		labelNumsOnly1.setHorizontalAlignment(SwingConstants.CENTER);
		labelNumsOnly1.setVisible(false);
		labelNumsOnly1.setEnabled(true);
		labelNumsOnly1.setBounds(325, 45, 95, 16);
		frame.getContentPane().add(labelNumsOnly1);
		
		JLabel labelNumsOnly2 = new JLabel("\u05DE\u05E1\u05E4\u05E8\u05D9\u05DD \u05D1\u05DC\u05D1\u05D3");
		labelNumsOnly2.setHorizontalAlignment(SwingConstants.CENTER);
		labelNumsOnly2.setVisible(false);
		labelNumsOnly2.setEnabled(true);
		labelNumsOnly2.setBounds(325, 136, 95, 16);
		frame.getContentPane().add(labelNumsOnly2);
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(((key>=e.VK_0 && key<=e.VK_9) || (key>=e.VK_NUMPAD0 && key<=e.VK_NUMPAD9) || key == e.VK_DELETE || key == e.VK_BACK_SPACE) && !e.isShiftDown()) {
					textField.setEditable(true);
					textField.setBackground(Color.white);
				}
				else {
					textField.setEditable(false);
					textField.setBackground(Color.red);
				}
			}
		});
		
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(((key>=e.VK_0 && key<=e.VK_9) || (key>=e.VK_NUMPAD0 && key<=e.VK_NUMPAD9) || key == e.VK_DELETE || key == e.VK_BACK_SPACE) && !e.isShiftDown()) {
					textField_1.setEditable(true);
					textField_1.setBackground(Color.white);
				}
				else {
					textField_1.setEditable(false);
					textField_1.setBackground(Color.red);
				}
			}
		});
		
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textField.getText().length() == 0) {
					textField.setText("1");
					numOfTechCrews = 1;
				}
				else {
					numOfTechCrews = Integer.parseInt(textField.getText());
				}
				if(textField_1.getText().length() == 0) {
					textField_1.setText("2");
					numForSecurityDuration = 2;
				}
				else {
					numForSecurityDuration = Integer.parseInt(textField_1.getText());
				}
				
				startAirport(numForSecurityDuration, numOfTechCrews);
			}
		});
	}
	
	public static void startAirport(int numForSecurityDuration, int numOfTechTeams) {
		System.out.println("Security Duration: "+numForSecurityDuration+", Technical Teams: "+numOfTechTeams);
		flightsVector = new Vector<Flight>();
		workersVector = new Vector<Runnable>();
		readFile();
		initializeWorkers();
	}

	public static void initializeWorkers(){
		initializeRunwayDirectors();
		initializeFuelingCrews();
		initializeLogisticsCrews();
		initializeTechnicalCrews();
		initializeSecurityMans();
		initializeManagementCrews();
	}

	public static void initializeRunwayDirectors(){
		Vector<RunwayDirector> runwayDirectors = new Vector<RunwayDirector>();
		for(int i=0;i<numOfRunwayDirectors;i++){
			RunwayDirector newRD = new RunwayDirector((int) (Math.random()*10000), flightsVector.size(), qm, runwayDirectors);
			workersVector.add(newRD);
			runwayDirectors.add(newRD);
		}
	}

	public static void initializeFuelingCrews(){
		FuelCrew FC1 = new FuelCrew("Fueling1", 10000, qm);
		FuelCrew FC2 = new FuelCrew("Fueling2", 5000, qm);
		workersVector.add(FC1);
		workersVector.add(FC2);
	}

	public static void initializeLogisticsCrews(){
		LogisticsCrew LC1 = new LogisticsCrew("Logistics1", 90, qm);
		LogisticsCrew LC2 = new LogisticsCrew("Logistics2", 70, qm);
		LogisticsCrew LC3 = new LogisticsCrew("Logistics3", 50, qm);
		workersVector.add(LC1);
		workersVector.add(LC2);
		workersVector.add(LC3);
	}

	public static void initializeTechnicalCrews(){
		String crewName;
		for(int i=0;i<numOfTechCrews;i++){
			crewName = "Technical"+(i+1);
			TechnicalCrew TC = new TechnicalCrew(crewName, qm);
			workersVector.add(TC);
		}
	}

	public static void initializeSecurityMans(){
		for(int i=0;i<numOfSecurityCrews;i++){
			SecurityMan SM;
			if(Math.random() <= 0.5){
				SM = new SecurityMan("Senior", numForSecurityDuration, qm);
			}
			else{
				SM = new SecurityMan("Junior", numForSecurityDuration, qm);
			}
			workersVector.add(SM);
		}
	}

	public static void initializeManagementCrews(){
		for(int i=0;i<numOfManagementCrews;i++){
			String crewName = "Management"+(i+1);
			ManagementCrew MC = new ManagementCrew(crewName, qm, flightsVector.size());
		}
	}

	public static void readFile(){
		BufferedReader inFile=null;
		try
		{
			FileReader fr = new FileReader (flightsFileLocation);
			inFile = new BufferedReader (fr);
			inFile.readLine();
			String[] separatedLine;
			Flight tempF;
			while(inFile.ready()) {
				separatedLine = inFile.readLine().split("\t");
				if(isNumeric(separatedLine[3])) {//if this is a number, its an incoming flight
					tempF = new Arrival(separatedLine[0], Integer.parseInt(separatedLine[1]), Integer.parseInt(separatedLine[2]), qm, Integer.parseInt(separatedLine[3]));
				}
				else{
					tempF = new Departure(separatedLine[0], Integer.parseInt(separatedLine[1]), Integer.parseInt(separatedLine[2]), qm, separatedLine[3]);

				}
				flightsVector.add(tempF);
				System.out.println(tempF.flightCode);
			}
		}
		catch (FileNotFoundException exception)
		{
			System.out.println ("The file " + flightsFileLocation + " was not found.");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		finally {
			try {
				inFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isNumeric(final String str) {

		if (str == null || str.length() == 0) {
			return false;
		}

		try {

			Integer.parseInt(str);
			return true;

		} catch (NumberFormatException e) {
			return false;
		}

	}
}
