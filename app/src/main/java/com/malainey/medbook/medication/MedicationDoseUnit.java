package com.malainey.medbook.medication;

import com.malainey.medbook.R;

/**
 * Units for medication dosages
 */
public enum MedicationDoseUnit {
    MG(R.string.dose_unit_mg),
    MCG(R.string.dose_unit_mcg),
    DROP(R.string.dose_unit_drop);

    /**
     * Id for human readable string resource for unit type
     */
    public final int id;

    MedicationDoseUnit(int id) {
        this.id = id;
    }
}
