package guesstheanimal;

import java.util.Map;
import java.util.Scanner;
import java.util.Random;
import java.util.ResourceBundle;


class TextHelper {

    static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final ResourceBundle bundle = ResourceBundle.getBundle("TextResource");
    @SuppressWarnings("unchecked")
    static final Map<String, String> FACT_NEGATION = (Map<String, String>) getObject("itCanHasIs.negate");
    @SuppressWarnings("unchecked")
    static final Map<String, String> FACT_QUESTION = (Map<String, String>) getObject("itCanHasIs.question");

    static String localizeFilename(final String extension) {
        String locale = bundle.getLocale().toString();
        if (!locale.isEmpty()) {
            locale = "_" + locale;
        }
        return "animals" + locale + extension;
    }

    static String nextLine() {
        return scanner.nextLine().strip().toLowerCase();
    }

    static String capitalizeFirst(final String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    static void print(final String key) {
        System.out.print(getString(key));
    }

    static void println(final String key) {
        System.out.println(getString(key));
    }

    static void printf(final String key, final Object arg) {
        System.out.printf(getString(key), arg);
    }

    static void printf(final String key, final Object[] args) {
        System.out.printf(getString(key), args);
    }

    static String getString(final String key) {
        final Object obj = bundle.getObject(key);
        if (obj.getClass().isArray()) {
            final String[] arr = (String[]) obj;
            return arr[random.nextInt(arr.length)];
        }
        return (String) obj;
    }

    static Object getObject(final String key) {
        return bundle.getObject(key);
    }
}
