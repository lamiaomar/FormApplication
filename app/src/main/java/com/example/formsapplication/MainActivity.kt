package com.example.formsapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.formflow.FormFlowFragment
import com.example.formflow.field.Field
import com.example.formflow.field.FieldType
import com.example.formflow.field.GoogleSheetURL
import com.example.formflow.listener.FormFlowListener
import com.example.formsapplication.custom.CustomRatingField

class MainActivity : AppCompatActivity(), FormFlowListener {

    private lateinit var buttonFormOne: Button
    private lateinit var buttonFormTwo: Button
    private lateinit var buttonFormThree: Button
    private lateinit var buttonContainer: View
    private lateinit var fragmentContainer: View
    private var customField: CustomRatingField? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonFormOne = findViewById(R.id.button_form_one)
        buttonFormTwo = findViewById(R.id.button_form_two)
        buttonFormThree = findViewById(R.id.button_form_three)
        buttonContainer = findViewById(R.id.button_container)
        fragmentContainer = findViewById(R.id.fragment_container)

        buttonFormOne.setOnClickListener {
            navigateToFormFlowFragment(getEventFormFields())
        }

        buttonFormTwo.setOnClickListener {
            navigateToFormFlowFragment(getHospitalFormFields())
        }

        buttonFormThree.setOnClickListener {
            navigateToFormFlowFragment(getGeneralFormFields())
        }
         customField = CustomRatingField(this)
    }

    private fun navigateToFormFlowFragment(formFields: ArrayList<Field>) {
        GoogleSheetURL.saveBaseUrl(
            this,
            "https://script.google.com/macros/s/AKfycbw6SZN2nhRilS_hvNOlZLxcMlSzrTcJYSXGIRYcUOQNKc1DAoHsfHcb-IQTD1DMh2Ao/"
        )

        val fragment = FormFlowFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("formFields", formFields)
                putString("submit_button_color", "#9f2b68")
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        buttonContainer.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
    }

    private fun getEventFormFields() = arrayListOf(
        Field(FieldType.EDITTEXT, "What is your name?", "#b26691", true),
        Field(FieldType.EDITTEXT, "What is your email address?", "#b26691", true),
        Field(
            FieldType.RADIO,
            "How did you hear about the event?",
            "#b26691",
            true,
            radioList = arrayListOf("Friends", "Social Media", "Website", "Other")
        ),
        Field(
            FieldType.DROPLIST,
            "How would you rate the event?",
            "#b26691",
            true,
            dropList = arrayListOf("Excellent", "Good", "Average", "Poor")
        ),
        Field(FieldType.EDITTEXT, "What did you like most about the event?", "#b26691", false),
        Field(FieldType.EDITTEXT, "What could be improved?", "#b26691", false),
        Field(
            FieldType.RADIO,
            "Would you attend future events?",
            "#b26691",
            true,
            radioList = arrayListOf("Yes", "No")
        ),
        Field(FieldType.CUSTOM, "Rate our service", "#808080", true, customField = customField)

    )

    private fun getHospitalFormFields() = arrayListOf(
        Field(FieldType.EDITTEXT, "What is your name?", "#e39e54", true),
        Field(FieldType.EDITTEXT, "What is your age?", "#e39e54", true),
        Field(
            FieldType.RADIO,
            "Was the hospital staff friendly?",
            "#e39e54",
            true,
            radioList = arrayListOf("Yes", "No", "Somewhat")
        ),
        Field(
            FieldType.DROPLIST,
            "How would you rate the cleanliness?",
            "#e39e54",
            true,
            dropList = arrayListOf("Excellent", "Good", "Average", "Poor")
        ),
        Field(FieldType.EDITTEXT, "What department did you visit?", "#e39e54", true),
        Field(
            FieldType.RADIO,
            "Was your waiting time reasonable?",
            "#e39e54",
            true,
            radioList = arrayListOf("Yes", "No")
        ),
        Field(FieldType.EDITTEXT, "Any additional comments?", "#e39e54", false),
        Field(
            FieldType.DROPLIST,
            "Would you recommend this hospital?",
            "#e39e54",
            true,
            dropList = arrayListOf("Definitely", "Probably", "Not sure", "No")
        )
    )

    private fun getGeneralFormFields() = arrayListOf(
        Field(FieldType.EDITTEXT, "What is your name?", "#d1d3b8", true),
        Field(
            FieldType.RADIO,
            "How satisfied are you with our service?",
            "#d1d3b8",
            true,
            radioList = arrayListOf(
                "Very Satisfied",
                "Satisfied",
                "Neutral",
                "Dissatisfied"
            )
        ),
        Field(
            FieldType.DROPLIST,
            "How would you rate the quality of our product?",
            "#d1d3b8",
            true,
            dropList = arrayListOf("Excellent", "Good", "Fair", "Poor")
        ),
        Field(FieldType.EDITTEXT, "What can we do to improve?", "#d1d3b8", false),
        Field(
            FieldType.RADIO,
            "Would you recommend us to others?",
            "#d1d3b8",
            true,
            radioList = arrayListOf("Yes", "No")
        )
    )

    override fun onFormClosed() {
        showButtonsAndHideFragment()
    }

    override fun onFormSubmitted() {
        showButtonsAndHideFragment()
    }

    private fun showButtonsAndHideFragment() {
        buttonContainer.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE
    }
}
