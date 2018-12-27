package dais.unive.it.robot.Automation;


import android.os.Handler;
import android.provider.ContactsContract;

import java.util.Timer;
import java.util.TimerTask;

public class AutomationTask {

    private static Timer timer;
    private static TimerTask timerTask;
    private static Handler handler = new Handler();
    private static String logMessage;
    private static int stepCounter;
    private static int activePhase;
    private static int missingColor;
    private static int dischareColorAux;
    private static int chargeColorAux;
    private static boolean chargeToGateDone;
    private static boolean dischargeToPickUpDone;
    private static boolean pickUpDone;
    private static int pickUpTimeOut;

    private static final AutomationTask automationTask = new AutomationTask();
    private AutomationTask(){};



    //To stop timer
    public static void StopTimer(){
        if(timer != null){
            timer.cancel();
            timer.purge();
        }
    }

    //To start timer
    public static void StartTimer(){

        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run(){
                        DataExchange.IncreaseAliveCounter();

                        if ((DataExchange.PeekColorFromDischargeQueue() > 0)
                                && ((DataExchange.GetNotificationCode() == 0) || (DataExchange.GetNotificationCode() == 1))
                                && (activePhase == 0 || activePhase == 2)
                                && (missingColor == 0)){
                            activePhase = 2;
                            stepCounter++;

                            if (stepCounter==1){
                                if (DataExchange.GetColorQuantity(DataExchange.PeekColorFromDischargeQueue())==0){
                                    DataExchange.SetNotificationCode(-4);
                                    missingColor = DataExchange.PeekColorFromDischargeQueue();
                                    stepCounter = 0;
                                    activePhase = 0;
                                }
                                else {
                                    logMessage = "Move to position " + DataExchange.GetColorDescription(DataExchange.PeekColorFromDischargeQueue());
                                }
                            }
                            if (stepCounter==40){ logMessage="Open discharge gate";}
                            dischargeToPickUpDone = LegoDeviceSym.GetPresenceSensor1();
                            if (stepCounter==80){
                                if (!dischargeToPickUpDone) {
                                    DataExchange.SetNotificationCode(-2);
                                    stepCounter = 40-1;
                                }
                                logMessage="Send pick up notification";}
                            if (stepCounter>120){  // DataExchange.SetColorDischargeRequest(0);
                                if(dischargeToPickUpDone){
                                    if(pickUpTimeOut < 100){
                                        DataExchange.SetNotificationCode(1);
                                        pickUpTimeOut++;
                                    }
                                    else
                                    {
                                        DataExchange.SetNotificationCode(-3);
                                    }
                                }
                                else {
                                    DataExchange.DecreaseColorQuantity(DataExchange.GetColorDischargeRequest(),1);
                                    DataExchange.RemoveColorFromDischargeQueue();
                                    logMessage = "Task completed";
                                    stepCounter = 0;
                                    activePhase = 0;
                                    pickUpTimeOut = 0;
                                }
                            }

                        }
                        else if
                                (((chargeColorAux > 0) || (DataExchange.GetColorChargeRequest() > 0))
                                        && (DataExchange.GetNotificationCode() == 0)
                                        && (activePhase == 0 || activePhase == 1)) {
                            activePhase = 1;
                            stepCounter++;

                            if (stepCounter == 1) {
                                chargeColorAux = DataExchange.GetColorChargeRequest();
                                logMessage = "Move to position " + DataExchange.GetColorDescription(chargeColorAux);
                            }
                            if (stepCounter == 40) {
                                DataExchange.SetColorChargeRequest(0);
                                logMessage = "Move charge gate";
                            }
                            if (!chargeToGateDone) {chargeToGateDone = LegoDeviceSym.GetDistanceSensor() > 0;} //Retentive
                            if (stepCounter == 80) {
                                if (!chargeToGateDone) {
                                    DataExchange.SetNotificationCode(-1);
                                    stepCounter = 40-1;
                                }
                                logMessage = "Send charge notification";
                            }
                            if (stepCounter == 120) {
                                //DataExchange.SetColorChargeRequest(0);
                                DataExchange.IncreaseColorQuantity(chargeColorAux,1);
                                DataExchange.SetNotificationCode(2);
                                logMessage = "Task completed";
                                stepCounter = 0;
                                activePhase = 0;
                                if (missingColor == chargeColorAux){
                                    missingColor = 0;
                                }
                                chargeColorAux = 0;
                                chargeToGateDone = false;
                            }
                        }

                        else if ((missingColor > 0)
                                && (DataExchange.GetNotificationCode() == 0)
                                && (activePhase == 0)){
                            DataExchange.SetNotificationCode(-4);
                        }



                        DataExchange.SetActivePhase(activePhase);

                    }
                });
            }
        };
        timer.schedule(timerTask, 100, 100);
    }


    public static String GetLogMessage(){
        return logMessage;
    }

}
