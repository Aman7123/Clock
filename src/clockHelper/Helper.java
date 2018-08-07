package clockHelper;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import updater.Updater;
import desktopClock.Time;

public class Helper {

	private static TrayIcon trayIcon;
	private static Image taskimg;
	static int Version = 4;

	public static String gettime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(ReadXMLFile.readConfig("timeformat"));
		return sdf.format(cal.getTime());
	}

	public static String getdate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(ReadXMLFile.readConfig("dateformat"));
		return sdf.format(cal.getTime());
	}

	public static void checkconfig() {
		if (!new File("ClockConfig.xml").exists()) {
			WriteXMLFile.createConfig();
			System.out.println("Config not found - Creating one");
		} else if (new File("ClockConfig.xml").exists()) {
			System.out.println("Config Found");
		}
	}

	public static void savePoints() {
		Point doubloc = Time.dialog.getLocation();
		String x = String.valueOf((int) doubloc.getX());
		String y = String.valueOf((int) doubloc.getY());
		System.out.println(x + ", " + y + " - Points that get saved");
		ModifyXMLFile.modifyConfig("lastx", x);
		ModifyXMLFile.modifyConfig("lasty", y);
	}

	public static void loadlastloc() throws FileNotFoundException, IOException {
		int lastx = Integer.parseInt(ReadXMLFile.readConfig("lastx"));
		int lasty = Integer.parseInt(ReadXMLFile.readConfig("lasty"));
		Time.dialog.setLocation(lastx, lasty);
	}

	public static boolean checkversion() {
		boolean newupdate = false;
		try {
			if (Integer.parseInt(Updater.getLatestVersion()) > Version) {
				runupdate();
			} else {
				if(new File("clockupdate.zip").exists()) {
					new File("clockupdate.zip").delete();
					System.out.println("Cleaned up from last update");
				}
				System.out.println("No update needed");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return newupdate;
	}

	private static void runupdate() {
		System.out.println("Update Found");
		if(new File("ClockUpdater.jar").exists()) {
	    	String[] run = {"java","-jar","ClockUpdater.jar"};
	        try {
	            Runtime.getRuntime().exec(run);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        System.exit(0);
		} else {
			System.out.println("Could not update");
		}
	}

	public static void sysTray() {
		if (SystemTray.isSupported()) {

			SystemTray sysTray = SystemTray.getSystemTray();
			taskimg = Toolkit.getDefaultToolkit().getImage(Helper.class.getResource("/resources/whiteclock.png"));
			PopupMenu menu = new PopupMenu();
			
			menu.add(versionItem());
			menu.add(resetloc());
			menu.add(exitbtn());

			trayIcon = new TrayIcon(taskimg, "Desktop Clock", menu);
			trayIcon.setImageAutoSize(true);

			try {
				sysTray.add(trayIcon);
			} catch (AWTException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static MenuItem exitbtn() {
		MenuItem exit = new MenuItem("Exit");

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeapp();
			}
		});

		return exit;
	}
	
	public static MenuItem versionItem() {
		MenuItem exit = new MenuItem("Version: " + Version);
		return exit;
	}

	public static void closeapp() {
		Helper.savePoints();
		System.exit(0);	
	}

	public static MenuItem resetloc() {
		MenuItem resetloc = new MenuItem("Reset Location");

		resetloc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Time.dialog.setLocation(0, 0);
			}
		});

		return resetloc;
	}
	
	public static String getPrintTime() {
		String sdf = new SimpleDateFormat("h:mmaa").format(Calendar.getInstance().getTime());
		return sdf;
	}
	
	public static String getPrintDate() {
		String date = new SimpleDateFormat("MM/d/y").format(Calendar.getInstance().getTime());
		return date;
	}
	public static int getVer() {
		return Version;
	}
	
	public static void updateNotify() {
		System.out.println("Just Updated");
	}
}
