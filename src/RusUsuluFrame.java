import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Rus Usulü Frame - Modern UI tasarımı
 */
public class RusUsuluFrame extends JFrame implements ActionListener {
    private JTextField odevField, quizField, katilimField, vizeField, finalField;
    private JLabel sonucLabel;

    public RusUsuluFrame() {
        setTitle("Rus Usulü - Not Hesaplama");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(ThemeManager.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Başlık
        JLabel titleLabel = ThemeManager.createTitleLabel("🎓 Rus Usulü");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // İçerik
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ThemeManager.BG_DARK);

        // Not girişi kartı
        JPanel notCard = createCard("Not Girişi");
        notCard.setLayout(new GridLayout(5, 2, 15, 12));

        notCard.add(ThemeManager.createLabel("Ödev Notu (%15):"));
        odevField = ThemeManager.createStyledTextField();
        notCard.add(odevField);

        notCard.add(ThemeManager.createLabel("Quiz Notu (%9):"));
        quizField = ThemeManager.createStyledTextField();
        notCard.add(quizField);

        notCard.add(ThemeManager.createLabel("Katılım Notu (%15):"));
        katilimField = ThemeManager.createStyledTextField();
        notCard.add(katilimField);

        notCard.add(ThemeManager.createLabel("Vize Notu (%21):"));
        vizeField = ThemeManager.createStyledTextField();
        notCard.add(vizeField);

        notCard.add(ThemeManager.createLabel("Final Notu (%40):"));
        finalField = ThemeManager.createStyledTextField();
        notCard.add(finalField);

        contentPanel.add(notCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Hesapla butonu
        ModernButton hesaplaButton = new ModernButton("🔍  Hesapla", ThemeManager.ACCENT);
        hesaplaButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        hesaplaButton.addActionListener(this);
        contentPanel.add(hesaplaButton);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Sonuç kartı
        JPanel sonucCard = createCard("Sonuç");
        sonucCard.setLayout(new BorderLayout());
        sonucLabel = new JLabel("Sonuç bekleniyor...", SwingConstants.CENTER);
        sonucLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sonucLabel.setForeground(ThemeManager.TEXT_PRIMARY);
        sonucCard.add(sonucLabel, BorderLayout.CENTER);
        sonucCard.setPreferredSize(new Dimension(480, 60));
        contentPanel.add(sonucCard);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createCard(String title) {
        JPanel card = new JPanel();
        card.setBackground(ThemeManager.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
                        title, 0, 0,
                        ThemeManager.FONT_LABEL,
                        ThemeManager.TEXT_SECONDARY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        return card;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double odev = Double.parseDouble(odevField.getText());
            double quiz = Double.parseDouble(quizField.getText());
            double katilim = Double.parseDouble(katilimField.getText());
            double vize = Double.parseDouble(vizeField.getText());
            double finalNot = Double.parseDouble(finalField.getText());

            if (!NotHesaplayici.notGecerliMi(odev) || !NotHesaplayici.notGecerliMi(quiz) ||
                    !NotHesaplayici.notGecerliMi(katilim) || !NotHesaplayici.notGecerliMi(vize) ||
                    !NotHesaplayici.notGecerliMi(finalNot)) {
                sonucLabel.setText("❌ Notlar 0-100 arasında olmalıdır!");
                sonucLabel.setForeground(ThemeManager.DANGER);
                return;
            }

            double toplam = NotHesaplayici.klasikSistemHesapla(odev, quiz, katilim, vize, finalNot);
            String harfNotu = NotHesaplayici.harfNotuBelirle(toplam);

            sonucLabel.setText("🎓 " + harfNotu + " (Toplam: " + String.format("%.2f", toplam) + ")");

            // Sonuca göre renk
            if (toplam < 30) {
                sonucLabel.setForeground(ThemeManager.DANGER);
            } else if (toplam < 50) {
                sonucLabel.setForeground(ThemeManager.WARNING);
            } else {
                sonucLabel.setForeground(ThemeManager.SECONDARY);
            }

        } catch (NumberFormatException ex) {
            sonucLabel.setText("❌ Lütfen geçerli sayılar girin!");
            sonucLabel.setForeground(ThemeManager.DANGER);
        }
    }
}
