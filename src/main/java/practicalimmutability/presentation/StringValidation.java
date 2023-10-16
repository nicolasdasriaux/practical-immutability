package practicalimmutability.presentation;

public class StringValidation {
    public static boolean isTrimmedAndNonEmpty(String s) {
        final String trimmed = s.trim();
        return !trimmed.isEmpty() && trimmed.equals(s);
    }
}
