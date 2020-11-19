package org.gradproj.heartrate.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*
import org.gradproj.heartrate.R

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        var name = "";
        var phoneNum = "";

        btn_ok.setOnClickListener {
            name = editText_Name.text.toString()
            phoneNum = editText_PhoneNm.toString()

            if(!name.equals("") && (!phoneNum.equals("")||phoneNum.length==11)){
                val intentCamera = Intent(this, CameraActivity::class.java)
                this.startActivity(intentCamera)
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
        }
    }
}