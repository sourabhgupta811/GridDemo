package co.markai.interviewapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.markai.interviewapplication.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)
        view.next.setOnClickListener {
            val row = view.rowEntry.text.toString().toIntOrNull()
            val col = view.colEntry.text.toString().toIntOrNull()
            if (row!= null && col != null){
                if(row > 1 && col > 1) {
                    if(row < 200 && col < 200) {
                        val intent = SecondActivity.newIntent(this@MainActivity, row, col)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "current implementation do not support more than 200x200 grid. OOM risks!", Toast. LENGTH_SHORT).show()
                    }
                } else{
                    Toast.makeText(this@MainActivity, "Value should be more than 1", Toast. LENGTH_SHORT).show()
                }
            }
        }
    }
}