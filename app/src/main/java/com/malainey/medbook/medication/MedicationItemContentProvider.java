package com.malainey.medbook.medication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.malainey.medbook.R;
import com.malainey.medbook.views.TripleRowListItemAdapter;

import java.util.List;

public class MedicationItemContentProvider implements TripleRowListItemAdapter.ContentProvider, DefaultLifecycleObserver {
    private final MedicationStore store;
    private Context context;

    public MedicationItemContentProvider(@NonNull MedicationStore store, @NonNull Context context) {
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
