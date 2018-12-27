package dais.unive.it.robot.Automation;

public class LegoDeviceSym {

    private static int distanceSensor;
    private static boolean presenceSensor1;

    private static final LegoDeviceSym legoDeviceSym = new LegoDeviceSym();
    private LegoDeviceSym(){};

    public static void SetDistanceSensor(int distance){ distanceSensor = distance;}
    public static int GetDistanceSensor(){return distanceSensor;}

    public static void SetPresenceSensor1(boolean presence){presenceSensor1 = presence;}
    public static boolean GetPresenceSensor1(){return presenceSensor1;}




}
