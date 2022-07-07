import it.unibo.pojos.WasteServiceConfig
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class WasteServiceConfigurator {
    val MAXPB = 10;
    val  MAXPG = 10 ;
    val XIndoor = 0 ;
    val YIndoor = 0;
    val XPBox = 0 ;
    val YPBox = 0;
    val XGBox = 0 ;
    val YGBox = 0;
    val XHome = 0 ;
    val YHome = 0;
//	public static int rd = 1;
//	public static int dlimit = 20

    companion object {
        fun setTheConfiguration(resourceName: String) {
            var fis: FileInputStream? = null
            try {
                println("%%% setTheConfiguration from file:$resourceName")
                if (fis == null) {
                    fis = FileInputStream(File(resourceName))
                }
                val tokener = JSONTokener(fis)
                val obj = JSONObject(tokener)
                WasteServiceConfig.MAXPB = obj.getInt("maxPB")
                WasteServiceConfig.MAXPG = obj.getInt("maxPG")
                WasteServiceConfig.XIndoor = obj.getInt("XIndoor")
                WasteServiceConfig.YIndoor = obj.getInt("YIndoor")
                WasteServiceConfig.XPBox = obj.getInt("XPBox")
                WasteServiceConfig.YPBox = obj.getInt("YPBox")
                WasteServiceConfig.XGBox = obj.getInt("XGBox")
                WasteServiceConfig.YGBox = obj.getInt("YGBox")
                WasteServiceConfig.XHome = obj.getInt("XHome")
                WasteServiceConfig.YHome = obj.getInt("YHome")
                //rd = object.getInt("rd");
                //dlimit = object.getInt("dlimit");
            } catch (e: FileNotFoundException) {
                println("setTheConfiguration ERROR " + e.message)
            }
        }
    }
}