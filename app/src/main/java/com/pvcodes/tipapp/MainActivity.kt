package com.pvcodes.tipapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //My Code Here
        val calc_btn = findViewById<Button>(R.id.btn_calculate)
        calc_btn.setOnClickListener {
            hideKeyboard(view = it)
            display_Tip()
        }
        findViewById<RadioButton>(R.id.ok).isChecked = true
        val footer = findViewById<TextView>(R.id.footer)
        footer.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private fun display_Tip() {
        var costStr: String = findViewById<EditText>(R.id.cost_string).text.toString()
        var cost: Double
        if (costStr.isEmpty()) {
            Toast.makeText(this, "Please enter the cost", Toast.LENGTH_SHORT).show()
        } else {
            val roundingOff: Boolean = findViewById<Switch>(R.id.roundoff_switch).isChecked
            cost = costStr.toDouble()
            val radiogroup_checkedBtn_id: Int =
                findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId
            val tipPercent = when (radiogroup_checkedBtn_id) {
                R.id.amazing -> .2
                R.id.good -> .18
                else -> .15
            }
            cost *= tipPercent
            costStr = String.format("%.3f", cost)
            cost = costStr.toDouble()
            if (roundingOff) {
                cost = round(cost)
            }
            findViewById<TextView>(R.id.tip_display_String).text = "$cost\$"
//            Toast.makeText(this, cost.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}

fun hideKeyboard(view: View) {
    // Retrieving the token if the view is hosted by the fragment.
    var windowToken: IBinder? = view.windowToken

    // Retrieving the token if the view is hosted by the activity.
    if (windowToken == null) {
        if (view.context is Activity) {
            val activity = view.context as Activity
            if (activity.window != null && activity.window.decorView != null) {
                windowToken = activity.window.decorView.windowToken
            }
        }
    }

    // Hide if shown before.
    val inputMethodManager = view
        .context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
