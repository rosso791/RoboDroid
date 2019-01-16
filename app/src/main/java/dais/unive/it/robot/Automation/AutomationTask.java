package dais.unive.it.robot.Automation;

import android.os.Handler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import it.unive.dais.legodroid.lib.EV3;

public class AutomationTask {

    private static Timer timer;
    private static TimerTask timerTask;
    private static Handler handler = new Handler();
    private static String logMessage = "Move to position " + DataExchange.GetColorDescription(DataExchange.PeekColorFromDischargeQueue());
    private static int stepCounter;
    private static int gVIndex = 0;
    private static int gVNotification = 0;
    private static int gVQtyColor = 0;
    private static int memCheckCounter = 0;
    private static boolean pickUpReady = false;
    private static boolean pickUpDone = true;
    private static EV3.Api gApi;
    private static int notification;
    private static int color;
    private static int qtyRed;
    private static int qtyBlue;
    private static int qtyGreen;
    private static int qtyYellow;

    private static final AutomationTask automationTask = new AutomationTask();
    private AutomationTask(){};

    public static AutomationTask GetInstance(){
        return automationTask;
    }

    //To stop timer
    public static void StopTimer(){
        if(timer != null){
            timer.cancel();
            timer.purge();
        }
    }

    //To start timer
    public static void StartTimer(EV3.Api api){

        EV3DataHandler ev3DataHandler = new EV3DataHandler(api);
        timer = new Timer();
        timerTask = new TimerTask(){
            public void run() {
                handler.post(new Runnable() {
                                 public void run() {
                                     gApi = api;
                                     try {
                                         if (memCheckCounter <= 2){
                                             if (stepCounter == 48){
                                                 DataExchange.SetNotificationCode(-5);
                                                 StopTimer();
                                             }
                                             stepCounter++;
                                             ev3DataHandler.sendDataToMailBox("MemAddressing", "Check");
                                             gVIndex = stepCounter * 4;
                                             int x = ev3DataHandler.getGlobalVariableIntValue(gVIndex).get();
                                             System.out.println("(" + gVIndex + ") " + x + " (" + memCheckCounter + ")");
                                             if (x == 111) {
                                                 gVNotification = gVIndex;
                                                 memCheckCounter++;
                                             }

                                             if (x == 9999) {
                                                 gVQtyColor = gVIndex;
                                                 memCheckCounter ++;
                                             }

                                             if (memCheckCounter == 2) {
                                                 ev3DataHandler.sendDataToMailBox("MemAddressing", "MemScanCompleted");
                                                 memCheckCounter++;
                                                 System.out.println("Done!");
                                             }

                                         }else {

                                             if (DataExchange.GetNotificationCode() == 1000)
                                                 ev3DataHandler.sendDataToMailBox("Reset", "ResetNotification");

                                             notification = ev3DataHandler.getGlobalVariableIntValue(gVNotification).get();
                                             color = ev3DataHandler.getGlobalVariableIntValue(gVQtyColor).get();

                                             qtyRed=((color%10000)-(color%1000))/1000;
                                             qtyBlue=((color%10)-(color%1))/1;
                                             qtyGreen=((color%100)-(color%10))/10;
                                             qtyYellow=((color%1000)-(color%100))/100;

                                             DataExchange.SetNotificationCode(notification);
                                             DataExchange.SetColorQuantity(1, qtyRed);
                                             DataExchange.SetColorQuantity(2, qtyBlue);
                                             DataExchange.SetColorQuantity(3, qtyGreen);
                                             DataExchange.SetColorQuantity(4, qtyYellow);

                                             if ((notification == 0) && pickUpReady) {
                                                 pickUpReady = false;
                                                 pickUpDone = true;
                                                 DataExchange.RemoveColorFromDischargeQueue();
                                             }

                                             if (notification == 2) {
                                                 pickUpReady = true;
                                                 pickUpDone = false;
                                                 ev3DataHandler.sendDataToMailBox("ColorRequest", "CIAO");
                                             }

                                             if (pickUpDone) {
                                                 switch (DataExchange.PeekColorFromDischargeQueue()) {
                                                     case 1:
                                                         ev3DataHandler.sendDataToMailBox("ColorRequest", "Red");
                                                         pickUpDone = false;
                                                         break;
                                                     case 2:
                                                         ev3DataHandler.sendDataToMailBox("ColorRequest", "Blue");
                                                         pickUpDone = false;
                                                         break;
                                                     case 3:
                                                         ev3DataHandler.sendDataToMailBox("ColorRequest", "Green");
                                                         pickUpDone = false;
                                                         break;
                                                     case 4:
                                                         ev3DataHandler.sendDataToMailBox("ColorRequest", "Yellow");
                                                         pickUpDone = false;
                                                         break;
                                                     default:
                                                         ev3DataHandler.sendDataToMailBox("ColorRequest", "CIAO");
                                                         break;
                                                 }
                                             }

                                         }
                                     } catch (IOException | InterruptedException | ExecutionException  e) {
                                         e.printStackTrace();
                                     }

                                 }
                });
            }
        };
        timer.schedule(timerTask, 100, 500);

    }

    public static String GetLogMessage(){
        return logMessage;
    }

}

