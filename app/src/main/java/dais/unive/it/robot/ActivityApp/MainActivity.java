package dais.unive.it.robot.ActivityApp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;


import dais.unive.it.robot.Automation.DataExchange;
import dais.unive.it.robot.R;
import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.comm.BluetoothConnection;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    private static int TIME_OUT = 1000;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DataExchange.GetInstance();
        /*DataExchange.IncreaseColorQuantity(1,5);
        DataExchange.IncreaseColorQuantity(2,1);
        DataExchange.IncreaseColorQuantity(3,2);*/
//        try{
            // ToDo rimettere il EV3 con il blocco try..catch
//            EV3 ev3 = new EV3(new BluetoothConnection("EV3").connect());
            new Handler().postDelayed(() -> {
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
            }, TIME_OUT);
        }
 /*       catch(IOException e ){
           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("No connection");
            //ToDo alertDialogBuilder.setIcon();  se si vuole inserire un immagine
            alertDialogBuilder.setMessage("Connessione bluetooth non trovata, controllare che il dispositivo sia connesso");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
            alertDialogBuilder.show();

            Log.e(TAG, "fatal error: cannot connect to EV3");
            e.printStackTrace();
        }

    }
*/
}
//Toast visione messaggio su dispositivo.
/*
interfaccia asyncVChannel aggiunto isConnected, implementato in spooledAsyncChannel, richiamato in ev3
 */