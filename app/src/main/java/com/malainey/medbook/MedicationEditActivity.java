package com.malainey.medbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.malainey.medbook.medication.MedicationDoseUnit;
import com.malainey.medbook.medication.MedicationIntentConstants;
import com.malainey.medbook.medication.MedicationItem;

import java.util.Date;

public class MedicationEditActivity extends AppCompatActivity {

    private int position;

    public void onSubmit(View view) {
        Intent intent = new Intent();
        intent.putExtra(MedicationIntentConstants.EDIT_MEDICATION_POSITION, position);
        intent.putExtra(MedicationIntentConstants.EDIT_MEDICATION_ITEM, new MedicationItem("a new med", new Date(), 30, MedicationDoseUnit.MG, 1));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_edit);

        Intent intent = getIntent();
        MedicationItem medicationItem = (MedicationItem) intent.getSerializableExtra(MedicationIntentConstants.EDIT_MEDICATION_ITEM);
        position = intent.getIntExtra(MedicationIntentConstants.EDIT_MEDICATION_POSITION, -1);

        if (medicationItem != null) {
            ((TextView) findViewById(R.id.editMedicationName)).setText(medicationItem.name);
            ((TextView) findViewById(R.id.editDoseAmount)).setText(String.valueOf(medicationItem.dosage));
            ((TextView) findViewById(R.id.editDoseFreq)).setText(String.valueOf(medicationItem.dailyFreq));
            ((CalendarView) findViewById(R.id.editDateStarted)).setDate(medicationItem.dateStarted.getTime());
        }
    }

    private void verifyData() {
        // TODO: 
    }
}