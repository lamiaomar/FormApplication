package com.example.formsapplication.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.formflow.field.custome.CustomField
import com.example.formsapplication.R

class CheckboxField(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val question: String,
    private val color: String,
) : ConstraintLayout(context, attrs, defStyleAttr), CustomField {

    private var isChecked: Boolean = false

    override fun getView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_checkbox, this, true)
        val questionText = view.findViewById<TextView>(R.id.question_text)
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox)

        questionText.text = question
        questionText.setTextColor(Color.parseColor(color))
        checkBox.setTextColor(Color.parseColor(color))

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            this.isChecked = isChecked
        }

        return view
    }

    override fun getValue(): String {
        return if (isChecked) "Yes" else "No"
    }
}