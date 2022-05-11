package gui;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;


/**
 * @author Daniels Kanepe
 *
 */
public class Common {

    /** The date format. */
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    /** The Date-time format. */
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    /** Currency representation */
    public static final String CURRENCY_FORMAT = "###,###.00";
    public static final String CURRENCY = "DKK";

    /**
     * Convert string to date.
     *
     * @param dateString the date string
     * @return localDate the parsed data
     * 
     * @throws DateTimeParseException when there is an error parsing the date
     */
    public static LocalDate stringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * Convert string to date time.
     *
     * @param dateTimeString the date time string
     * @return localDateTime the parsed data
     *
     * @throws DateTimeParseException when there is an error parsing the date
     */
    public static LocalDateTime stringToDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    /**
     * Convert a LocalDate object to String
     *
     * @param date the date
     * @return the string
     */
    public static String dateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * Convert a LocalDateTime object to String
     *
     * @param dateTime the date time
     * @return the string
     */
    public static String datetimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }

    /**
     * Get the date format.
     *
     * @return the date format
     */
    public static String getDateFormat() {
        return DATE_FORMAT.toLowerCase();
    }
    
    /**
     * Get the date time format.
     *
     * @return the date time format
     */
    public static String getDateTimeFormat() {
        return DATETIME_FORMAT.toLowerCase();
    }

    /*
     * Recursively enable or disable all components in a Swing container.
     */
    public static void toggleUserComponents(Container container, boolean enabled) {
        for (Component c : container.getComponents()) {
            // if the component is user editable
            if (c instanceof JTextField || c instanceof JTextArea ||
                    c instanceof JComboBox || c instanceof JSpinner ||
                    c instanceof JRadioButton || c instanceof JCheckBox || c instanceof JPasswordField) {
                // disable/enable it
                c.setEnabled(enabled);
            }
            // if the component is a container
            if (c instanceof Container) {
                // recursively disable/enable all fields in it
                toggleUserComponents((Container) c, enabled);
            }
        }
    }

    /*
     * Represent currency as a string
     */
    public static String toCurrency(BigDecimal amount) {
        DecimalFormat df = new DecimalFormat(CURRENCY_FORMAT);
        df.setCurrency(java.util.Currency.getInstance(CURRENCY));
        return df.format(amount) + " " + CURRENCY;
    }

}