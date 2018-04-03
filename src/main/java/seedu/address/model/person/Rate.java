package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

//@@author sherlynng
/**
 * Represents a Person's rating in the address book.
 * Guarantees: immutable;
 */
public class Rate {

    /* Regex notation
    ^                   # Start of string
    (?:                 # Either match...
    5(?:\.0)?           # 5.0 (or 5)
    |                   # or
    [0-4](?:\.[0-9])?   # 0.0-4.9 (or 1-4)
    |                   # or
    0?\.[1-9]           # 0.1-0.9 (or .1-.9)
    )                   # End of alternation
    $                   # End of string
     */
    public static final String RATE_VALIDATION_REGEX = "^(?:5(?:\\.0)?|[0-4](?:\\.[0-9])?|0?\\.[0-9])$";
    public static final String RATE_VALIDATION_REGEX_ABSOLUTE = "^(?:5(?:\\.0)?|[0-4](?:\\.[0-9])?|0?\\.[0-9])" + "-";
    public static final String MESSAGE_RATE_CONSTRAINTS =
            "Rate must be a number between 0 and 5 (inclusive) with at most 1 decimal place";

    private double value;
    private int count;
    private boolean isAbosulte;

    /**
     * Constructs an {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rate (double rating, boolean isAbsolute) {
        requireNonNull(rating);
        checkArgument(isValidRate(Double.toString(rating)), MESSAGE_RATE_CONSTRAINTS);

        this.value = rating;
        this.isAbosulte = isAbsolute;
    }

    /**
     * Calculates the accumulated value of a person's rating
     * @param oldRate
     * @param newRate
     * @return {@code Rate} that contains updated value and count
     */
    public static Rate acummulatedValue (Rate oldRate, Rate newRate) {
        double value;
        double newValue;

        value = oldRate.getValue() * oldRate.getCount();
        newValue = (value + newRate.getValue()) / (oldRate.getCount() + 1);
        newValue = Math.floor(newValue * 10) / 10;

        newRate = new Rate(newValue, true);
        newRate.setCount(oldRate.getCount() + 1);

        return newRate;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidRate(String test) {
        return test.equals("") || test.matches(RATE_VALIDATION_REGEX) || test.matches(RATE_VALIDATION_REGEX_ABSOLUTE);
    }

    public double getValue() {
        return this.value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getIsAbosulte() {
        return isAbosulte;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rate // instanceof handles nulls
                && this.value == ((Rate) other).value
                && this.count == ((Rate) other).count); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}