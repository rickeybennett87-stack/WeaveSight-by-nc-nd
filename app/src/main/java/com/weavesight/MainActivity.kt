
package com.weavesight

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.weavesight.sensors.ResonanceManager
import com.weavesight.ui.OverlayView

class MainActivity : AppCompatActivity() {

    private lateinit var overlayView: OverlayView
    private lateinit var resonanceManager: ResonanceManager
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        overlayView = findViewById(R.id.overlay_view)
        resonanceManager = ResonanceManager(this)
        overlayView.resonanceManager = resonanceManager

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BODY_SENSORS), 1)
        }

        startUpdateLoop()
    }

    private fun startUpdateLoop() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                overlayView.updateOverlay()
                handler.postDelayed(this, 100)
            }
        }, 100)
    }
}
