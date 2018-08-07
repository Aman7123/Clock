package stats;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import clockHelper.Helper;

public class StatsCollector {
	private static String localloop = "f528764d624db129b32c21fbca0cb8d6";
	
	public static void writeStats() {
		try {
			sendToServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static String makeJSON() {
		Map<String, Serializable> obj = new LinkedHashMap<String, Serializable>();
			obj.put("time", Helper.getPrintTime());
			obj.put("date", Helper.getPrintDate());
			obj.put("appver", Helper.getVer());
			obj.put("ip", getIP());
			JSONArray detail = new JSONArray();
				JSONObject accname = new JSONObject();
					accname.put("acc-name", System.getProperty("user.name"));
				detail.add(accname);
				JSONObject osname = new JSONObject();
					osname.put("os-name", System.getProperty("os.name"));
				detail.add(osname);
				JSONObject osversion = new JSONObject();
					osversion.put("os-version", System.getProperty("os.version"));
				detail.add(osversion);
				JSONObject arch = new JSONObject();
					arch.put("arch", System.getProperty("os.arch"));
				detail.add(arch);
				JSONObject jversion = new JSONObject();
					jversion.put("java-version", System.getProperty("java.version"));
				detail.add(jversion);
			obj.put("details", detail);
		return JSONValue.toJSONString(obj);
	}
	
	public static String getIP() {
		String myip = null;
		try {
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			myip = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myip;
	}
	
	public static String getuserUUID() {
		return StringtoMD5(getIP());
	}
	
	public static String StringtoMD5(String text) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(text.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			while(hashtext.length() < 32) {
				hashtext = "0"+hashtext;
			}
			return hashtext;
		} catch (Exception e) {
			e.printStackTrace();
			return localloop;
		}
	}
	
	public static void sendToServer() {
		try {
			String address = "http://24.126.101.7:80/javaapp/test2.php?uuid=" + getuserUUID() + "&time=" + Helper.getPrintTime() + "&date=" + Helper.getPrintDate() +  "&appver=" + Helper.getVer();
	        URL url = new URL(address);
	        
	        InputStream html = null;
	
	        html = url.openStream();
	        
	        int c = 0;
	        StringBuffer buffer = new StringBuffer("");
	
	        while(c != -1) {
	            c = html.read();
	            
	        buffer.append((char)c);
	        }
	        System.out.println(buffer.toString());
	        html.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

