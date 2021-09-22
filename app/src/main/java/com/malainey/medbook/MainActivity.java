package com.malainey.medbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.malainey.medbook.medication.MedicationIntentConstants;
import com.malainey.medbook.medication.MedicationItem;
import com.malainey.medbook.medication.MedicationStore;
import com.malainey.medbook.views.MainActivityItemContentProvider;
import com.malainey.medbook.views.TripleRowListItemAdapter;


/**
 * Main launcher activity, displays a list of medications
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Callback for handling medication edit result activity
     */
    private final ActivityResultLauncher<Intent> editActivityResultLauncher;
    /**
     * Medication registry store
     */
    private final MedicationStore store;

    /**
     * List view adapter
     */
    private TripleRowListItemAdapter adapter;

    /**
     * Constructor for the main activity
     */
    public MainActivity() {
        this.store = MedicationStore.getInstance();
        this.editActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent intent = result.getData();
                if (result.getResultCode() == Activity.RESULT_OK && intent != null) {
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
        RecyclerView medicationListView = findViewById(R.id.medicationListItemsView);

        // Create the content provider and attach it to this activity's lifecycle
        MainActivityItemContentProvider contentProvider =
                new MainActivityItemContentProvider();
        contentProvider.setAction((Integer position) -> {
            Intent intent = new Intent(this, MedicationEditActivity.class);
            intent.putExtra(MedicationIntentConstants.EDIT_MEDICATION_ITEM, store.get(position));
            intent.putExtra(MedicationIntentConstants.EDIT_MEDICATION_POSITION, position);
            editActivityResultLauncher.launch(intent);
        });
        contentProvider.setStore(this.store);
        contentProvider.setResources(getResources());
        getLifecycle().addObserver(contentProvider);

        // Create adapter and give it to the RecyclerView
        adapter = new TripleRowListItemAdapter(contentProvider);
        adapter.showCheckboxes(true);
        medicationListView.setAdapter(adapter);
        medicationListView.setLayoutManager(new LinearLayoutManager(this));

        store.addListener(this::onMedicationStoreChangeListener);
    }

    /**
     * Callback for the add new item button
     *
     * Launches the medication edit activity with no additional data
     * @param view view that calls the callback (unused)
     */
    public void onAddNewMedButtonClick(View view) {
        Intent intent = new Intent(this, MedicationEditActivity.class);
        intent.putExtra(MedicationIntentConstants.EDIT_MEDICATION_POSITION, -1);
        editActivityResultLauncher.launch(intent);
    }

    /**
     * Callback for the remove medications button
     *
     * Removes selected items from store
     * @param view view that calls the callback (unused)
     */
    public void onDeleteMedClick(View view) {
        int[] items = adapter.getSelectedItems();
        if (items.length > 0) {
            for (int i = items.length - 1; i >= 0; i--) {
                store.remove(items[i]);
            }
        } else {
            Toast.makeText(this, getString(R.string.select_items_first),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback for listening to changes with the medication store's data
     * @param event store event that was fired
     * @param position position of changed or new data
     */
    public void onMedicationStoreChangeListener(MedicationStore.MedicationStoreEvent event, int position) {
        if (event.equals(MedicationStore.MedicationStoreEvent.ITEM_CHANGED)) {
            adapter.notifyItemChanged(position);
            adapter.notifyItemChanged(this.store.getItemCount());
        } else if (event.equals(MedicationStore.MedicationStoreEvent.ITEM_INSERTED)) {
            adapter.notifyItemInserted(position);
            adapter.notifyItemChanged(this.store.getItemCount());
        } else if (event.equals(MedicationStore.MedicationStoreEvent.ITEM_REMOVED)) {
            adapter.removeAt(position);
            adapter.notifyItemChanged(this.store.getItemCount());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        store.removeListener(this::onMedicationStoreChangeListener);
        adapter = null;
    }
}