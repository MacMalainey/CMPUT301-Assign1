package com.malainey.medbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.malainey.medbook.medication.MedicationDoseUnit;
import com.malainey.medbook.medication.MedicationIntentConstants;
import com.malainey.medbook.medication.MedicationItem;

import java.util.Calendar;
import java.util.Date;

public class MedicationEditActivity extends AppCompatActivity {

    /**
     * Position of item editing, to be passed back on activity end
     */
    private int position;

    /**
     * Text Edit View for editing medication name
     */
    private TextView editMedicationNameView;
    /**
     * Text Edit View for editing dose frequency
     */
    private TextView editDoseFrequencyView;
    /**
     * Text Edit View for editing dose amount
     */
    private TextView editDoseAmountView;
    /**
     * Calendar view for editing date started
     */
    private CalendarView editDateStartedView;

    /**
     * Radio group for selecting unit type
     */
    private RadioGroup editUnitTypeView;

    /**
     * Callback for handling submit button events
     * @param view button that ran callback, unused
     */
    public void onSubmit(View view) {
        // Get the input from each view
        final String name = editMedicationNameView.getText().toString().trim();
        final String frequencyInput = editDoseFrequencyView.getText().toString();
        final String dosageInput = editDoseAmountView.getText().toString();
        final Date dateStarted = new Date(editDateStartedView.getDate());
        final int unitId = editUnitTypeView.getCheckedRadioButtonId();
        int frequency = 0;
        int dosage = 0;

        // Verify the data is correct
        boolean hasErrors = false;
        if (name.length() <= 0) {
            editMedicationNameView.setError("Required");
            hasErrors = true;
        } else if (name.length() > MedicationItem.MAX_NAME_LENGTH) {
            editMedicationNameView.setError("Must be under 40 characters");
            hasErrors = true;
        }
        // Input is forced to be positive, so we just have to check if it is empty
        if (frequencyInput.isEmpty()) {
            editDoseFrequencyView.setError("Required");
            hasErrors = true;
        } else {
            frequency = Integer.parseInt(frequencyInput);
            if (frequency <= 0) {
                editDoseFrequencyView.setError("Must be positive");
                hasErrors = true;
            }
        }
        // Input is forced to be positive, so we just have to check if it is empty
        if (dosageInput.isEmpty()) {
            editDoseAmountView.setError("Required");
            hasErrors = true;
        } else {
            dosage = Integer.parseInt(dosageInput);
            if (dosage <= 0) {
                editDoseAmountView.setError("Must be positive");
                hasErrors = true;
            }
        }


        // We can always assume one is set because we make sure a value is initialized
        MedicationDoseUnit units = MedicationDoseUnit.MG;
        if (unitId == R.id.radioButtonUnitMcg)
            units = MedicationDoseUnit.MCG;
        else if (unitId == R.id.radioButtonUnitDrops)
            units = MedicationDoseUnit.DROP;

        // Return with a result if there are no issues
        if (!hasErrors) {
            final MedicationItem result = new MedicationItem(name, dateStarted, dosage, units, frequency);
            Intent intent = new Intent();
            intent.putExtra(MedicationIntentConstants.EDIT_MEDICATION_POSITION, position);
            intent.putExtra(MedicationIntentConstants.EDIT_MEDICATION_ITEM, result);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_edit);

        // Get data that was passed in
        Intent intent = getIntent();
        MedicationItem medicationItem = (MedicationItem) intent.getSerializableExtra(MedicationIntentConstants.EDIT_MEDICATION_ITEM);
        position = intent.getIntExtra(MedicationIntentConstants.EDIT_MEDICATION_POSITION, -1);

        // Get the views so we don't have to keep finding them
        editMedicationNameView = findViewById(R.id.editMedicationName);
        editDoseAmountView = findViewById(R.id.editDoseAmount);
        editDoseFrequencyView = findViewById(R.id.editDoseFreq);
        editDateStartedView = findViewById(R.id.editDateStarted);
        editUnitTypeView = findViewById(R.id.editUnitTypeRadioGroup);

        editDateStartedView.setOnDateChangeListener((calendarView, year, month, day) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            editDateStartedView.setDate(c.getTimeInMillis());
        });

        // Populate any initial data we have
        if (medicationItem != null) {
            editMedicationNameView.setText(medicationItem.name);
            editDoseAmountView.setText(String.valueOf(medicationItem.dosage));
            editDoseFrequencyView.setText(String.valueOf(medicationItem.dailyFreq));
            editDateStartedView.setDate(medicationItem.dateStarted.getTime());
            editUnitTypeView.check(getUnitButtonId(medicationItem.unit));
        }
    }

    /**
     * Helper function for getting the view id of the radio buttons depending on unit type
     * @param unit type of units
     * @return id of the view that represents the unit type
     */
    private int getUnitButtonId(MedicationDoseUnit unit) {
        switch(unit) {
            case MG:
                return R.id.radioButtonUnitMg;
            case MCG:
                return R.id.radioButtonUnitMcg;
            case DROP:
                return R.id.radioButtonUnitDrops;
            default:
                throw new IllegalArgumentException("No handler for MedicationDoseUnit type: " + unit.toString());
        }
    }
}