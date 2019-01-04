package dais.unive.it.robot.Automation;


import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class DataExchange {

    //Singleton object which encloses all methods for data interface exchange handling

    private static final DataExchange dataExchange = new DataExchange();
    private static int colorDischargeRequest;
    private static int colorChargeRequest;
    private static int notificationCode;
    private static int aliveCounter;
    private static int activePhase;
    private static int[] colorQuantityArray = new int[5];

    private static Queue<Integer> colorRequestQueue = new LinkedList<>();
    private static Queue<Integer> notificationQueue = new LinkedList<>();

    //Constructor
    private DataExchange(){}

    //Returns singleton instance
    public static DataExchange GetInstance(){
        return dataExchange;
    }


    //Get the color discharge request
    public static int GetColorDischargeRequest(){
        return colorDischargeRequest;
    }

    //Set the color discharge request
    public static void SetColorDischargeRequest(int colorId){
        colorDischargeRequest = colorId;
    }

    //Get the color charge request
    public static int GetColorChargeRequest(){
        return colorChargeRequest;
    }

    //Set the color charge request
    public static void SetColorChargeRequest(int colorId){ colorChargeRequest = colorId; }

    //Peek color from queue
    public static int PeekColorFromDischargeQueue(){
        int colorToDischarge;
        if (colorRequestQueue.size() > 0) {
            colorToDischarge = colorRequestQueue.peek();
        }
        else
        {
            colorToDischarge = 0;
        }
        SetColorDischargeRequest(colorToDischarge);
        return colorToDischarge;
    }

    //Add color to queue
    public static void AddColorToDischargeQueue( int colorId){
        if (colorId > 0){ colorRequestQueue.add(colorId);}
    }

    //Remove color from queue
    public static void RemoveColorFromDischargeQueue(){
        colorRequestQueue.remove();
    }

    //Get all color request queue
    public static Queue<Integer> GetDischargeQueue(){
        return colorRequestQueue;
    }


    //Get the phase value
    public static int GetActivePhase(){
        return activePhase;
    }

    //Set the phase value
    public static void SetActivePhase(int activePhaseValue){ activePhase = activePhaseValue; }

    //Get notification code
    public static int GetNotificationCode(){
        return notificationCode;
    }

    //Set notification code
    public static void SetNotificationCode(int codeValue){
        notificationCode = codeValue;
    }

    //Get alive counter
    public static int GetAliveCounter(){
        return aliveCounter;
    }

    //Increase alive counter (rolling counter)
    public static void IncreaseAliveCounter(){
        if (aliveCounter > Integer.MAX_VALUE)
            aliveCounter = Integer.MIN_VALUE;
        else
            aliveCounter++;
    }

    //Get color quantity by id
    public static int GetColorQuantity(int colorId){
        return colorQuantityArray[colorId];
    }

    //Increase color quantity on n units by id
    public static void IncreaseColorQuantity(int colorId, int units){
        colorQuantityArray[colorId] += units;
    }

    //Decrease color quantity on n units by id
    public static void DecreaseColorQuantity(int colorId, int units){
        colorQuantityArray[colorId] -= units;
    }

    //Add notification to queue
    public static void AddNotificationToQueue(int notification){
        notificationQueue.add(notification);
    }

    //Peek notification from queue
    public static int PeekNotificationFromQueue(){
        int colorToDischarge;
        if (notificationQueue.size() > 0) {
            return notificationQueue.peek();
        }
        else
        {
            return  0;
        }

    }

    //Decode color (just visualization)
    public static String GetColorDescription(int colorId){
        String colorName;
        switch (colorId) {
            case 0: colorName = "None";
                break;
            case 1: colorName = "Red";
                break;
            case 2: colorName = "Blue";
                break;
            case 3: colorName = "Green";
                break;
            case 4: colorName = "Yellow";
                break;
            default: colorName = "None";
                break;
        }

        return colorName;
    }

    //Deconde notification (just visualization)
    //Negative ids reserved for alarms
    //Zero id reserved for no-notification
    //Positive ids reserved for information
    public static String GetNotificationDescription(){
        String notificationDescription;
        switch (notificationCode){
            case -9: notificationDescription = "Spare Alarm";
                break;
            case -8: notificationDescription = "Spare Alarm";
                break;
            case -7: notificationDescription = "Spare Alarm";
                break;
            case -6: notificationDescription = "Spare Alarm";
                break;
            case -5: notificationDescription = "Spare Alarm";
                break;
            case -4: notificationDescription = "Color not Present\r\nPlease fill with required color";
                break;
            case -3: notificationDescription = "Pick Up Timeout\r\nPlease remove object from Pick Up zone.";
                break;
            case -2: notificationDescription = "Discharge Stuck\r\nObject not in expected position during discharging phase.";
                break;
            case -1: notificationDescription = "Charge Stuck\r\nObject not in expected position during charging phase.";
                break;
            case 0: notificationDescription = "";
                break;
            case 1: notificationDescription = "Pick Up Ready";
                break;
            case 2: notificationDescription = "Charge Completed";
                break;
            case 3: notificationDescription = "Discharge Completed";
                break;
            case 4: notificationDescription = "Spare";
                break;
            case 5: notificationDescription = "Spare";
                break;
            case 6: notificationDescription = "Spare";
                break;
            case 7: notificationDescription = "Spare";
                break;
            case 8: notificationDescription = "Spare";
                break;
            case 9: notificationDescription = "Spare";
                break;
            default: notificationDescription = "Unhandled Notification";
                break;
        }
        return notificationDescription;
    }

    //Return phase description
    public static String GetActivePhaseDescription(){
        String activePhaseDescription;
        switch (activePhase){
            case 0:
                activePhaseDescription = "STAND BY";
                break;
            case 1:
                activePhaseDescription = "CHARGING";
                break;
            case 2:
                activePhaseDescription = "DISCHARGING";
                break;
            default:
                activePhaseDescription = "STAND BY";
                break;

        }

        return activePhaseDescription;
    }
}

