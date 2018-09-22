package com.example.sombrero.bluem.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.sombrero.bluem.MainViewModel;
import com.example.sombrero.bluem.R;
import com.example.sombrero.bluem.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    //region Constants

    private final static int REQUEST_ENABLE_BT = 1;

    //endregion

    ///region Fields

    private MainViewModel mainViewModel;
    private PairedDevicesList pairedDevicesAdapter;

    ///endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MainViewModel initiation
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setDataContext(mainViewModel);

        // PairedDevicesList initiation and configuration
        pairedDevicesAdapter = new PairedDevicesList(this, new ArrayList<BluetoothDevice>());
        binding.pairedDevicesListView.setAdapter(pairedDevicesAdapter);
        binding.pairedDevicesListView.setOnItemClickListener((parent, view, position, id) -> {
            this.mainViewModel.onPairedDevicesItemChosen(position);
        });

        // MainViewModel event handlers configuration
        mainViewModel.getActivityScreen().observe(this, activityString -> {
                switch (activityString) {
                    case "BluetoothReq":
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        this.showEnableBtDialog(enableBtIntent);
                        break;
                    case "MouseScreen":
                        break;
                    default:
                        break;
                }
            });
        mainViewModel.getPairedDevices().observe(this, pairedDevicesList -> {
            this.pairedDevicesAdapter.clear();
            this.pairedDevicesAdapter.addAll(pairedDevicesList);
        });
    }

    ///region BluetoothDialog

    public void showEnableBtDialog(Intent enableBtIntent) {
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode != RESULT_OK)
                    finish();
                else
                    mainViewModel.bluetoothLoadPairedDevices();
                break;
            default:
                throw new RuntimeException("Unknown requestCode!");
        }
    }

    ///endregion

}
