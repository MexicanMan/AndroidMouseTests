package com.test.sombrero.testbluetoothapp.Activities;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.test.sombrero.testbluetoothapp.Exceptions.ExceptionHandler;

import java.util.Set;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
    }

    public void showEnableBtDialog(Intent enableBtIntent) {
        throw new RuntimeException("There is no implementation for this method!");
    }

    public void outputFoundedDevices(BluetoothDevice device) {
        throw new RuntimeException("There is no implementation for this method!");
    }

}
