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
import com.example.formsapplication.custom.CheckboxField
import com.example.formsapplication.custom.CustomRatingField

class MainActivity : AppCompatActivity(), FormFlowListener {

    private lateinit var buttonFormOne: Button
    private lateinit var buttonFormTwo: Button
    private lateinit var buttonFormThree: Button
    private lateinit var buttonContainer: View
    private lateinit var fragmentContainer: View
    private var customField: CustomRatingField? = null

    private var buttonColor = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonFormOne = findViewById(R.id.button_form_one)
        buttonFormTwo = findViewById(R.id.button_form_two)
        buttonFormThree = findViewById(R.id.button_form_three)
        buttonContainer = findViewById(R.id.button_container)
        fragmentContainer = findViewById(R.id.fragment_container)


        buttonFormOne.setOnClickListener {
            buttonColor = "#b26691"
            navigateToFormFlowFragment(getEventFormFields())
        }

        buttonFormTwo.setOnClickListener {
            buttonColor = "#4E4F50"
            navigateToFormFlowFragment(getHospitalFormFields())
        }

        buttonFormThree.setOnClickListener {
            buttonColor = "#914955"
            navigateToFormFlowFragment(getGeneralFormFields())
        }
        customField = CustomRatingField(this)
    }

    private fun navigateToFormFlowFragment(formFields: ArrayList<Field>) {
        GoogleSheetURL.saveBaseUrl(
            this,
            "https://script.google.com/macros/s/AKfycbyLYUhKferHsbZerz_qgaUl2d_0x8IZgpQ28ZZcROgUa1jG-ICGbO6az8MBH6In5s4VnA/"
        )

        val fragment = FormFlowFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("formFields", formFields)
                putString("submit_button_color", buttonColor)
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
        Field(FieldType.EDITTEXT, "What did you like most about the event?", "#b26691", false),
        Field(FieldType.EDITTEXT, "What could be improved?", "#b26691", false),
        Field(
            FieldType.DROPLIST,
            "How would you rate the event?",
            "#b26691",
            true,
            dropList = arrayListOf("Excellent", "Good", "Average", "Poor")
        ),
        Field(
            FieldType.RADIO,
            "How did you hear about the event?",
            "#b26691",
            true,
            radioList = arrayListOf("Friends", "Social Media", "Website", "Other")
        ),
        Field(
            FieldType.RADIO,
            "Would you attend future events?",
            "#b26691",
            true,
            radioList = arrayListOf("Yes", "No")
        ),
        Field(FieldType.CUSTOM, "Rate our service", "#b26691", true, customField = customField)
    )

    private fun getHospitalFormFields() = arrayListOf(
        Field(FieldType.EDITTEXT, "What is your name?", "#4E4F50", true),
        Field(FieldType.EDITTEXT, "What is your age?", "#4E4F50", true),
        Field(FieldType.EDITTEXT, "What department did you visit?", "#4E4F50", true),
        Field(
            FieldType.RADIO,
            "Was the hospital staff friendly?",
            "#4E4F50",
            true,
            radioList = arrayListOf("Yes", "No", "Somewhat")
        ),
        Field(
            FieldType.RADIO,
            "Was your waiting time reasonable?",
            "#4E4F50",
            true,
            radioList = arrayListOf("Yes", "No")
        ),
        Field(
            FieldType.DROPLIST,
            "How would you rate the cleanliness?",
            "#4E4F50",
            true,
            dropList = arrayListOf("Excellent", "Good", "Average", "Poor")
        ),
        Field(
            FieldType.DROPLIST,
            "Would you recommend this hospital?",
            "#4E4F50",
            true,
            dropList = arrayListOf("Definitely", "Probably", "Not sure", "No")
        ),
        Field(
            FieldType.CUSTOM,
            "Would you recommend this hospital?",
            "#4E4F50",
            true,
            customField = CheckboxField(
                this,
                question = "Would you like to participate in future surveys?",
                color = "#4E4F50"
            )

        )
    )

    private fun getGeneralFormFields() = arrayListOf(
        Field(FieldType.EDITTEXT, "What is your name?", "#914955", true),
        Field(
            FieldType.RADIO,
            "How satisfied are you with our service?",
            "#914955",
            true,
            radioList = arrayListOf(
                "Satisfied",
                "Neutral",
                "Dissatisfied"
            )
        ),
        Field(
            FieldType.DROPLIST,
            "How would you rate the quality of our product?",
            "#914955",
            true,
            dropList = arrayListOf("Excellent", "Good", "Fair", "Poor")
        ),
        Field(FieldType.EDITTEXT, "What can we do to improve?", "#914955", false),
        Field(
            FieldType.RADIO,
            "Would you recommend us to others?",
            "#914955",
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
