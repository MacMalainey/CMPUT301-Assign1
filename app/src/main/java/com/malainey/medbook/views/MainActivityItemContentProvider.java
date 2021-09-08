package com.malainey.medbook.views;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.malainey.medbook.R;
import com.malainey.medbook.medication.MedicationStore;

public class MainActivityItemContentProvider implements TripleRowListItemAdapter.ContentProvider, DefaultLifecycleObserver {
    /**
     * Store to get information from
     */
    private final MedicationStore store;

    /**
     * Context to get string resources from
     */
    private Context context;

    /**
     * Constructor for content provider
     * @param store supplier for item entries
     * @param context supplier for string resources
     *
     * <b>REGISTER THIS WITH THE USER'S LIFECYCLE TO AVOID MEMORY</b>
     */
    public MainActivityItemContentProvider(@NonNull MedicationStore store, @NonNull Context context) {
        this.context = context;
        this.store = store;
    }

    @Override
    public String getTitle(int pos) {
        return store.get(pos).name;
    }

    @Override
    public String getSecondRow(int pos) {
        return context.getString(R.string.daily_dosage_tag, store.get(pos).dailyFreq, store.get(pos).dosage, context.getString(store.get(pos).unit.id));
    }

    @Override
    public String getThirdRow(int pos) {
        return context.getString(R.string.date_started_tag, store.get(pos).dateStarted);
    }

    @Override
    public int getItemCount() {
        return store.getItemCount();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        this.context = null;
    }
}
