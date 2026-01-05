import javax.swing.*;
import java.awt.*;

/**
 * Buton oluşturmak için yardımcı sınıf
 * Tüm butonların tutarlı görünmesini sağlar
 */
public class ButtonFactory {

    // Renk temaları
    public static final Color BLUE_THEME = new Color(30, 80, 140);
    public static final Color GREEN_THEME = new Color(20, 120, 70);
    public static final Color RED_THEME = new Color(180, 50, 50);
    public static final Color ORANGE_THEME = new Color(200, 120, 20);
    public static final Color PURPLE_THEME = new Color(100, 50, 150);

    /**
     * Stilize edilmiş buton oluşturur
     * 
     * @param text            Buton metni
     * @param backgroundColor Arka plan rengi
     * @return Stilize edilmiş JButton
     */
    public static JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 50));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Küçük boyutlu stilize edilmiş buton oluşturur
     * 
     * @param text            Buton metni
     * @param backgroundColor Arka plan rengi
     * @return Stilize edilmiş küçük JButton
     */
    public static JButton createSmallButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 35));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
