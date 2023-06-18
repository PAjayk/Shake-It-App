package com.example.shakeit;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager manager;
    View view;
    boolean isColor=false;
    long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view= findViewById(R.id.textview);
        view.setBackgroundColor(Color.CYAN);
        manager=(SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate=System.currentTimeMillis();
    }
    @Override
    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            getAcceletometer(event);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public void getAcceletometer(SensorEvent event){
        float[] values=event.values;
        float x=values[0];
        float y=values[1];
        float z=values[2];
        float accelarationSquareRoot=(x*x+y*y+z*z)
                / (SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH);
        long actualTime=System.currentTimeMillis();
        Toast.makeText(getApplicationContext(),String.valueOf(accelarationSquareRoot)+" "+SensorManager.GRAVITY_EARTH, Toast.LENGTH_SHORT).show();
        if(accelarationSquareRoot>=2)
        {
            if(actualTime-lastUpdate<200){
                return;
            }
            lastUpdate=actualTime;
            if(isColor){
                view.setBackgroundColor(Color.MAGENTA);
            }
            else{
                view.setBackgroundColor(Color.BLUE);
            }
            isColor=!isColor;
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        manager.unregisterListener(this);
    }
}