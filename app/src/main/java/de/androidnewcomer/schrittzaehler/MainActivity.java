package de.androidnewcomer.schrittzaehler;

//import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //private SensorManager sensorManager;
    //private Sensor sensor;
    //private int schritte = 0;
    //private ErschuetterungsHandler handler = new ErschuetterungsHandler();
    //private ErschuetterungListener listener = new ErschuetterungListener(handler);
    private TextView textView;
    private EreignisHandler ereignisHandler = new EreignisHandler();

    private class EreignisHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            textView.setText(Integer.toString(msg.what));
        }
    }


    /*
        // ErschuetterungsHandler ist eine lokale Klasse innerhalb der MainActivity. So kann sie leicht auf die Attribute und Methoden der umschließenden Klasse zugreifen
    private class ErschuetterungsHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            textView.setText(Integer.toString(msg.what));
            schritte++;
            aktualisiereAnzeige();
        }
    } */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = findViewById(R.id.schritte);
        findViewById(R.id.zuruecksetzen).setOnClickListener(this);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);

        /*
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        aktualisiereAnzeige();
        findViewById(R.id.button).setOnClickListener(this);
         */
    }


        // hier können Sie in der Activity ein Attribut für den Listener erzeugen und ihn in der Methode onResume() mit dem Beschleunigungssensor verdrahten:
    @Override
    protected void onResume() {
        super.onResume();
        //sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        SchrittzaehlerService.ereignisHandler = ereignisHandler;
        textView.setText(Integer.toString(SchrittzaehlerService.schritte));
    }

/*
    @Override
    protected void onPause() {
        sensorManager.unregisterListener(listener);
        super.onPause();
    }


    private void aktualisiereAnzeige() {
        textView.setText(Integer.toString(schritte));
    }
    */


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.start) {
            startService(new Intent(this, SchrittzaehlerService.class));
            SchrittzaehlerService.ereignisHandler = ereignisHandler;
        }
        if(view.getId()==R.id.stop) {
            stopService(new Intent(this, SchrittzaehlerService.class));
        }
        if(view.getId()==R.id.zuruecksetzen) {
            SchrittzaehlerService.schritte=0;
            textView.setText(Integer.toString(0));
        }
    }

    /* --> ALT !!!
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.button) {
            schritte=0;
            aktualisiereAnzeige();
        }
    } */
}