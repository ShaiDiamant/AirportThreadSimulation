import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class GUI {

	private JFrame frame;//GUI Frame
	private JTextField textFieldNumOfTechCrews;//GUI text field for number of tech crews
	private JTextField textFieldSecurityCheckDuration;//GUI text field for duration of security checks
	private static String dataFileAddress = "FlightsData.txt";


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {//Main for gui initialization
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
		textFieldNumOfTechCrews = new JTextField();

		textFieldNumOfTechCrews.setText("1");
		textFieldNumOfTechCrews.setBounds(115, 42, 177, 22);
		frame.getContentPane().add(textFieldNumOfTechCrews);
		textFieldNumOfTechCrews.setColumns(10);
		
		textFieldSecurityCheckDuration = new JTextField();
		textFieldSecurityCheckDuration.setText("2");
		textFieldSecurityCheckDuration.setBounds(115, 133, 177, 22);
		frame.getContentPane().add(textFieldSecurityCheckDuration);
		textFieldSecurityCheckDuration.setColumns(10);
		
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
		
		textFieldNumOfTechCrews.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(((key>=e.VK_0 && key<=e.VK_9) || (key>=e.VK_NUMPAD0 && key<=e.VK_NUMPAD9) || key == e.VK_DELETE || key == e.VK_BACK_SPACE) && !e.isShiftDown()) {
					textFieldNumOfTechCrews.setEditable(true);
					textFieldNumOfTechCrews.setBackground(Color.white);
				}
				else {
					textFieldNumOfTechCrews.setEditable(false);
					textFieldNumOfTechCrews.setBackground(Color.red);
				}
			}
		});
		
		textFieldSecurityCheckDuration.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(((key>=e.VK_0 && key<=e.VK_9) || (key>=e.VK_NUMPAD0 && key<=e.VK_NUMPAD9) || key == e.VK_DELETE || key == e.VK_BACK_SPACE) && !e.isShiftDown()) {
					textFieldSecurityCheckDuration.setEditable(true);
					textFieldSecurityCheckDuration.setBackground(Color.white);
				}
				else {
					textFieldSecurityCheckDuration.setEditable(false);
					textFieldSecurityCheckDuration.setBackground(Color.red);
				}
			}
		});
		
		btnExit.addMouseListener(new MouseAdapter() {//Reaction for clicking the exit button
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		btnStart.addMouseListener(new MouseAdapter() {//Reaction for clicking the start button
			@Override
			public void mouseClicked(MouseEvent e) {
				int numOfTechCrews;
				int numForSecurityDuration;
				if(textFieldNumOfTechCrews.getText().length() == 0) {
					textFieldNumOfTechCrews.setText("1");
					numOfTechCrews = 1;
				}
				else {
					numOfTechCrews = Integer.parseInt(textFieldNumOfTechCrews.getText());
				}
				if(textFieldSecurityCheckDuration.getText().length() == 0) {
					textFieldSecurityCheckDuration.setText("2");
					numForSecurityDuration = 2;
				}
				else {
					numForSecurityDuration = Integer.parseInt(textFieldSecurityCheckDuration.getText());
				}
				Airport a1 = new Airport(dataFileAddress, numOfTechCrews, numForSecurityDuration);
			}
		});
	}
}
