/*
 * Brandon Lynch
 * This program lets the user interact with three different calculators using a text-based console.
 * Each calculator has multiple functions, including mathematical operations and unit/type conversions.
 * The user can also run "test mode" on each calculator which tests all its functions and prints the outputs.
 * Calculators include:
 *    - Binary calculator
 *    - Hexadecimal calculator
 *    - Bandwidth calculator
 */

import java.util.*;

public class Calculator {

    // Keeps track of data, time, and bandwidth units used by the calculators and their conversion tables.
    private static final Map<String, Long> SIZE_UNIT_CONVERSIONS = new HashMap<>();
    private static final Map<String, Long> BANDWIDTH_UNIT_CONVERSIONS = new HashMap<>();
    private static final Map<String, Double> TIME_UNIT_CONVERSIONS = new HashMap<>();
    private static final String[] SIZE_UNITS = {"b", "kb", "mb", "gb", "tb", "B", "KB", "MB", "GB", "TB"};
    private static final String[] BIG_SIZE_UNITS = {"B", "KB", "MB", "GB", "TB"};
    private static final String[] BANDWIDTH_UNITS = {"bit/s", "Kbit/s", "Mbit/s", "Gbit/s", "Tbit/s"};
    private static final String[] TIME_UNITS = {"seconds", "minutes", "hours", "days", "months"};

    // Binary calculator functions

    /**
     * Adds two binary values and returns and prints the result
     * @param a - String representing a binary number
     * @param b - String representing a binary number
     * @return String - Binary sum
     */
    private static String addBinary(String a, String b) {
        return operation("+", a, b, "binary");
    }

    /**
     * Subtracts the first binary value by the second and returns and prints the result
     * @param a - String representing a binary number
     * @param b - String representing a binary number
     * @return String - Binary difference
     */
    private static String subtractBinary(String a, String b) {
        return operation("-", a, b, "binary");
    }

    /**
     * Multiplies two binary values and returns and prints the result
     * @param a - String representing a binary number
     * @param b - String representing a binary number
     * @return String - Binary product
     */
    private static String multiplyBinary(String a, String b) {
        return operation("*", a, b, "binary");
    }

    /**
     * Divides the first binary number by the second and returns and prints the result
     * @param a - String representing a binary number
     * @param b - String representing a binary number
     * @return String - Binary quotient
     */
    private static String divideBinary(String a, String b) {
        return operation("/", a, b, "binary");
    }

    /**
     * Converts the given integer to a binary value and returns the result
     * @param n - A non-negative integer
     * @return String representation of a binary value
     */
    private static String getBinaryFromDecimal(int n) {
        if (n == 0) {
            return "0";
        }
        String result = "";
        while (n != 0) {
            result = (n % 2) + result;
            n /= 2;
        }
        return result;
    }

    /**
     * Converts the given binary number to an integer and returns the result
     * @param binary - String representation of a binary value
     * @return integer value of the given binary number
     */
    private static int getDecimalFromBinary(String binary) {
        validateBinaryInput(binary);
        int sum = 0;
        for (int i = 0; i < binary.length(); i++) {
            int curr = Integer.parseInt(String.valueOf(binary.charAt(i)));
            sum += (curr * Math.pow(2, (binary.length() - 1 - i)));
        }
        return sum;
    }

