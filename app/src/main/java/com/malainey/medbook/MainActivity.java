package com.malainey.medbook;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.malainey.medbook.medication.MedicationDoseUnit;
import com.malainey.medbook.medication.MedicationIntentConstants;
import com.malainey.medbook.medication.MedicationItem;
import com.malainey.medbook.medication.MedicationItemContentProvider;
import com.malainey.medbook.medication.MedicationStore;
import com.malainey.medbook.views.TripleRowListItemAdapter;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> mStartForResult;
    private final MedicationStore store;

    public MainActivity() {
        this.store = MedicationStore.getInstance();
        this.mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    MedicationItem mItem = (MedicationItem) intent.getSerializableExtra(MedicationIntentConstants.EDIT_MEDICATION_ITEM);
                    int position = intent.getIntExtra(MedicationIntentConstants.EDIT_MEDICATION_POSITION, -1);
                    if (position > -1) {
                        store.replace(position, mItem);
                    } else {
                        store.add(mItem);
                    }
                }
            });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.itemsView);

        store.add(new MedicationItem("Medication Alpha", new Date(), 1, MedicationDoseUnit.DROP, 1));
        store.add(new MedicationItem("Medication Beta", new Date(), 1, MedicationDoseUnit.DROP, 1));
        store.add(new MedicationItem("Medication Delta", new Date(), 1, MedicationDoseUnit.DROP, 1));
        store.add(new MedicationItem("Medication Gamma", new Date(), 1, MedicationDoseUnit.DROP, 1));

        // Create the content provider
        MedicationItemContentProvider contentProvider =
                new MedicationItemContentProvider(
                        store,
                        this
                );
        // Create adapter passing in the sample user data
        TripleRowListItemAdapter adapter = new TripleRowListItemAdapter(contentProvider, (Integer position) -> {
            Intent intent = new Intent(this, MedicationEditActivity.class);
            intent.putExtra(MedicationIntentConstants.EDIT_MEDICATION_ITEM, store.get(position));
            intent.putExtra(MedicationIntentConstants.EDIT_MEDICATION_POSITION, position);
            mStartForResult.launch(intent);
        });
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

        store.addListener((event, position) -> {
            if (event.equals(MedicationStore.MedicationStoreEvent.ITEM_CHANGED)) {
                adapter.notifyItemChanged(position);
            } else if (event.equals(MedicationStore.MedicationStoreEvent.ITEM_INSERTED)) {
                adapter.notifyItemInserted(position);
            }
        });
    }

    public void onAddNewMedButtonClick(View view) {
        Intent intent = new Intent(this, MedicationEditActivity.class);
        intent.putExtra(MedicationIntentConstants.EDIT_MEDICATION_POSITION, -1);
        mStartForResult.launch(intent);
    }
}