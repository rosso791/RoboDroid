package dais.unive.it.robot.ActivityApp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dais.unive.it.robot.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    private static int TIME_OUT = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*try{
            EV3 ev3 = new EV3(new BluetoothConnection("EV3").connect());
            Log.e("Tag", "EV3 connected");
            Toast.makeText(this, "EV3 connected ",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Toast.makeText(this, "Cannot connect to EV3",Toast.LENGTH_SHORT).show();
            Log.e(TAG, "fatal error: cannot connect to EV3");
            e.printStackTrace();
        }*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}
//Toast visione messaggio su dispositivo.
