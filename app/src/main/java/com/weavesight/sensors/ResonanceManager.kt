
package com.weavesight.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class ResonanceManager(context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    var resonanceValue: Float = 0f
        private set

    init {
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            val magnitude = Math.sqrt((event.values[0] * event.values[0] +
                                       event.values[1] * event.values[1] +
                                       event.values[2] * event.values[2]).toDouble()).toFloat()
            resonanceValue = magnitude
            Log.d("WeaveSight", "Magnetic field strength: $magnitude µT")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    fun unregister() {
        sensorManager.unregisterListener(this)
    }
}
