import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class RaspberryConfigurator {

    companion object {
        var dlimit = 20
        var ip_wasteService = "10.5.5.1"
        var porta_wasteService = 8078
        fun setTheConfiguration(resourceName: String) {
            var fis: FileInputStream? = null
            try {
                println("%%% setTheConfiguration from file:$resourceName")
                if (fis == null) {
                    fis = FileInputStream(File(resourceName))
                }
                val tokener = JSONTokener(fis)
                val obj = JSONObject(tokener)
                ip_wasteService = obj.getString("ip_wasteService")
                porta_wasteService = obj.getInt("porta_wasteService")
                dlimit = obj.getInt("dlimit")
            } catch (e: FileNotFoundException) {
                println("setTheConfiguration ERROR " + e.message)
            }
        }
    }
}