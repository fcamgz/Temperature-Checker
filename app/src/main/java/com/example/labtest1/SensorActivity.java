package com.example.labtest1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView temperatureView;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private Boolean isSensorAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        temperatureView = (TextView) findViewById(R.id.temperatureLabel);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isSensorAvailable = true;
        }
        else{
            temperatureView.setText("Temperature Service is not available on this device");
            isSensorAvailable = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        temperatureView.setText(sensorEvent.values[0]+ "");
        if(Float.parseFloat(temperatureView.getText().toString()) < 0 ){
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
        }
        else if(Float.parseFloat(temperatureView.getText().toString()) >= 0  && Float.parseFloat(temperatureView.getText().toString())  <= 20){
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        }
        else if (Float.parseFloat(temperatureView.getText().toString()) > 20  && Float.parseFloat(temperatureView.getText().toString()) <= 40){
            getWindow().getDecorView().setBackgroundColor(Color.YELLOW);

        }
        else{
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        }
        temperatureView.setText(sensorEvent.values[0]+ " °C");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(isSensorAvailable){
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}