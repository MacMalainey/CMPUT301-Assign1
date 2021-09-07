package com.malainey.medbook.medication;

import java.io.Serializable;
import java.util.Date;

public class MedicationItem implements Serializable {
    public final Date dateStarted;
    public final String name;
    public final int dosage;
    public final MedicationDoseUnit unit;
    public final int dailyFreq;

    public MedicationItem(String name, Date dateStarted, int dosage, MedicationDoseUnit unit, int dailyFreq) {
        // TODO: Verify types
        this.dateStarted = dateStarted;
        this.name = name;
        this.dosage = dosage;
        this.unit = unit;
        this.dailyFreq = dailyFreq;
    }
}
