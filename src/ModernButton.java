import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Modern buton sınıfı - Hover efekti ve yuvarlak köşeler
 */
public class ModernButton extends JButton {
    private Color normalColor;
    private Color hoverColor;
    private Color pressedColor;
    private int cornerRadius = 10;
    private boolean isHovered = false;
    private boolean isPressed = false;

    public ModernButton(String text, Color baseColor) {
        super(text);
        this.normalColor = baseColor;
        this.hoverColor = brighten(baseColor, 0.1f);
        this.pressedColor = darken(baseColor, 0.1f);

        setFont(ThemeManager.FONT_BUTTON);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(200, 45));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                isPressed = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Arka plan rengi
        Color bgColor = normalColor;
        if (isPressed) {
            bgColor = pressedColor;
        } else if (isHovered) {
            bgColor = hoverColor;
        }

        // Gölge efekti (hover durumunda)
        if (isHovered && !isPressed) {
            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, cornerRadius, cornerRadius);
        }

        // Ana buton
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // Metin
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), x, y);

        g2.dispose();
    }

    private Color brighten(Color color, float factor) {
        int r = Math.min(255, (int) (color.getRed() * (1 + factor)));
        int g = Math.min(255, (int) (color.getGreen() * (1 + factor)));
        int b = Math.min(255, (int) (color.getBlue() * (1 + factor)));
        return new Color(r, g, b);
    }

    private Color darken(Color color, float factor) {
        int r = Math.max(0, (int) (color.getRed() * (1 - factor)));
        int g = Math.max(0, (int) (color.getGreen() * (1 - factor)));
        int b = Math.max(0, (int) (color.getBlue() * (1 - factor)));
        return new Color(r, g, b);
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }
}
