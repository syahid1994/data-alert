package com.syahid.test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class App 
{
	
	public App(String bankCodes) {
        Map<String, String> mapAlert = getAlertInstancePerCode(bankCodes);
        for (String key: mapAlert.keySet()) {
        	sentAlert(key, mapAlert.get(key));
        }
	}
	
	private Map<String, String> getAlertInstancePerCode(String bankCodes) {
		System.out.println(bankCodes);
		bankCodes = bankCodes.replace(" ", "");
		bankCodes = bankCodes.toUpperCase();
		List<String> bankCodesArray = Arrays.asList(bankCodes.split(","));
		System.out.println(bankCodesArray);
		Map<String, String> mapListAlert = new HashMap<>();
		
		String fileSrc = "input/Data-Alert.txt";
        
		BufferedReader br = null;
        try {
        	InputStream is = App.class.getClassLoader().getResourceAsStream(fileSrc);
	        if (is != null) {
        		br = new BufferedReader(new FileReader(fileSrc));
	            String ln;
	
	            while ((ln = br.readLine()) != null) {
	                String[] lndata = ln.split(";");
	                String code = lndata[0];
	                String envi = lndata[1];
	                String port = lndata[2];
	                String name = lndata[3];
	                String status = lndata[4];
	                
	                if (bankCodesArray.size() > 0 && !bankCodesArray.contains(code.toUpperCase())) {
		                System.out.println("invalid");
		                System.out.println(code);
	                	continue;
	                }
	                System.out.println(code);
	                
	            	String message = mapListAlert.get(code.toUpperCase()) == null ? "" : mapListAlert.get(code.toUpperCase());
	            	message += String.format("- Envi %1$s Port %2$s terpantau %3$s \n", envi, port, status);
	            	mapListAlert.put(code, message);
	            }
	        } else {
	        	System.out.println("Invalid file");
	        }
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
        
        return mapListAlert;
	}
	
	private String getGreetings(String code) {
		Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        StringBuilder sb = new StringBuilder();
        sb.append("Selamat ");
        if (hourOfDay >= 5 && hourOfDay < 12) {
        	sb.append("Pagi");
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
        	sb.append("Siang");
        } else {
        	sb.append("Malam");
        }
        sb.append(" Rekan Bank ");
        sb.append(code);
        sb.append(",\n \n");
        sb.append("Mohon bantuan untuk Sign on pada envi berikut: \n \n");
        
        return sb.toString();
	}
	
	//ini saya simulasi nya sent nya pake println aja ya
	private void sentAlert(String code, String message) {
		System.out.println("==========================");
		message = getGreetings(code) + message + "Terima Kasih";
		System.out.println(message);
	}
	
	public static class AlertInstanceData {
		
		public String code;
		public String envi;
		public String port;
		public String name;
		public String status;
	}
	
    public static void main( String[] args )
    {
        System.out.println("Alert instance service ");
        System.out.println("======================= ");
        System.out.println("Please select institution will receive alert, separate with comma");
        System.out.println("Or fill blank if will sent to all institution");
        System.out.println("eg: BNI,MDR");
        System.out.print("Select here:");
    	Scanner in = new Scanner(System.in);
    	 
        String bankCodes = in.nextLine();
        new App(bankCodes);
    }
}
