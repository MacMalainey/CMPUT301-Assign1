package com.malainey.medbook.medication;

import com.malainey.medbook.util.ChangeEventEmitter;

import java.util.ArrayList;
import java.util.List;

public class MedicationStore extends ChangeEventEmitter<MedicationStore.MedicationStoreEvent, Integer> {

    // Easy way to keep all the medications loaded even if the main activity (for some WILD reason gets destroyed)
    private static final List<MedicationItem> medications = new ArrayList<>();

    public static MedicationStore getInstance() {
        return new MedicationStore();
    }

    private MedicationStore() {} // Empty Constructor

    public MedicationItem get(int index) {
        return medications.get(index);
    }

    public void replace(int index, MedicationItem item) {
        medications.set(index, item);
        fireEvent(MedicationStoreEvent.ITEM_CHANGED, index);
    }

    public void add(MedicationItem item) {
        medications.add(item);
        fireEvent(MedicationStoreEvent.ITEM_INSERTED, medications.size() - 1);
    }

    public static List<MedicationItem> getMedications() {
        return medications;
    }

    public enum MedicationStoreEvent {
        ITEM_INSERTED,
        ITEM_CHANGED
    }
}
