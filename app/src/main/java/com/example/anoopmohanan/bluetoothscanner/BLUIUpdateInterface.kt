package com.example.anoopmohanan.bluetoothscanner

import com.example.anoopmohanan.bluetoothscanner.Model.BLEDevice

/**
 * Created by anoopmohanan on 28/03/18.
 */
interface BLUIUpdateInterface {

    fun showMessage(message: String)
    fun initializeWith(data: ArrayList<BLEDevice>)
}