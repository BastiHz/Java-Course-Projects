package sortingtool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    private enum SortingType {NATURAL, BY_COUNT}
    private enum DataType {WORD, LINE, LONG}
    private static SortingType sType = SortingType.NATURAL;
    private static DataType dType = DataType.WORD;
    private static String inputFileName;
    private static String outputFileName;
    private static String output = "";

    public static void main(final String[] args) {
        // System.out.println("Working Directory = " + System.getProperty("user.dir"));
        if (!readArgs(args)) {
            return;
        }
        final List<String> rawData = readInput();
        if (sType == SortingType.NATURAL) {
            sortNatural(rawData);
        } else {
            sortByCount(rawData);
        }
        printResult();
    }

    private static boolean readArgs(final String[] args) {
        for (int i = 0; i < args.length; i++) {
            final String arg = args[i];
            switch (arg) {
                case "-sortingType":
                    try {
                        readSortingType(args, i);
                        i++;
                    } catch (final IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("No sorting type defined!");
                        return false;
                    }
                    break;
                case "-dataType":
                    try {
                        readDataType(args, i);
                        i++;
                    } catch (final IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("No data type defined!");
                        return false;
                    }
                    break;
                case "-inputFile":
                    try {
                        inputFileName = args[i + 1];
                        i++;
                    } catch (final ArrayIndexOutOfBoundsException e) {
                        System.out.println("No input file defined!");
                        return false;
                    }
                    break;
                case "-outputFile":
                    try {
                        outputFileName = args[i + 1];
                        i++;
                    } catch (final ArrayIndexOutOfBoundsException e) {
                        System.out.println("No output file defined!");
                        return false;
                    }
                    break;
                default:
                    System.out.printf("\"%s\" is not a valid parameter and will be skipped.\n", arg);
            }
        }
        return true;
    }

    private static void readDataType(final String[] args, final int index)
        throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        switch (args[index + 1]) {
            case "word":
                dType = DataType.WORD;
                break;
            case "long":
                dType = DataType.LONG;
                break;
            case "line":
                dType = DataType.LINE;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static void readSortingType(final String[] args, final int index)
            throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        switch (args[index + 1]) {
            case "natural":
                sType = SortingType.NATURAL;
                break;
            case "byCount":
                sType = SortingType.BY_COUNT;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static List<String> readInput() {
        List<String> data = new ArrayList<>();
        if (inputFileName == null) {
            data = scanInput(new Scanner(System.in));
        } else {
            final File inputFile = new File(inputFileName);
            try (final Scanner scanner = new Scanner(inputFile)) {
                data = scanInput(scanner);
            } catch (final FileNotFoundException e) {
                System.out.println("Input file not found.");
            }
        }
        return data;
    }

    private static List<String> scanInput(final Scanner scanner) {
        final List<String> data = new ArrayList<>();
        switch (dType) {
            case LINE:
                while (scanner.hasNextLine()) {
                    data.add(scanner.nextLine());
                }
                break;
            case WORD:
                while (scanner.hasNext()) {
                    data.add(scanner.next());
                }
                break;
            case LONG:
                while (scanner.hasNext()) {
                    final String s = scanner.next();
                    try {
                        Long.parseLong(s);
                        data.add(s);
                    } catch (final NumberFormatException e) {
                        System.out.printf("\"%s\" is not a long. It will be skipped.\n", s);
                    }
                }
                break;
        }
        return data;
    }

    private static void printTotal(final int total) {
        String type = "";
        switch (dType) {
            case WORD:
                type = "words";
                break;
            case LINE:
                type = "lines";
                break;
            case LONG:
                type = "numbers";
                break;
        }
        output += String.format("Total %s: %d.", type, total);
    }

    private static List<String> sortNumbers(final List<String> data) {
        // This looks bad. Is there a better solution than
        // casting to long, sorting, and casting back to string?

        final List<Long> longList = new ArrayList<>();
        for (final String number : data) {
            longList.add(Long.valueOf(number));
        }
        Collections.sort(longList);
        final List<String> sortedData = new ArrayList<>();
        for (final long number : longList) {
            sortedData.add(String.valueOf(number));
        }
        return sortedData;
    }

    private static void sortNatural(List<String> data) {
        printTotal(data.size());
        final String sep = dType == DataType.LINE ? "\n" : " ";
        output += "\nSorted data:";

        if (dType == DataType.LONG) {
            data = sortNumbers(data);
        } else {
            Collections.sort(data);
        }
        for (final String x : data) {
            output += sep + x;
        }
    }

    private static void sortByCount(final List<String> data) {
        final HashMap<String, Integer> dataCount = new HashMap<>();
        for (final String key : data) {
            final int count = dataCount.getOrDefault(key, 0);
            dataCount.put(key, count + 1);
        }
        final TreeMap<Integer, Set<String>> countData = new TreeMap<>();
        for (final Map.Entry<String, Integer> entry : dataCount.entrySet()) {
            final Integer key = entry.getValue();
            final String value = entry.getKey();
            final Set<String> values = countData.getOrDefault(key, new TreeSet<>());
            values.add(value);
            countData.put(key, values);
        }

        if (dType == DataType.LONG) {
            for (final Map.Entry<Integer, Set<String>> entry : countData.entrySet()) {
                // Ugh, this is ugly. There must be a better way.
                final Set<String> sortedValues = new LinkedHashSet<>(
                    sortNumbers(new ArrayList<>(entry.getValue()))
                );
                countData.put(entry.getKey(), sortedValues);
            }
        }

        final int total = data.size();
        printTotal(total);

        for (final Map.Entry<Integer, Set<String>> entry : countData.entrySet()) {
            final int count = entry.getKey();
            final double percentage = (double) count / total * 100;
            for (final String value : entry.getValue()) {
                output += String.format("\n%s: %d time(s), %.0f%%", value, count, percentage);
            }
        }
    }

    private static void printResult() {
        output += "\n";
        if (outputFileName == null) {
            System.out.print(output);
        } else {
            final File outputFile = new File(outputFileName);
            try (final FileWriter writer = new FileWriter(outputFile)) {
                writer.write(output);
            } catch (final IOException e) {
                System.out.printf("An exception occurred while writing output %s", e.getMessage());
            }
        }
    }
}
