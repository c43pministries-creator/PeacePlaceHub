package org.thepeaceplace.hub

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // --- SECURITY GATEKEEPER CHECK ---
        if (SecurityGatekeeper.isVpnActive(this)) {
            showSecurityBlockDialog(
                getString(R.string.vpn_detected),
                getString(R.string.vpn_message)
            )
            return
        }
        
        if (SecurityGatekeeper.isProxyActive()) {
            showSecurityBlockDialog(
                getString(R.string.proxy_detected),
                getString(R.string.proxy_message)
            )
            return
        }
        // ---------------------------------
        
        // App is clean – continue loading
        val statusText = findViewById<TextView>(R.id.statusText)
        val checkButton = findViewById<Button>(R.id.checkButton)
        
        statusText.text = "Connection secure. Welcome to The Peace Place."
        
        checkButton.setOnClickListener {
            Toast.makeText(this, "Security check passed", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showSecurityBlockDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.exit_app) { _, _ ->
                finishAffinity()
            }
            .show()
    }
}
