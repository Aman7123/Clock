package desktopClock;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import clockHelper.Helper;
import clockHelper.ReadXMLFile;

@SuppressWarnings("serial")
public class Time extends JDialog {
	
	static JLabel time = new JLabel();
	static JLabel date = new JLabel();
	public static Time dialog = new Time();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Helper.checkversion();
			Helper.sysTray();
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			Helper.loadlastloc();
			dialog.setVisible(true);
			//StatsCollector.writeStats();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public Time() {
		timer();
		Helper.checkconfig();
		
		getContentPane().setBackground(Color.decode(ReadXMLFile.readConfig("backgroundcolor")));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Time.class.getResource("/resources/clock.png")));
		setBounds(0, 0, 170, 110);
		
		buildlayout();
		builddate();
		buildtime();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Helper.closeapp();
			}
		});
		
	}
	
	public static void timer() {
		//Timer for time
		int delaytime = 3000; //milliseconds
		ActionListener timecount = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				time.setText(Helper.gettime());
			}
		};
		new Timer(delaytime, timecount).start();
		
		//Timer for date
		int delaydate = 3600000; //milliseconds
		ActionListener datecount = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				date.setText(Helper.getdate());
			}
		};
		new Timer(delaydate, datecount).start();
	}
	
	public void buildlayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{154, 0};
		gridBagLayout.rowHeights = new int[]{14, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}
	
	public void builddate() {
		date.setText(Helper.getdate());
		date.setForeground(Color.decode(ReadXMLFile.readConfig("timeanddatecolor")));
		date.setHorizontalTextPosition(SwingConstants.CENTER);
		date.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_date = new GridBagConstraints();
		gbc_date.insets = new Insets(0, 0, 5, 0);
		gbc_date.gridx = 0;
		gbc_date.gridy = 2;
		gbc_date.fill = GridBagConstraints.BOTH;
		getContentPane().add(date, gbc_date);
	}
	
	public void buildtime() {
		time.setText(Helper.gettime());
		time.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_time = new GridBagConstraints();
		gbc_time.insets = new Insets(0, 0, 5, 0);
		gbc_time.gridx = 0;
		gbc_time.gridy = 1;
		gbc_time.fill = GridBagConstraints.BOTH;
		time.setForeground(Color.decode(ReadXMLFile.readConfig("timeanddatecolor")));
		time.setHorizontalTextPosition(SwingConstants.CENTER);
		time.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(time, gbc_time);
	}
}
