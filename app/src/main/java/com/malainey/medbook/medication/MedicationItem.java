package com.malainey.medbook.medication;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a medication data entry
 */
public class MedicationItem implements Serializable {

    /**
     * Medication name character limit
     */
    public static final int MAX_NAME_LENGTH = 40;

    /**
     * The start date for taking the medication
     */
    public final Date dateStarted;

    /**
     * The name of the medication
     */
    public final String name;

    /**
     * The amount taken per dose of the medication
     */
    public final int dosage;

    /**
     * The units for the dosage
     */
    public final MedicationDoseUnit unit;

    /**
     * The amount of times to take the medication daily
     */
    public final int dailyFreq;

    /**
     * @param name the name of the medication (character limit set by MAX_NAME_LIMIT)
     * @param dateStarted the date the medication was first taken
     * @param dosage the amount taken per dose (as a positive decimal)
     * @param unit the dose amount's unit specifier
     * @param dailyFreq the number of doses taken per day (as a positive integer)
     */
    public MedicationItem(String name, Date dateStarted, int dosage, MedicationDoseUnit unit, int dailyFreq) {
        // Perform input verification
        if (name.length() > MAX_NAME_LENGTH)
            throw new IllegalArgumentException("Argument \"name\" is over the 40 character limit");
        if (dailyFreq <= 0)
            throw new IllegalArgumentException("Argument \"dailyFreq\" is non-positive");
        if (dosage <= 0)
            throw new IllegalArgumentException("Argument \"dosage\" is non-positive");

        // Set values
        this.dateStarted = dateStarted;
        this.name = name;
        this.dosage = dosage;
        this.unit = unit;
        this.dailyFreq = dailyFreq;
    }
}
