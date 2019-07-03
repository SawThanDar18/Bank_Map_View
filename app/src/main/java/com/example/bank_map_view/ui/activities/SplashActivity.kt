package com.example.bank_map_view.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.bank_map_view.R
import kotlinx.android.synthetic.main.splash_screen.*

class SplashActivity : AppCompatActivity() {

    private var progressBarStatus = 0
    private var dummy : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_screen)

        Thread(Runnable {
                try {
                    dummy = dummy + 25
                    Thread.sleep(2000)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }catch (e : InterruptedException){
                    e.printStackTrace()
                }

                progressBarStatus = dummy
                progressBar.progress = progressBarStatus

        }).start()
    }
}