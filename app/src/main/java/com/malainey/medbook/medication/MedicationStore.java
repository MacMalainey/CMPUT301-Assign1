package com.malainey.medbook.medication;

import com.malainey.medbook.util.ChangeEventEmitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Store to keep registered medication items loaded even when activities using them get destroyed.
 * All data is shared as this produces a singleton
 */
public class MedicationStore extends ChangeEventEmitter<MedicationStore.MedicationStoreEvent, Integer> {

    /**
     * List for registered medications
     */
    private static final List<MedicationItem> medications = new ArrayList<>();
    /**
     * Internal reference that is shared with whomever wants it
     */
    private static final MedicationStore store = new MedicationStore();

    /**
     * Get the instance of the store
     * @return store instance
     */
    public static MedicationStore getInstance() {
        return store;
    }

    /**
     * Private constructor to stop other users from making their own
     */
    private MedicationStore() {} // Empty Constructor

    /**
     * Return the item at the given index
     * @param index location of registered item
     * @return registered item or null if no item registered at index
     */
    public MedicationItem get(int index) {
        return medications.get(index);
    }

    /**
     * Count of the number items registered in the store
     * @return number of registered items
     */
    public int getItemCount() {
        return medications.size();
    }

    /**
     * Replaces the registered item at an index for a different item
     * @param index position of item to replace
     * @param item medication to register
     */
    public void replace(int index, MedicationItem item) {
        medications.set(index, item);
        fireEvent(MedicationStoreEvent.ITEM_CHANGED, index);
    }

    /**
     * Register a new medication
     * @param item medication to register
     */
    public void add(MedicationItem item) {
        medications.add(item);
        fireEvent(MedicationStoreEvent.ITEM_INSERTED, medications.size() - 1);
    }

    /**
     * Events fired by this class
     */
    public enum MedicationStoreEvent {
        /**
         * Event fired when a new item has been registered
         */
        ITEM_INSERTED,
        /**
         * Event fired when an item has been changed
         */
        ITEM_CHANGED
    }
}
