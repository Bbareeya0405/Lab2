public class AirPurifier {

    public static final int MAX_SPEED = 3;
    private static int count = 0;


    private String name;
    private boolean on;
    private int fanSpeed;
    private double filterLife;
    private double pm25;

    public AirPurifier(String name, double initialPm25) {
        this.name = name;
        this.on = false;
        this.fanSpeed = 0;
        this.filterLife = 100.0;
        this.pm25 = Math.max(0, initialPm25);
        count++;
    }

    public void turnOn() {
        if (!on) {
            on = true;
            fanSpeed = 1;
        }
    }

    public void turnOff() {
        on = false;
        fanSpeed = 0;
    }

    public boolean setFanSpeed(int speed) {
        if (!on || !isValidSpeed(speed)) {
            return false;
        }
        fanSpeed = speed;
        return true;
    }

    public double run(int minutes) {
        if (!on || minutes <= 0) {
            return pm25;
        }
        if (filterLife > 0) {
            pm25 = Math.max(0, pm25 - fanSpeed * minutes * 0.5);
        }
        filterLife = Math.max(0, filterLife - fanSpeed * minutes * 0.1);
        return pm25;
    }

    public void replaceFilter() {
        filterLife = 100.0;
    }

    public String getName()       { return name; }
    public boolean isOn()         { return on; }
    public int getFanSpeed()      { return fanSpeed; }
    public double getFilterLife() { return filterLife; }
    public double getPm25()       { return pm25; }

    @Override
    public String toString() {
        return String.format("%s [%s, speed=%d, filter=%.1f%%, PM2.5=%.1f]",
                name, on ? "ON" : "OFF", fanSpeed, filterLife, pm25);
    }


    public static int getCount() {
        return count;
    }

    public static boolean isValidSpeed(int speed) {
        return speed >= 1 && speed <= MAX_SPEED;
    }
}