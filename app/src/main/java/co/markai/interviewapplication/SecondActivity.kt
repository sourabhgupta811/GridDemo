package co.markai.interviewapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import co.markai.interviewapplication.databinding.ActivitySecondBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SecondActivity: AppCompatActivity() {
    companion object{
        fun newIntent(context: Context, rowEntry: Int, colEntry: Int): Intent {
            return Intent(context, SecondActivity::class.java).apply {
                putExtra("row_entry", rowEntry)
                putExtra("col_entry", colEntry)
            }
        }
    }
    // TODO: replace with factory, won't hold in config changes
    val viewModel by viewModels<SecondActivityViewModel>()
    lateinit var rootLayoutBinding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rowEntry = intent.getIntExtra("row_entry", 0)
        val colEntry = intent.getIntExtra("col_entry", 0)
        rootLayoutBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(rootLayoutBinding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.gridFlow().collectLatest {
                    it?.let {
                        renderUI(it)
                    }
                }
            }
        }
        if (viewModel.gridFlow().value == null) {
            viewModel.setData(rowEntry, colEntry)
        }
        setListener()
    }

    fun setListener(){
        rootLayoutBinding.generateRandomPos.setOnClickListener {
            val data = viewModel.gridFlow().value
            data?.let {
                val points = viewModel.calculateRandomPos(it)
                viewModel.updateData(points)
            }
        }
    }

    private fun renderUI(it: SecondActivityViewModel.GridData) {
        val points = it.points
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }// orientation
        for(i in 0 until it.rowCount) {
            val row = LinearLayout(this)
            for (y in 0 until it.colCount) {
                val view = ImageView(this)
                if(points != null){
                    if(i == points.first.y && y == points.first.x){
                        view.setImageResource(R.drawable.ic_first)
                    } else if(i == points.second.y && y == points.second.x){
                        view.setImageResource(R.drawable.ic_second)
                    } else {
                        view.setImageResource(R.drawable.ic_empty)
                    }
                }
                row.addView(view)
            }
            layout.addView(row,0)
        }

        // check if already exists and remove
        // add with a id here, doing with index now as layout is fixed
        if (rootLayoutBinding.root.getChildAt(0) is LinearLayout){
            rootLayoutBinding.root.removeViewAt(0)
        }
        rootLayoutBinding.root.addView(layout,0)
    }
}