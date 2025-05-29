import javax.swing.text.*;

public class DigitFilters extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (isValidInput(string, fb.getDocument().getLength())) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)
            throws BadLocationException {
        if (isValidInput(string, fb.getDocument().getLength())) {
            super.replace(fb, offset, length, string, attr);
        }
    }

    private boolean isValidInput(String text, int currentLength) {
        // Chỉ cho phép 1 chữ số từ 1–9 (không phải 0)
        return text.matches("[1-9]") && currentLength == 0;
    }
}
