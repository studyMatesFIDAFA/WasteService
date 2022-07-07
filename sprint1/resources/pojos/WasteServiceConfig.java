package pojos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.json.JSONObject;
import org.json.JSONTokener;
 


public class WasteServiceConfig {
	public static  int MAXPB = 10;	
	public static int MAXPG = 10 ;
	public static int XIndoor = 0 ;
	public static int YIndoor = 0;
	public static int XPBox = 0 ;
	public static int YPBox = 0;
	public static int XGBox = 0 ;
	public static int YGBox = 0;
	public static int XHome = 0 ;
	public static int YHome = 0;
//	public static int rd = 1;
//	public static int dlimit = 20;
	
	

	public static void setTheConfiguration(  ) {
		setTheConfiguration("../WasteServiceConfig.json");
	}
	
	public static void setTheConfiguration( String resourceName ) { 
		FileInputStream fis = null;
		try {
			System.out.println("%%% setTheConfiguration from file:" + resourceName);
			if(  fis == null ) {
 				 fis = new FileInputStream(new File(resourceName));
			}
	        JSONTokener tokener = new JSONTokener(fis);
	        JSONObject object   = new JSONObject(tokener);
	 		
	        MAXPB = object.getInt("maxPB");
	        MAXPG = object.getInt("maxPG");
	        XIndoor = object.getInt("XIndoor");
	        YIndoor = object.getInt("YIndoor");
	        XPBox = object.getInt("XPBox");
	        YPBox = object.getInt("YPBox");
	        XGBox = object.getInt("XGBox");
	        YGBox = object.getInt("YGBox");
	        XHome = object.getInt("XHome");
	        YHome = object.getInt("YHome");
	        //rd = object.getInt("rd");
	        //dlimit = object.getInt("dlimit");
 	        
		} catch (FileNotFoundException e) {
 			System.out.println("setTheConfiguration ERROR " + e.getMessage() );
		}

	}	
	 
}
