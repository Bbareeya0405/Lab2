public class Lab02 {

    private static int failures = 0;

    private static void check(String description, boolean condition) {
        System.out.println((condition ? "PASS: " : "FAIL: ") + description);
        if (!condition) {
            failures++;
        }
    }

    public static void main(String[] args) {
        check("count starts at 0", AirPurifier.getCount() == 0);

        AirPurifier a = new AirPurifier("Living room", 100);
        AirPurifier b = new AirPurifier("Bedroom", 40);

        check("count is 2 after creating two objects", AirPurifier.getCount() == 2);
        check("isValidSpeed(3) is true", AirPurifier.isValidSpeed(3));
        check("isValidSpeed(0) is false", !AirPurifier.isValidSpeed(0));
        check("isValidSpeed(4) is false", !AirPurifier.isValidSpeed(4));

        // ----- initial state -----
        System.out.println("\nInitial state:");
        System.out.println(a);
        System.out.println(b);
        check("new purifier is off", !a.isOn());
        check("new filter is 100%", a.getFilterLife() == 100.0);

        // ----- off purifier does nothing -----
        check("run() while off does not change PM2.5", a.run(10) == 100);
        check("setFanSpeed() while off is rejected", !a.setFanSpeed(2));

        // ----- turn on / speed -----
        a.turnOn();
        check("turnOn() sets on and speed 1", a.isOn() && a.getFanSpeed() == 1);
        check("setFanSpeed(3) accepted", a.setFanSpeed(3) && a.getFanSpeed() == 3);
        check("setFanSpeed(4) rejected, speed stays 3", !a.setFanSpeed(4) && a.getFanSpeed() == 3);
        check("setFanSpeed(0) rejected, speed stays 3", !a.setFanSpeed(0) && a.getFanSpeed() == 3);

        double pm = a.run(20);
        System.out.println("\nAfter running a for 20 min at speed 3:");
        System.out.println(a);
        check("PM2.5 dropped from 100 to 70", Math.abs(pm - 70.0) < 1e-9);
        check("filter wore from 100% to 94%", Math.abs(a.getFilterLife() - 94.0) < 1e-9);
        check("run(0) changes nothing", a.run(0) == pm);
        check("run(-5) changes nothing", a.run(-5) == pm);

        b.turnOn();
        b.setFanSpeed(3);
        b.run(100);
        check("PM2.5 never negative", b.getPm25() == 0);

        AirPurifier c = new AirPurifier("Office", 5000);
        c.turnOn();
        c.setFanSpeed(3);
        c.run(400);
        double pmAfterWear = c.getPm25();
        check("filter is 0% after heavy use", c.getFilterLife() == 0);
        check("worn filter removes no more dust", c.run(10) == pmAfterWear);
        c.replaceFilter();
        check("replaceFilter() restores 100%", c.getFilterLife() == 100.0);

        a.turnOff();
        check("turnOff() sets off and speed 0", !a.isOn() && a.getFanSpeed() == 0);

        check("count is 3 after creating three objects", AirPurifier.getCount() == 3);

        System.out.println("\n" + (failures == 0 ? "All checks passed." : failures + " check(s) failed."));
    }
}