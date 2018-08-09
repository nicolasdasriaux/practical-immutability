package fr.carbonit.common;

public class StringValidation {
    public static boolean isTrimmedAndNonEmpty(final String s) {
        final String trimmed = s.trim();
        return !trimmed.isEmpty() && trimmed.equals(s);
    }
}
