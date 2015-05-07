package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class readCSV {
	
	public static void hello(){
		System.out.println("Hello");
	}
	
	
	
	public static void main(String[] args) {
		readCSV obj = new readCSV();
		obj.run(); 
		
	}
	
	public String gettimeZoneId(String lng, String lat)
        throws MalformedURLException, IOException, org.json.simple.parser.ParseException {
         
        URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&sensor=true");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String timeZoneId = "";
 
        try {
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String result, line = reader.readLine();
            result = line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
 
            JSONParser parser = new JSONParser();
            JSONObject rsp = (JSONObject) parser.parse(result);
 
            if (rsp.containsKey("results")) {
                JSONArray matches = (JSONArray) rsp.get("results");
                JSONObject data = (JSONObject) matches.get(0); //TODO: check if idx=0 exists
                timeZoneId = (String) data.get("timeZoneId");
            }
 
            return "";
        } finally {
            urlConnection.disconnect();
            return timeZoneId;
        }
    }
	
	public void run() {
		String csvFile = "test.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try { 
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
			// use comma as separator
				String[] str = line.split(cvsSplitBy);
				try {
					gettimeZoneId(str[1],str[2]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	} 
}

