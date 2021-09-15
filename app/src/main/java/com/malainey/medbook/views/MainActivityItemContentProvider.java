package com.malainey.medbook.views;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.malainey.medbook.R;
import com.malainey.medbook.medication.MedicationStore;

import java.util.function.Consumer;

public class MainActivityItemContentProvider implements TripleRowListItemAdapter.ContentProvider, DefaultLifecycleObserver {
    /**
     * Store to get information from
     */
    private MedicationStore store;

    /**
     * Context to get string resources from
     */
    private Resources resources;

    /**
     * Callback to run when an item's action button gets pressed
     */
    private Consumer<Integer> onAction;

    /**
     *
     * @param callback
     */
    public void setAction(@NonNull Consumer<Integer> callback) {
        onAction = callback;
    }

    /**
     *
     * @param res
     */
    public void setResources(@NonNull Resources res) {
        this.resources = res;
    }

    public void setStore(@NonNull MedicationStore store) {
        this.store = store;
    }

    @Override
    public String getTitle(int pos) {
        if (pos < store.getItemCount())
            return store.get(pos).name;
        else
            return "Total doses:" + store.getDoseCount(); //TODO
    }

    @Override
    public String getSecondRow(int pos) {
        if (pos < store.getItemCount())
            return resources.getString(R.string.daily_dosage_tag, store.get(pos).dailyFreq, store.get(pos).dosage, resources.getString(store.get(pos).unit.id));
        else
            return null;
    }

    @Override
    public String getThirdRow(int pos) {
        if (pos < store.getItemCount())
            return resources.getString(R.string.date_started_tag, store.get(pos).dateStarted);
        else
            return null;
    }

    @Override
    public int getItemCount() {
        return store.getItemCount() + 1;
    }

    @Override
    public boolean isSelectable(int pos) {
        return pos < store.getItemCount();
    }

    @Override
    public Consumer<Integer> getAction(int pos) {
        return pos < store.getItemCount() ? activePosition -> onAction.accept(activePosition) : null;
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        this.resources = null;
        this.onAction = null;
        this.store = null;
    }
}
