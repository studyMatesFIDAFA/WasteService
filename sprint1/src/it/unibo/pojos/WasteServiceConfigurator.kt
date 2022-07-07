import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class WasteServiceConfigurator {
//	public static int rd = 1;
//	public static int dlimit = 20

    companion object {
        var MAXPB = 10;
        var MAXGB = 10 ;
        var XIndoor = 0 ;
        var YIndoor = 0;
        var XPBox = 0 ;
        var YPBox = 0;
        var XGBox = 0 ;
        var YGBox = 0;
        var XHome = 0 ;
        var YHome = 0;
        var CurPWeight = 0;
        var CurGWeight = 0;
        fun setTheConfiguration(resourceName: String) {
            var fis: FileInputStream? = null
            try {
                println("%%% setTheConfiguration from file:$resourceName")
                if (fis == null) {
                    fis = FileInputStream(File(resourceName))
                }
                val tokener = JSONTokener(fis)
                val obj = JSONObject(tokener)
                MAXPB = obj.getInt("maxPB")
                MAXGB = obj.getInt("maxPG")
                XIndoor = obj.getInt("XIndoor")
                YIndoor = obj.getInt("YIndoor")
                XPBox = obj.getInt("XPBox")
                YPBox = obj.getInt("YPBox")
                XGBox = obj.getInt("XGBox")
                YGBox = obj.getInt("YGBox")
                XHome = obj.getInt("XHome")
                YHome = obj.getInt("YHome")
                CurPWeight = obj.getInt("CurPWeight")
                CurGWeight = obj.getInt("CurGWeight")
                //rd = object.getInt("rd");
                //dlimit = object.getInt("dlimit");
            } catch (e: FileNotFoundException) {
                println("setTheConfiguration ERROR " + e.message)
            }
        }
    }
}