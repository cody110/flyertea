package com.ideal.flyerteacafes.utils.tools;

public class GNumber {

    private int intValue = 0;
    private double doubleValue = 0;

    public GNumber(Object src) {
        if (src != null) {
            if (src.getClass() == Integer.class) {
                intValue = (Integer) src;
                doubleValue = intValue;
            } else if (src.getClass() == Double.class) {
                doubleValue = (Double) src;
                intValue = (int) doubleValue;
            } else if (src.getClass() == String.class) {
                try {
                    doubleValue = Double.valueOf((String) src);
                    intValue = (int) doubleValue;
                } catch (NumberFormatException ex) {
                    try {
                        intValue = Integer.valueOf((String) src);
                        doubleValue = intValue;
                    } catch (NumberFormatException ex1) {

                    }
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.valueOf(doubleValue);
    }

    public int getInt() {
        return intValue;
    }

    public double getDouble() {
        return doubleValue;
    }
}
