package dais.unive.it.robot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.comm.BluetoothConnection;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            EV3 ev3 = new EV3(new BluetoothConnection("EV3").connect());
            Log.e("Tag", "EV3 connected");
            Toast.makeText(this, "EV3 connected ",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Toast.makeText(this, "Fatal error: cannot connect to EV3",Toast.LENGTH_SHORT).show();
            Log.e(TAG, "fatal error: cannot connect to EV3");
            e.printStackTrace();
        }
    }
}
//Toast visione messaggio su dispositivo.