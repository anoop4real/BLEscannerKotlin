package com.example.anoopmohanan.bluetoothscanner

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Created by anoopmohanan on 25/03/18.
 */
class BTStateManager(activityContext:Context): BroadcastReceiver() {


    val activityContext = activityContext
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action


        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
            val state = intent?.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)

            when (state){

                BluetoothAdapter.STATE_OFF ->{
                    Toast.makeText(this.activityContext,"Bluetooth Off", 1)
                }
                BluetoothAdapter.STATE_TURNING_OFF ->{
                    Toast.makeText(this.activityContext,"Bluetooth Turning Off", 1)
                }
                BluetoothAdapter.STATE_ON ->{
                    Toast.makeText(this.activityContext,"Bluetooth On", 1)
                }
                BluetoothAdapter.STATE_TURNING_ON ->{
                    Toast.makeText(this.activityContext,"Bluetooth Turning On", 1)
                }

            }
        }

    }

}