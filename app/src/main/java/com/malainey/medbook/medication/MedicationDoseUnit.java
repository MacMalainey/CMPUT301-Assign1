package com.malainey.medbook.medication;

import com.malainey.medbook.R;

/**
 * Units for medication dosages
 */
public enum MedicationDoseUnit {
    MG(R.string.dose_unit_mg, R.string.dose_label_unit_mg),
    MCG(R.string.dose_unit_mcg, R.string.dose_label_unit_mcg),
    DROP(R.string.dose_unit_drop, R.string.dose_label_unit_drop);

    /**
     * Id for human readable string resource for unit type
     */
    public final int RSuffixId;
    public final int RLabelId;

    /**
     * Constructor for enum values
     * @param RSuffixId resource id for displaying measurement unit suffix
     * @param RLabelId resource id for displaying unit label
     */
    MedicationDoseUnit(int RSuffixId, int RLabelId) {
        this.RSuffixId = RSuffixId;
        this.RLabelId = RLabelId;
    }
}