    /**
     * Determines if the given String is valid binary
     * @param binary - String to test
     * @return boolean - True if valid binary, false if not valid
     */
    private static boolean validateBinaryInput(String binary) {
        if (binary == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        for (char c : binary.toCharArray()) {
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }

    // Hexadecimal calculator functions

    /**
     * Adds two hexadecimal values and prints the result
     * @param a - String representing first hexadecimal value
     * @param b - String representing second hexadecimal value
     * @return String - Hexadecimal sum
     */
    private static String addHex(String a, String b) {
        return operation("+", a, b, "hexadecimal");
    }

    /**
     * Subtracts the first hexadecimal value by the second and prints the result
     * @param a - String representing first hexadecimal value
     * @param b - String representing second hexadecimal value
     * @return String - Hexadecimal difference
     */
    private static String subtractHex(String a, String b) {
        return operation("-", a, b, "hexadecimal");
    }

    /**
     * Multiplies two hexadecimal values and prints the result
     * @param a - String representing first hexadecimal value
     * @param b - String representing second hexadecimal value
     * @return String - Hexadecimal product
     */
    private static String multiplyHex(String a, String b) {
        return operation("*", a, b, "hexadecimal");
    }

    /**
     * Divides the first hexadecimal value by the second and prints the result
     * @param a - String representing first hexadecimal value
     * @param b - String representing second hexadecimal value
     * @return String - Hexadecimal quotient
     */
    private static String divideHex(String a, String b) {
        return operation("/", a, b, "hexadecimal");
    }

    /**
     * Determines if the given String is a valid hexadecimal value.
     * Throws exception if hex is null
     * @param hex - String representation of hexadecimal value to test
     * @return boolean - True if valid hexadecimal, false if not
     */
    private static boolean validateHexadecimalInput(String hex) {
        Set<Character> hexValues = new HashSet<>(
                Arrays.asList('-', '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F')
        );
        if (hex == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        for (char c : hex.toCharArray()) {
            if (!hexValues.contains(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts the given positive integer to a hexadecimal value and returns the result
     * @param n - Positive integer to convert
     * @return String representation of hexadecimal value
     */
    private static String getHexFromDecimal(int n) {
        if (n == 0) {
            return "0";
        }
        boolean isNeg = n < 0;
        n = Math.abs(n);
        char[] hexValues = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        String result = "";
        while (n != 0) {
            result = hexValues[n % 16] + result;
            n /= 16;
        }
        if (isNeg) {
            return "-" + result;
        }
        return result;
    }

    /**
     * Converts the given hexadecimal value to a decimal and returns the result
     * @param hex - String representation of hexadecimal value
     * @return int - decimal value
     */
    private static int getDecimalFromHex(String hex) {
        if (hex == null) {
            throw new IllegalArgumentException("Cannot be null");
        }

        boolean isNeg = false;
        if (hex.charAt(0) == '-') {
            isNeg = true;
            hex = hex.substring(1);
        }

        List<Character> hexValues = new ArrayList<>(
                Arrays.asList('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F')
        );
        int sum = 0;
        for (int i = 0; i < hex.length(); i++) {
            int curr = hexValues.indexOf(hex.charAt(i));
            sum += (curr * Math.pow(16, (hex.length() - 1 - i)));
        }
        if (isNeg) {
            sum *= -1;
        }
        return sum;
    }

    // Bandwidth calculator functions

    /**
     * Converts the given data value and unit and converts it to each of the units:
     * "b", "kb", "mb", "gb", "tb", "B", "KB", "MB", "GB", and "TB"
     * and prints the results and returns it as a list.
     * @param num - Value of data size
     * @param unit - Unit of the data size value
     * @return List of Strings - Values of num converted to all units
     */
    private static List<String> convertSizeUnits(double num, String unit) {
        // Display as a whole number if possible
        if (num == Math.floor(num)) {
            System.out.println((long) num + " " + unit + " is equivalent to any of the following:");
        } else {
            System.out.println(num + " " + unit + " is equivalent to any of the following:");
        }
        List<String> results = new ArrayList<>();
        // Use custom array to choose ordering of units from smallest to largest
        String dataWithUnit;
        for (String u : SIZE_UNITS) {
            if (!u.equals(unit)) {
                double converted = convertSizeUnit(unit, u, num);
                // Display as a whole number if possible
                if (converted == Math.floor(converted)) {
                    dataWithUnit = (long) converted + " " + u;
                } else {
                    dataWithUnit = converted + " " + u;
                }
                results.add(dataWithUnit);
                System.out.println(dataWithUnit);
            }
        }
        return results;
    }

    /**
     * Converts a single data size value to the given unit and returns the new value.
     * Available units:
     * "b", "kb", "mb", "gb", "tb", "B", "KB", "MB", "GB", and "TB"
     * @param fromUnit - Original unit of data size value
     * @param toUnit - Unit to convert to
     * @param num - Value of data size
     * @return double - value of num converted to the given unit
     */
    private static double convertSizeUnit(String fromUnit, String toUnit, double num) {
        long from = SIZE_UNIT_CONVERSIONS.get(fromUnit);
        long to = SIZE_UNIT_CONVERSIONS.get(toUnit);

        return num * ((double) to / from);
    }

    /**
     * Converts a single bandwidth value to the given unit and returns the new value.
     * Available units:
     * "bit/s", "Kbit/s", "Mbit/s", "Gbit/s", and "Tbit/s"
     * @param fromUnit - Original unit of the bandwidth value
     * @param toUnit - Bandwidth unit to convert to
     * @param num - Value of the bandwidth
     * @return double - value of num converted into the given unit
     */
    public static double convertBandwidthUnit(String fromUnit, String toUnit, double num) {
        long from = BANDWIDTH_UNIT_CONVERSIONS.get(fromUnit);
        long to = BANDWIDTH_UNIT_CONVERSIONS.get(toUnit);

        return num * ((double) to / from);
    }

    /**
     * Converts a single time value to the given unit and returns the new value.
     * Available units:
     * "seconds", "minutes", "hours", "days", and "months"
     * @param fromUnit - Original unit of the time
     * @param toUnit - Unit to convert to
     * @param time - value of the time to be converted
     * @return double - value of the given time converted into the given unit
     */
    public static double convertTimeUnit(String fromUnit, String toUnit, double time) {
        double from = TIME_UNIT_CONVERSIONS.get(fromUnit);
        double to = TIME_UNIT_CONVERSIONS.get(toUnit);

        return time * (to / from);
    }

    /**
     * Calculates the upload/download time for the given file size and bandwidth and returns the result.
     * size units: "b", "kb", "mb", "gb", "tb", "B", "KB", "MB", "GB", and "TB"
     * bandwidth units: "bit/s", "Kbit/s", "Mbit/s", "Gbit/s", and "Tbit/s"
     * @param size - Value of the file size
     * @param sizeUnit - Unit of the file size
     * @param bandwidth - Value of the bandwidth
     * @param bandwidthUnit - Unit of the bandwidth
     * @return String - load time
     */
    private static String calculateLoadTime(double size, String sizeUnit, double bandwidth, String bandwidthUnit) {
        double sizeConverted = convertSizeUnit(sizeUnit, "MB", size);
        double bandwidthConverted = convertBandwidthUnit(bandwidthUnit, "Mbit/s", bandwidth);
        double loadTime = sizeConverted / convertSizeUnit("mb", "MB", bandwidthConverted);

        String timeString = "";
        if ((int) loadTime >= 60) {
            timeString = (loadTime % 60) + " seconds " + timeString;
            loadTime /= 60; // go to minutes
            timeString = (int) (loadTime % 60) + " minutes " + timeString;
            loadTime /= 60; // go to hours

            if ((int) loadTime > 0) {
                timeString = (int) loadTime + " hours " + timeString;
                loadTime /= 24; // go to days

                if ((int) loadTime > 0) {
                    timeString = (int) loadTime + " days " + timeString;
                }
            }
        }
        return timeString.strip();
    }

    /**
     * Calculates the bandwidth needed for a website given the average page views per time unit, the average page size,
     * and the redundancy factor (a positive number). Prints out the results.
     * size units: "b", "kb", "mb", "gb", "tb", "B", "KB", "MB", "GB", and "TB"
     * time units: "seconds", "minutes", "hours", "days", and "months"
     * @param views - Average number of views of the page
     * @param timeUnit - Time unit for the average views
     * @param pageSize - Value of the page data size
     * @param sizeUnit - Unit fo the page data size
     * @param redundancy - The redundancy factor
     * @return Map of the results with keys:
     * "bandwidthSeconds", "bandwidthMonths", "redundancy", "bandwidthSecondsRedundancy", "bandwidthMonthsRedundancy"
     */
    private static Map<String, Double> calculateWebsiteBandwidth(double views, String timeUnit, double pageSize, String sizeUnit, double redundancy) {
        double months = convertTimeUnit(timeUnit, "months", 1);
        double viewsPerMonth = views / months;
        double actualBandwidthMonths = convertSizeUnit(sizeUnit, "GB", pageSize) * viewsPerMonth;
        double actualBandwidthSeconds = convertSizeUnit("GB", "mb", actualBandwidthMonths);
        double secondsInMonth = convertTimeUnit("months", "seconds", 1);
        actualBandwidthSeconds /= secondsInMonth;

        System.out.println("Actual bandwidth needed is " + actualBandwidthSeconds + " Mbit/s or " + actualBandwidthMonths + " GB per month.");
        if (redundancy != 1) {
            System.out.println("With redundancy factor " + redundancy + ", the bandwidth needed is " + actualBandwidthSeconds * redundancy + " Mbit/s or " + actualBandwidthMonths * redundancy + " GB per month.");
        }

        Map<String, Double> results = new HashMap<>();
        results.put("bandwidthSeconds", actualBandwidthSeconds);
        results.put("bandwidthMonths", actualBandwidthMonths);
        results.put("redundancy", redundancy);
        results.put("bandwidthSecondsRedundancy", actualBandwidthSeconds * redundancy);
        results.put("bandwidthMonthsRedundancy", actualBandwidthMonths * redundancy);
        return results;
    }

    /**
     * Converts the given monthly usage to the given bandwidth unit and returns the result
     * size units: "b", "kb", "mb", "gb", "tb", "B", "KB", "MB", "GB", and "TB"
     * bandwidth units: "bit/s", "Kbit/s", "Mbit/s", "Gbit/s", and "Tbit/s"
     * @param usage - Value of monthly data usage
     * @param sizeUnit - Data unit of monthly usage
     * @param bandwidthUnit - Bandwidth unit to convert to
     * @return double - Bandwidth value
     */
    private static double convertUsageToBandwidth(double usage, String sizeUnit, String bandwidthUnit) {
        String bandwidthUnitAsSize = getBandwidthUnitAsSizeUnit(bandwidthUnit);
        usage /= convertTimeUnit("months", "seconds", 1);
        return convertSizeUnit(sizeUnit, bandwidthUnitAsSize, usage);
    }

    /**
     * Converts the given bandwidth to monthly data usage of the given data unit and returns the result
     * size units: "b", "kb", "mb", "gb", "tb", "B", "KB", "MB", "GB", and "TB"
     * bandwidth units: "bit/s", "Kbit/s", "Mbit/s", "Gbit/s", and "Tbit/s"
     * @param bandwidth - Value of the bandwidth
     * @param bandwidthUnit - Unit of the bandwidth
     * @param sizeUnit - Data size unit to convert to
     * @return double - Bandwidth value
     */
    private static double convertBandwidthToUsage(double bandwidth, String bandwidthUnit, String sizeUnit) {
        String bandwidthUnitAsSize = getBandwidthUnitAsSizeUnit(bandwidthUnit);
        bandwidth /= convertTimeUnit("seconds", "months", 1);
        return convertSizeUnit(bandwidthUnitAsSize, sizeUnit, bandwidth);
    }

    /**
     * gets the data unit portion from the given bandwidth unit and returns it
     * bandwidth units: "bit/s", "Kbit/s", "Mbit/s", "Gbit/s", and "Tbit/s"
     * @param bandwidthUnit - Unit of the bandwidth
     * @return String - data unit portion of the bandwidth unit
     */
    private static String getBandwidthUnitAsSizeUnit(String bandwidthUnit) {
        if (bandwidthUnit.equals("bit/s")) {
            return "b";
        } else {
            return bandwidthUnit.substring(0, 2).toLowerCase();
        }
    }

    /**
     * Initializes the unit conversion tables for data size, bandwidth, and time unit conversions
     */
    private  static void initializeUnitConversions() {
        initializeSizeUnitConversions();
        initializeBandwidthUnitConversions();
        initializeTimeUnitConversions();
    }

    /**
     * Initializes the unit conversion tables for data size unit conversions
     */
    private static void initializeSizeUnitConversions() {
        SIZE_UNIT_CONVERSIONS.put("TB", (long) 1);
        SIZE_UNIT_CONVERSIONS.put("GB", (long) 1000);
        SIZE_UNIT_CONVERSIONS.put("MB", 1000 * SIZE_UNIT_CONVERSIONS.get("GB"));
        SIZE_UNIT_CONVERSIONS.put("KB", 1000 * SIZE_UNIT_CONVERSIONS.get("MB"));
        SIZE_UNIT_CONVERSIONS.put("B", 1000 * SIZE_UNIT_CONVERSIONS.get("KB"));
        SIZE_UNIT_CONVERSIONS.put("tb", 8 * SIZE_UNIT_CONVERSIONS.get("TB"));
        SIZE_UNIT_CONVERSIONS.put("gb", 8 * SIZE_UNIT_CONVERSIONS.get("GB"));
        SIZE_UNIT_CONVERSIONS.put("mb", 8 * SIZE_UNIT_CONVERSIONS.get("MB"));
        SIZE_UNIT_CONVERSIONS.put("kb", 8 * SIZE_UNIT_CONVERSIONS.get("KB"));
        SIZE_UNIT_CONVERSIONS.put("b", 8 * SIZE_UNIT_CONVERSIONS.get("B"));
    }

    /**
     * Initializes the unit conversion tables for bandwidth unit conversions
     */
    private static void initializeBandwidthUnitConversions() {
        BANDWIDTH_UNIT_CONVERSIONS.put("Tbit/s", (long) 1);
        BANDWIDTH_UNIT_CONVERSIONS.put("Gbit/s", (long) 1000);
        BANDWIDTH_UNIT_CONVERSIONS.put("Mbit/s", 1000 * BANDWIDTH_UNIT_CONVERSIONS.get("Gbit/s"));
        BANDWIDTH_UNIT_CONVERSIONS.put("Kbit/s", 1000 * BANDWIDTH_UNIT_CONVERSIONS.get("Mbit/s"));
        BANDWIDTH_UNIT_CONVERSIONS.put("bit/s", 1000 * BANDWIDTH_UNIT_CONVERSIONS.get("Kbit/s"));
    }

    /**
     * Initializes the unit conversion tables for time unit conversions
     */
    private static void initializeTimeUnitConversions() {
        // seconds, minutes, hours, days
        TIME_UNIT_CONVERSIONS.put("months", 1.0);
        TIME_UNIT_CONVERSIONS.put("days", (365.25 / 12.0));
        TIME_UNIT_CONVERSIONS.put("hours", 24 * TIME_UNIT_CONVERSIONS.get("days"));
        TIME_UNIT_CONVERSIONS.put("minutes", 60 * TIME_UNIT_CONVERSIONS.get("hours"));
        TIME_UNIT_CONVERSIONS.put("seconds", 60 * TIME_UNIT_CONVERSIONS.get("minutes"));
    }

    // Test methods

    /**
     * Asserts that the actual Object is equal to the expected Object and prints a message of the result
     * @param actual - Actual object
     * @param expected - Expected object
     */
    private static void assertEquals(Object actual, Object expected) {
        if (actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
            System.out.println("Expected value: " + expected);
            System.out.println("But received actual value: " + actual);
        }
    }

    /**
     * Tests all functions in the binary calculator and prints the results.
     * Expected values taken from https://www.calculator.net/binary-calculator.html
     */
    private static void testBinaryOperations() {
        System.out.println("Binary operations tests:");
        System.out.println("-----Add function-----");
        assertEquals(addBinary("10101010", "11001100"), "101110110");
        System.out.println();

        System.out.println("-----Subtract function-----");
        assertEquals(subtractBinary("10101010", "11001100"), "-100010");
        System.out.println();

        System.out.println("-----Multiply function-----");
        assertEquals(multiplyBinary("10101010", "11001100"), "1000011101111000");
        System.out.println();

        System.out.println("-----Divide function-----");
        // Answer is "0 Remainder: 10101010"
        assertEquals(divideBinary("10101010", "11001100"), "0");
        System.out.println();

        System.out.println("-----Binary to decimal function-----");
        int actualDecimal = getDecimalFromBinary("10101010");
        System.out.println("Binary value: 10101010");
        System.out.println("Decimal Value: " + actualDecimal);
        assertEquals(actualDecimal, 170);
        System.out.println();

        System.out.println("-----Decimal to binary function-----");
        String actualBinary = getBinaryFromDecimal(170);
        System.out.println("Decimal value: 170");
        System.out.println("Binary Value: " + actualBinary);
        assertEquals(actualBinary, "10101010");
        System.out.println();
    }

    /**
     * Tests all functions in the hexadecimal calculator and prints the results.
     * Expected values taken from https://www.calculator.net/hex-calculator.html
     */
    private static void testHexOperations() {
        System.out.println("Hexadecimal operations tests:");
        System.out.println("-----Add function-----");
        assertEquals(addHex("8AB", "B78"), "1423");
        System.out.println();

        System.out.println("-----Subtract function-----");
        assertEquals(subtractHex("8AB", "B78"), "-2CD");
        System.out.println();

        System.out.println("-----Multiply function-----");
        assertEquals(multiplyHex("8AB", "B78"), "636928");
        System.out.println();

        System.out.println("-----Divide function-----");
        assertEquals(divideHex("DAC", "23"), "64");
        System.out.println();

        System.out.println("-----Hexadecimal to decimal function-----");
        int actualDecimal = getDecimalFromHex("DAD");
        System.out.println("Hexadecimal value: DAD");
        System.out.println("Decimal Value: " + actualDecimal);
        assertEquals(actualDecimal, 3501);
        System.out.println();

        System.out.println("-----Decimal to hexadecimal function-----");
        String actualHex = "" + getHexFromDecimal(170);
        System.out.println("Decimal Value: " + actualDecimal);
        System.out.println("Hexadecimal value: AA");
        assertEquals(actualHex, "AA");
        System.out.println();
    }

    /**
     * Tests all functions in the bandwidth calculator and prints the results.
     * Expected values are taken from https://www.calculator.net/bandwidth-calculator.html
     */
    private static void testBandwidthOperations() {
        System.out.println("Bandwidth operations tests:");
        System.out.println("-----Convert unit function-----");
        List<String> unitConversions = convertSizeUnits(500, "MB");
        assertEquals(
                unitConversions,
                Arrays.asList(
                        "4000000000 b",
                        "4000000 kb",
                        "4000 mb",
                        "4 gb",
                        "0.004 tb",
                        "500000000 B",
                        "500000 KB",
                        "0.5 GB",
                        "5.0E-4 TB"
                )
        );
        System.out.println();

        System.out.println("-----Calculate download/upload time function-----");
        String loadTime = calculateLoadTime(12567, "MB",  3.2, "Mbit/s");
        System.out.println("Download or upload time needed is: ~" + loadTime);
        assertEquals(loadTime, "8 hours 43 minutes 37.5 seconds");
        System.out.println();

        System.out.println("-----Calculate website bandwidth function-----");
        Map<String, Double> actualWebBandwidth = calculateWebsiteBandwidth(5000, "days", 500, "KB", 2);
        Map<String, Double> expectedWebBandwidth = new HashMap<>();
        // Note: extended repeating expected value from online calc
        expectedWebBandwidth.put("bandwidthSeconds", 0.23148148148148148);
        expectedWebBandwidth.put("bandwidthMonths", 76.09375);
        expectedWebBandwidth.put("redundancy", 2.0);
        // Note: extended repeating expected value from online calc
        expectedWebBandwidth.put("bandwidthSecondsRedundancy", 0.46296296296296297);
        expectedWebBandwidth.put("bandwidthMonthsRedundancy", 152.1875);
        assertEquals(actualWebBandwidth, expectedWebBandwidth);
        System.out.println();

        System.out.println("-----Calculate monthly usage to bandwidth function-----");
        double actualBandwidth = convertUsageToBandwidth(1000.0, "GB", "Mbit/s");
        System.out.println("Monthly usage: 1000 GB");
        System.out.println("Bandwidth: " + actualBandwidth);
        assertEquals(actualBandwidth, 3.042056430146779); // rounded expected value from online calc
        System.out.println();

        System.out.println("-----Calculate bandwidth to monthly usage function-----");
        double actualUsage = convertBandwidthToUsage(1000, "Mbit/s", "GB");
        System.out.println("Bandwidth: 1000 Mbit/s");
        System.out.println("Monthly usage: " + actualUsage);
        assertEquals(actualUsage, 328725.0);
        System.out.println();
    }

    // Helper methods

    /**
     * Performs a binary or hexadecimal mathematical operation and returns and prints the results.
     * @param operator - String representation of math operation: "+", "-", "*", or "/"
     * @param a - String representing first hexadecimal or binary value
     * @param b - String representing second hexadecimal or binary value
     * @param type - String representing type of the values: either "binary" or "hexadecimal"
     * @return String - result of the operation
     */
    private static String operation(String operator, String a, String b, String type) {
        if ((operator == null) || (a == null) || (b == null) || (type == null)) {
            throw new IllegalArgumentException("Cannot be null");
        }
        if (!operator.equals("+") && !operator.equals("-") && !operator.equals("*") && !operator.equals("/")) {
            throw new IllegalArgumentException("Not a valid operation");
        }
        if (!type.equals("binary") && !type.equals("hexadecimal")) {
            throw new IllegalArgumentException("Not a valid value type");
        }

        // Declare terms
        int aDec;
        int bDec;
        int rDec;
        int decResult;
        String result;
        String r;
        // Different conversion cases
        if (type.equals("binary")) {
            // Validate parameters
            validateBinaryInput(a);
            validateBinaryInput(b);

            // Convert to decimal, do decimal operation, convert back
            aDec = getDecimalFromBinary(a);
            bDec = getDecimalFromBinary(b);
            rDec = aDec % bDec;
            decResult = switch (operator) {
                case "+" -> aDec + bDec;
                case "-" -> aDec - bDec;
                case "*" -> aDec * bDec;
                case "/" -> aDec / bDec;
                default -> -1;
            };
            result = getBinaryFromDecimal(Math.abs(decResult));
            r = getBinaryFromDecimal(rDec);
        } else /*type is hex*/{
            validateHexadecimalInput(a);
            validateHexadecimalInput(b);

            // Convert to decimal, do decimal operation, convert back
            aDec = getDecimalFromHex(a);
            bDec = getDecimalFromHex(b);
            rDec = aDec % bDec;
            decResult = switch (operator) {
                case "+" -> aDec + bDec;
                case "-" -> aDec - bDec;
                case "*" -> aDec * bDec;
                case "/" -> aDec / bDec;
                default -> -1;
            };
            result = getHexFromDecimal(decResult);
            r = getHexFromDecimal(rDec);
        }
        if (type.equals("binary") && operator.equals("-") && (aDec < bDec)) {
            result = "-" + result;
        }
        // Print results
        type = Character.toUpperCase(type.charAt(0)) + type.substring(1);
        System.out.println(type + " value:");
        System.out.print(a + " " + operator + " " + b + " = " + result);
        if (operator.equals("/")) {
            System.out.println(" Remainder: " + r);
        } else {
            System.out.println();
        }
        System.out.println();
        System.out.println("Decimal value:");
        System.out.print(aDec + " " + operator + " " + bDec + " = " + decResult);
        if (operator.equals("/")) {
            System.out.println(" Remainder: " + rDec);
        } else {
            System.out.println();
        }
        System.out.println();
        return result;
    }

    /**
     * Searches for the given String in the given array and returns if it in the array
     * @param array - Array to search through
     * @param find - String to look for
     * @return boolean - True if it is in the array, false if it is not
     */
    private static boolean inStringArray(String[] array, String find) {
        for (String s : array) {
            if (find.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Runs the binary calculator until the user quits it.
     * Functions include:
     *    1. Add
     *    2. Subtract
     *    3. Multiply
     *    4. Divide
     *    5. Convert binary value to decimal value
     *    6. Convert decimal value to binary value
     *    7. Run tests
     * @param input - A Scanner for user input.
     * @return boolean - True if calculator is still running, false if it has been quit.
     */
    private static boolean runBinaryCalculator(Scanner input) {
        System.out.println("Select a binary operation (enter \"q\" to quit):");
        System.out.println("   1. Add");
        System.out.println("   2. Subtract");
        System.out.println("   3. Multiply");
        System.out.println("   4. Divide");
        System.out.println("   5. Convert binary value to decimal value");
        System.out.println("   6. Convert decimal value to binary value");
        System.out.println("   7. Run tests");
        System.out.print("Selection: ");

        String operationChoice = input.next();
        while(!(operationChoice.equals("q")
                || operationChoice.equals("1")
                || operationChoice.equals("2")
                || operationChoice.equals("3")
                || operationChoice.equals("4")
                || operationChoice.equals("5")
                || operationChoice.equals("6")
                || operationChoice.equals("7"))) {
            System.out.print("Please select a valid option: ");
            operationChoice = input.next();
        }

        if (operationChoice.equals("q")) {
            return false;
        }

        //mathOperation(input, operationChoice, "binary");
        /* option 7 */
        switch (operationChoice) {
            case "1", "2", "3", "4" -> {
                System.out.print("First value: ");
                String firstValue = input.next();
                while (!validateBinaryInput(firstValue)) {
                    System.out.println("Must contain only 1's and 0's.");
                    System.out.print("First value: ");
                    firstValue = input.next();
                }
                System.out.print("Second value: ");
                String secondValue = input.next();
                while (!validateBinaryInput(secondValue)) {
                    System.out.println("Must contain only 1's and 0's.");
                    System.out.print("Second value: ");
                    secondValue = input.next();
                }
                String op = switch (operationChoice) {
                    case "1" -> "+";
                    case "2" -> "-";
                    case "3" -> "*";
                    default -> "/";
                };
                System.out.println("Result:");
                operation(op, firstValue, secondValue, "binary");
            }
            case "5" -> {
                System.out.print("Enter binary number: ");
                String binary = input.next();
                while (!validateBinaryInput(binary)) {
                    System.out.println("Must contain only 1's and 0's.");
                    System.out.print("Enter binary number: ");
                    binary = input.next();
                }
                System.out.println("Decimal value: " + getDecimalFromBinary(binary));
            }
            case "6" -> {
                boolean isNumeric = false;
                while (!isNumeric) {
                    try {
                        System.out.print("Enter a decimal value: ");
                        int num = input.nextInt();
                        isNumeric = true;
                        while ((num != Math.floor(num)) || num < 0) {
                            System.out.println("Must be a non-negative integer");
                            System.out.print("Enter a decimal value: ");
                            num = input.nextInt();
                        }
                        System.out.println("Binary value: " + getBinaryFromDecimal(num));
                    } catch (Exception InputMismatchException) {
                        System.out.println("Must be numeric.");
                        // Eat the invalid input
                        input.next();
                        isNumeric = false;
                    }
                }
            }
            default -> testBinaryOperations();
        }
        return true;
    }

    /**
     * Runs the hexadecimal calculator until the user quits it.
     * Functions include:
     *    1. Add
     *    2. Subtract
     *    3. Multiply
     *    4. Divide
     *    5. Convert hexadecimal value to decimal value
     *    6. Convert decimal value to hexadecimal value
     *    7. Run tests
     * @param input - A Scanner for user input.
     * @return boolean - True if calculator is still running, false if it has been quit.
     */
    private static boolean runHexCalculator(Scanner input) {
        System.out.println("Select a hexadecimal operation (enter \"q\" to quit):");
        System.out.println("   1. Add");
        System.out.println("   2. Subtract");
        System.out.println("   3. Multiply");
        System.out.println("   4. Divide");
        System.out.println("   5. Convert hexadecimal value to decimal value");
        System.out.println("   6. Convert decimal value to hexadecimal value");
        System.out.println("   7. Run tests");
        System.out.print("Selection: ");

        String operationChoice = input.next();
        while(!(operationChoice.equals("q")
                || operationChoice.equals("1")
                || operationChoice.equals("2")
                || operationChoice.equals("3")
                || operationChoice.equals("4")
                || operationChoice.equals("5")
                || operationChoice.equals("6")
                || operationChoice.equals("7"))) {
            System.out.print("Please select a valid option: ");
            operationChoice = input.next();
        }

        if (operationChoice.equals("q")) {
            return false;
        }

        //mathOperation(input, operationChoice, "hexadecimal");
        /* option 7 */
        switch (operationChoice) {
            case "1", "2", "3", "4" -> {
                System.out.print("First value: ");
                String firstValue = input.next();
                while (!validateHexadecimalInput(firstValue)) {
                    System.out.println("Must contain only values 0-9 and A-F.");
                    System.out.print("First value: ");
                    firstValue = input.next();
                }
                System.out.print("Second value: ");
                String secondValue = input.next();
                while (!validateHexadecimalInput(secondValue)) {
                    System.out.println("Must contain only values 0-9 and A-F.");
                    System.out.print("Second value: ");
                    secondValue = input.next();
                }
                String op = switch (operationChoice) {
                    case "1" -> "+";
                    case "2" -> "-";
                    case "3" -> "*";
                    default -> "/";
                };
                System.out.println("Result:");
                operation(op, firstValue, secondValue, "hexadecimal");
            }
            case "5" -> {
                System.out.print("Enter a hexadecimal value: ");
                String hex = input.next();
                while (!validateHexadecimalInput(hex)) {
                    System.out.println("Must contain only values 0-9 and A-F.");
                    System.out.print("Enter hexadecimal value: ");
                    hex = input.next();
                }
                System.out.println("Decimal value: " + getDecimalFromHex(hex));
            }
            case "6" -> {
                boolean isNumeric = false;
                while (!isNumeric) {
                    try {
                        System.out.print("Enter a decimal value: ");
                        double num = input.nextDouble();
                        isNumeric = true;
                        while (num != Math.floor(num)) {
                            System.out.println("Must be an integer");
                            System.out.print("Enter a decimal value: ");
                            num = input.nextDouble();
                        }
                        System.out.println("Hexadecimal value: " + getHexFromDecimal((int) Math.ceil(num)));
                    } catch (Exception InputMismatchException) {
                        System.out.println("Must be numeric.");
                        // Eat the invalid input
                        input.next();
                        isNumeric = false;
                    }
                }
            }
            default -> testHexOperations();
        }
        return true;
    }

    /**
     * Runs the bandwidth calculator until the user quits it.
     * Functions include:
     *    1. Convert data unit
     *    2. Calculate download/upload time
     *    3. Calculate website bandwidth
     *    4. Convert monthly usage to bandwidth
     *    5. Convert bandwidth to monthly usage
     *    6. Run tests
     * @param input - A Scanner for user input.
     * @return boolean - True if calculator is still running, false if it has been quit.
     */
    private static boolean runBandwidthCalculator(Scanner input) {
        System.out.println("Select a bandwidth operation (enter \"q\" to quit):");
        System.out.println("   1. Convert data unit");
        System.out.println("   2. Calculate download/upload time");
        System.out.println("   3. Calculate website bandwidth");
        System.out.println("   4. Convert monthly usage to bandwidth");
        System.out.println("   5. Convert bandwidth to monthly usage");
        System.out.println("   6. Run tests");
        System.out.print("Selection: ");

        String operationChoice = input.next();
        while(!(operationChoice.equals("q")
                || operationChoice.equals("1")
                || operationChoice.equals("2")
                || operationChoice.equals("3")
                || operationChoice.equals("4")
                || operationChoice.equals("5")
                || operationChoice.equals("6"))) {
            System.out.print("Please select a valid option: ");
            operationChoice = input.next();
        }

        if (operationChoice.equals("q")) {
            return false;
        }

        // Convert data unit
        // Calculate download/upload time
        // Calculate website bandwidth
        // Convert monthly usage to bandwidth
        // Convert monthly bandwidth to usage
        /* option 7 */
        switch (operationChoice) {
            case "1" -> bandwidthFuncConvertDataUnit(input);
            case "2" -> bandwidthFuncCalcLoadTime(input);
            case "3" -> bandwidthFuncCalcWebsiteBandwidth(input);
            case "4" -> bandwidthFuncConvertUsageToBandwidth(input);
            case "5" -> bandwidthFuncConvertBandwidthToUsage(input);
            default -> testBandwidthOperations();
        }
        return true;
    }

    /**
     * Performs the "convert data unit" function of the bandwidth calculator
     * @param input - Scanner for user input
     */
    private static void bandwidthFuncConvertDataUnit(Scanner input) {
        System.out.println("Data units:");
        printUnitOptions(SIZE_UNITS);
        System.out.print("Enter a unit to convert from (e.g. \"GB\"): ");
        String unit = input.next();
        while (!inStringArray(SIZE_UNITS, unit)) {
            System.out.println("Please enter one of the given units.");
            System.out.print("Enter a unit to convert from (e.g. \"GB\"): ");
            unit = input.next();
        }
        double num = 0;
        boolean isNumeric = false;
        while (!isNumeric) {
            try {
                System.out.print("Enter a value: ");
                num = input.nextDouble();
                isNumeric = true;
                while (num < 0) {
                    System.out.println("Must be a positive number");
                    System.out.print("Enter a value: ");
                    num = input.nextDouble();
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Must be numeric.");
                // Eat the invalid input
                input.next();
                isNumeric = false;
            }
        }
        convertSizeUnits(num, unit);
        System.out.println();
    }

    /**
     * Performs the "Calculate download/upload time" function of the bandwidth calculator
     * @param input - Scanner for user input
     */
    private static void bandwidthFuncCalcLoadTime(Scanner input) {
        System.out.println("File size units:");
        printUnitOptions(BIG_SIZE_UNITS);
        System.out.print("Enter a unit: ");
        String sizeUnit = input.next();
        while (!inStringArray(BIG_SIZE_UNITS, sizeUnit)) {
            System.out.println("Please enter one of the given units.");
            System.out.print("Enter a unit: ");
            sizeUnit = input.next();
        }
        double sizeValue = 0;
        boolean isNumeric = false;
        while (!isNumeric) {
            try {
                System.out.print("Enter a file size: ");
                sizeValue = input.nextDouble();
                isNumeric = true;
                while (sizeValue < 0) {
                    System.out.println("Must be a positive number");
                    System.out.print("Enter a value: ");
                    sizeValue = input.nextDouble();
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Must be numeric.");
                // Eat the invalid input
                input.next();
                isNumeric = false;
            }
        }

        System.out.println("Bandwidth units:");
        printUnitOptions(BANDWIDTH_UNITS);
        System.out.print("Enter a unit: ");
        String bandwidthUnit = input.next();
        while (!inStringArray(BANDWIDTH_UNITS, bandwidthUnit)) {
            System.out.println("Please enter one of the given units.");
            System.out.print("Enter a unit: ");
            bandwidthUnit = input.next();
        }

        double bandwidthValue = 0;
        isNumeric = false;
        while (!isNumeric) {
            try {
                System.out.print("Enter bandwidth value: ");
                bandwidthValue = input.nextDouble();
                isNumeric = true;
                while (bandwidthValue < 0) {
                    System.out.println("Must be a positive number");
                    System.out.print("Enter a value: ");
                    bandwidthValue = input.nextDouble();
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Must be numeric.");
                // Eat the invalid input
                input.next();
                isNumeric = false;
            }
        }
        String loadTime = calculateLoadTime(sizeValue, sizeUnit, bandwidthValue, bandwidthUnit);
        System.out.println("Download or upload time needed is: ~" + loadTime);
        System.out.println();
    }

    /**
     * Performs the "Calculate website bandwidth" function of the bandwidth calculator
     * @param input - Scanner for user input
     */
    private static void bandwidthFuncCalcWebsiteBandwidth(Scanner input) {
        System.out.println("Page views:");
        printUnitOptions(TIME_UNITS);
        System.out.print("Enter a unit: ");
        String timeUnit = input.next();
        while (!inStringArray(TIME_UNITS, timeUnit)) {
            System.out.println("Please enter one of the given units.");
            System.out.print("Enter a unit: ");
            timeUnit = input.next();
        }
        double pageViews = 0;
        boolean isNumeric = false;
        while (!isNumeric) {
            try {
                System.out.print("Enter page views value: ");
                pageViews = input.nextDouble();
                isNumeric = true;
                while (pageViews < 0) {
                    System.out.println("Must be a positive number");
                    System.out.print("Enter a value: ");
                    pageViews = input.nextDouble();
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Must be numeric.");
                // Eat the invalid input
                input.next();
                isNumeric = false;
            }
        }

        System.out.println("Average page size:");
        printUnitOptions(BIG_SIZE_UNITS);
        System.out.print("Enter a unit: ");
        String sizeUnit = input.next();
        while (!inStringArray(BIG_SIZE_UNITS, sizeUnit)) {
            System.out.println("Please enter one of the given units.");
            System.out.print("Enter a unit: ");
            sizeUnit = input.next();
        }
        double pageSize = 0;
        isNumeric = false;
        while (!isNumeric) {
            try {
                System.out.print("Enter page size value: ");
                pageSize = input.nextDouble();
                isNumeric = true;
                while (pageSize < 0) {
                    System.out.println("Must be a positive number");
                    System.out.print("Enter a value: ");
                    pageSize = input.nextDouble();
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Must be numeric.");
                // Eat the invalid input
                input.next();
                isNumeric = false;
            }
        }

        double redundancy = 0;
        isNumeric = false;
        while (!isNumeric) {
            try {
                System.out.print("Enter redundancy factor: ");
                redundancy = input.nextDouble();
                isNumeric = true;
                while (redundancy < 0) {
                    System.out.println("Must be a positive number");
                    System.out.print("Enter redundancy factor: ");
                    redundancy = input.nextDouble();
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Must be numeric.");
                // Eat the invalid input
                input.next();
                isNumeric = false;
            }
        }
        calculateWebsiteBandwidth(pageViews, timeUnit, pageSize, sizeUnit, redundancy);
        System.out.println();
    }

    /**
     * Performs the "Convert data usage to bandwidth" function of the bandwidth calculator
     * @param input - Scanner for user input
     */
    private static void bandwidthFuncConvertUsageToBandwidth(Scanner input) {
        System.out.println("Monthly usage:");
        printUnitOptions(BIG_SIZE_UNITS);
        System.out.print("Enter a unit: ");
        String fromUnit = input.next();
        while (!inStringArray(BIG_SIZE_UNITS, fromUnit)) {
            System.out.println("Please enter one of the given units.");
            System.out.print("Enter a unit: ");
            fromUnit = input.next();
        }
        double usage = 0;
        boolean isNumeric = false;
        while (!isNumeric) {
            try {
                System.out.print("Enter monthly usage: ");
                usage = input.nextDouble();
                isNumeric = true;
                while (usage < 0) {
                    System.out.println("Must be a positive number");
                    System.out.print("Enter a value: ");
                    usage = input.nextDouble();
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Must be numeric.");
                // Eat the invalid input
                input.next();
                isNumeric = false;
            }
        }

        System.out.println("Bandwidth units");
        printUnitOptions(BANDWIDTH_UNITS);
        System.out.print("Enter a bandwidth unit: ");
        String toUnit = input.next();
        while (!inStringArray(BANDWIDTH_UNITS, toUnit)) {
            System.out.println("Please enter one of the given units.");
            System.out.print("Enter a bandwidth unit: ");
            toUnit = input.next();
        }

        System.out.println(usage + " " + fromUnit + " per month is equivalent to: ");
        System.out.println(convertUsageToBandwidth(usage, fromUnit, toUnit) + " " + toUnit);
        System.out.println();
    }

    /**
     * Performs the "Convert bandwidth to data usage" function of the bandwidth calculator
     * @param input - Scanner for user input
     */
    private static void bandwidthFuncConvertBandwidthToUsage(Scanner input) {
        System.out.println("Bandwidth units");
        printUnitOptions(BANDWIDTH_UNITS);
        System.out.print("Enter a bandwidth unit: ");
        String fromUnit = input.next();
        while (!inStringArray(BANDWIDTH_UNITS, fromUnit)) {
            System.out.println("Please enter one of the given units.");
            System.out.print("Enter a bandwidth unit: ");
            fromUnit = input.next();
        }
        double bandwidth = 0;
        boolean isNumeric = false;
        while (!isNumeric) {
            try {
                System.out.print("Enter bandwidth: ");
                bandwidth = input.nextDouble();
                isNumeric = true;
                while (bandwidth < 0) {
                    System.out.println("Must be a positive number");
                    System.out.print("Enter a value: ");
                    bandwidth = input.nextDouble();
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Must be numeric.");
                // Eat the invalid input
                input.next();
                isNumeric = false;
            }
        }

        System.out.println("Monthly usage:");
        printUnitOptions(BIG_SIZE_UNITS);
        System.out.print("Enter a unit: ");
        String toUnit = input.next();
        while (!inStringArray(BIG_SIZE_UNITS, toUnit)) {
            System.out.println("Please enter one of the given units.");
            System.out.print("Enter a unit: ");
            toUnit = input.next();
        }

        System.out.println(bandwidth + " " + fromUnit + " is equivalent to: ");
        System.out.println(convertBandwidthToUsage(bandwidth, fromUnit, toUnit) + " " + toUnit + " per month.");
        System.out.println();
    }

    /**
     * Prints the given units as a bulleted list.
     * @param units - String array of units to print
     */
    private static void printUnitOptions(String[] units) {
        if (units != null) {
            for (String u : units) {
                System.out.println("   - " + u);
            }
        }
    }

    public static void main(String[] args) {
        initializeUnitConversions();

        //runTests();

        Scanner input = new Scanner(System.in);

        System.out.println("Please select a calculator to use (enter the number of your selection, or \"q\" to quit):");
        System.out.println("   1. Binary calculator");
        System.out.println("   2. Hexadecimal calculator");
        System.out.println("   3. Bandwidth calculator");
        System.out.print("Selection: ");

        String selection = input.next();

        while (!selection.equals("q")) {
            // Binary calculator
            if (selection.equals("1")) {
                boolean inCalculator = true;
                while (inCalculator) {
                    inCalculator = runBinaryCalculator(input);
                }
            }
            // Hexadecimal calculator
            if (selection.equals("2")) {
                boolean inCalculator = true;
                while (inCalculator) {
                    inCalculator = runHexCalculator(input);
                }
            }
            // Bandwidth calculator
            if (selection.equals("3")) {
                boolean inCalculator = true;
                while (inCalculator) {
                    inCalculator = runBandwidthCalculator(input);
                }
            }

            System.out.println("Please select a calculator to use (type the number of your selection, or \"q\" to quit:");
            System.out.println("   1. Binary calculator");
            System.out.println("   2. Hexadecimal calculator");
            System.out.println("   3. Bandwidth calculator");
            System.out.print("Selection: ");

            selection = input.next();
        }
    }
}