import javax.swing.*;
import java.awt.*;

/**
 * Ana uygulama sınıfı - Modern UI tasarımı
 */
public class NotHesaplamaUygulamasi extends JFrame {

    public NotHesaplamaUygulamasi() {
        setTitle("Not Hesaplama Uygulaması");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Ana panel - koyu tema
        JPanel anaPanel = new JPanel();
        anaPanel.setLayout(new BoxLayout(anaPanel, BoxLayout.Y_AXIS));
        anaPanel.setBackground(ThemeManager.BG_DARK);
        anaPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Logo/Icon alanı
        JLabel iconLabel = new JLabel("📊", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        anaPanel.add(iconLabel);

        anaPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Başlık
        JLabel baslikLabel = ThemeManager.createTitleLabel("Not Hesaplama");
        baslikLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        anaPanel.add(baslikLabel);

        anaPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Alt başlık
        JLabel altBaslik = ThemeManager.createSubtitleLabel("Bir hesaplama yöntemi seçin");
        altBaslik.setAlignmentX(Component.CENTER_ALIGNMENT);
        anaPanel.add(altBaslik);

        anaPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Butonlar paneli
        JPanel butonPanel = new JPanel();
        butonPanel.setLayout(new BoxLayout(butonPanel, BoxLayout.Y_AXIS));
        butonPanel.setBackground(ThemeManager.BG_DARK);
        butonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Klasik Sistem Butonu
        ModernButton klasikButton = new ModernButton("📚  Klasik Sistem", ThemeManager.PRIMARY);
        klasikButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        klasikButton.setMaximumSize(new Dimension(280, 50));
        klasikButton.setPreferredSize(new Dimension(280, 50));
        klasikButton.addActionListener(e -> new KlasikSistemFrame());
        butonPanel.add(klasikButton);

        butonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Kişiselleştirilir Butonu
        ModernButton kisiselButton = new ModernButton("⚙️  Kişiselleştirilir", ThemeManager.SECONDARY);
        kisiselButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        kisiselButton.setMaximumSize(new Dimension(280, 50));
        kisiselButton.setPreferredSize(new Dimension(280, 50));
        kisiselButton.addActionListener(e -> new KisisellestirilirFrame());
        butonPanel.add(kisiselButton);

        butonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Rus Usulü Butonu
        ModernButton rusButton = new ModernButton("🎓  Rus Usulü", ThemeManager.ACCENT);
        rusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rusButton.setMaximumSize(new Dimension(280, 50));
        rusButton.setPreferredSize(new Dimension(280, 50));
        rusButton.addActionListener(e -> new RusUsuluFrame());
        butonPanel.add(rusButton);

        anaPanel.add(butonPanel);

        anaPanel.add(Box.createVerticalGlue());

        // Alt bilgi
        JLabel footerLabel = new JLabel("v2.0 - Modern UI", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(107, 114, 128));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        anaPanel.add(footerLabel);

        add(anaPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Sistem görünümünü ayarla
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new NotHesaplamaUygulamasi());
    }
}