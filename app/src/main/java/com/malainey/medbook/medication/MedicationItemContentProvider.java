package com.malainey.medbook.medication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.malainey.medbook.R;
import com.malainey.medbook.views.TripleRowListItemAdapter;

import java.util.List;

public class MedicationItemContentProvider implements TripleRowListItemAdapter.ContentProvider, DefaultLifecycleObserver {
    private final List<MedicationItem> items;
    private Context context;

    public MedicationItemContentProvider(@NonNull List<MedicationItem> items, @NonNull Context context) {
        this.context = context;
        this.items = items;
    }

    @Override
    public String getTitle(int pos) {
        return items.get(pos).name;
    }

    @Override
    public String getSecondRow(int pos) {
        return context.getString(R.string.daily_dosage_tag, items.get(pos).dailyFreq, items.get(pos).dosage, context.getString(items.get(pos).unit.id));
    }

    @Override
    public String getThirdRow(int pos) {
        return context.getString(R.string.date_started_tag, items.get(pos).dateStarted);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        this.context = null;
    }
}
