package unibo.WasteServiceStatusGUI.model;

public class AddressForm {
    private String ip_led;
    private String ip_wasteservice;
    private String ip_trolley;
    private int port_led;
    private int port_trolley;
    private int port_wasteservice;

    public String getIp_led() {
        return ip_led;
    }

    public String getIp_wasteservice() {
        return ip_wasteservice;
    }

    public String getIp_trolley() {
        return ip_trolley;
    }

    public int getPort_led() {
        return port_led;
    }

    public int getPort_trolley() {
        return port_trolley;
    }

    public int getPort_wasteservice() {
        return port_wasteservice;
    }

    public void setIp_led(String ip_led) {
        this.ip_led = ip_led;
    }

    public void setIp_wasteservice(String ip_wasteservice) {
        this.ip_wasteservice = ip_wasteservice;
    }

    public void setIp_trolley(String ip_trolley) {
        this.ip_trolley = ip_trolley;
    }

    public void setPort_led(int port_led) {
        this.port_led = port_led;
    }

    public void setPort_trolley(int port_trolley) {
        this.port_trolley = port_trolley;
    }

    public void setPort_wasteservice(int port_wasteservice) {
        this.port_wasteservice = port_wasteservice;
    }
}
