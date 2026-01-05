import javax.swing.*;
import java.awt.*;

/**
 * Tema yöneticisi - Tutarlı renk ve stil sistemi
 */
public class ThemeManager {

    // Ana renkler
    public static final Color PRIMARY = new Color(79, 70, 229); // Mor/İndigo
    public static final Color PRIMARY_DARK = new Color(67, 56, 202);
    public static final Color SECONDARY = new Color(16, 185, 129); // Yeşil
    public static final Color SECONDARY_DARK = new Color(5, 150, 105);
    public static final Color ACCENT = new Color(139, 92, 246); // Açık mor
    public static final Color DANGER = new Color(239, 68, 68); // Kırmızı
    public static final Color WARNING = new Color(245, 158, 11); // Turuncu

    // Arka plan renkleri
    public static final Color BG_DARK = new Color(17, 24, 39); // Koyu arka plan
    public static final Color BG_CARD = new Color(31, 41, 55); // Kart arka planı
    public static final Color BG_LIGHT = new Color(243, 244, 246); // Açık arka plan
    public static final Color BG_WHITE = new Color(255, 255, 255);

    // Metin renkleri
    public static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    public static final Color TEXT_SECONDARY = new Color(156, 163, 175);
    public static final Color TEXT_DARK = new Color(31, 41, 55);

    // Fontlar
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_RESULT = new Font("Segoe UI Semibold", Font.PLAIN, 14);
    public static final Font FONT_MONO = new Font("JetBrains Mono", Font.PLAIN, 12);

    /**
     * Panel arka planını ayarlar
     */
    public static void stylePanel(JPanel panel, boolean isDark) {
        panel.setBackground(isDark ? BG_DARK : BG_LIGHT);
    }

    /**
     * Kart stilinde panel oluşturur
     */
    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        return card;
    }

    /**
     * Stilize edilmiş text field oluşturur
     */
    public static JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(FONT_INPUT);
        field.setBackground(new Color(55, 65, 81));
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(75, 85, 99), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return field;
    }

    /**
     * Stilize edilmiş label oluşturur
     */
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_SECONDARY);
        return label;
    }

    /**
     * Başlık label oluşturur
     */
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(FONT_TITLE);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    /**
     * Alt başlık label oluşturur
     */
    public static JLabel createSubtitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(FONT_SUBTITLE);
        label.setForeground(TEXT_SECONDARY);
        return label;
    }

    /**
     * Sonuç text area oluşturur
     */
    public static JTextArea createResultArea() {
        JTextArea area = new JTextArea();
        area.setFont(FONT_MONO);
        area.setBackground(BG_CARD);
        area.setForeground(TEXT_PRIMARY);
        area.setCaretColor(TEXT_PRIMARY);
        area.setEditable(false);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return area;
    }

    /**
     * Scroll pane stilize eder
     */
    public static JScrollPane createStyledScrollPane(Component view, String title) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
                title,
                0, 0,
                FONT_LABEL,
                TEXT_SECONDARY));
        scrollPane.getViewport().setBackground(BG_CARD);
        return scrollPane;
    }
}
