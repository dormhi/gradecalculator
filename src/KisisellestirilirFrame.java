import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Kişiselleştirilir Frame - Modern UI tasarımı
 */
public class KisisellestirilirFrame extends JFrame implements ActionListener {
    private JTextField notField, agirlikField, notAdiField;
    private JTextField ddField, dcField, ccField, cbField, bbField, baField, aaField;
    private JTextArea notlarArea, sonucArea;
    private JLabel toplamAgirlikLabel;
    private List<Double> notlar = new ArrayList<>();
    private List<Double> agirliklar = new ArrayList<>();
    private List<String> notAdlari = new ArrayList<>();

    private static final double DEFAULT_DD = 30;
    private static final double DEFAULT_DC = 35;
    private static final double DEFAULT_CC = 40;
    private static final double DEFAULT_CB = 50;
    private static final double DEFAULT_BB = 60;
    private static final double DEFAULT_BA = 70;
    private static final double DEFAULT_AA = 80;

    public KisisellestirilirFrame() {
        setTitle("Kişiselleştirilir Sistem");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(ThemeManager.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Başlık
        JLabel titleLabel = ThemeManager.createTitleLabel("⚙️ Kişiselleştirilir Sistem");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // İçerik
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ThemeManager.BG_DARK);

        // Not ekleme kartı
        JPanel ekleCard = createCard("Not Ekle");
        ekleCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        ekleCard.add(ThemeManager.createLabel("Not Adı:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        notAdiField = ThemeManager.createStyledTextField();
        ekleCard.add(notAdiField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        ekleCard.add(ThemeManager.createLabel("Not:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        notField = ThemeManager.createStyledTextField();
        ekleCard.add(notField, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0;
        ekleCard.add(ThemeManager.createLabel("Ağırlık(%):"), gbc);
        gbc.gridx = 5;
        gbc.weightx = 0.5;
        agirlikField = ThemeManager.createStyledTextField();
        ekleCard.add(agirlikField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        ModernButton ekleButton = new ModernButton("➕ Ekle", ThemeManager.SECONDARY);
        ekleButton.setPreferredSize(new Dimension(120, 35));
        ekleButton.addActionListener(e -> notEkle());
        ekleCard.add(ekleButton, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 3;
        toplamAgirlikLabel = new JLabel("Toplam Ağırlık: %0");
        toplamAgirlikLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        toplamAgirlikLabel.setForeground(ThemeManager.SECONDARY);
        ekleCard.add(toplamAgirlikLabel, gbc);

        contentPanel.add(ekleCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Eşik kartı
        JPanel esikCard = createCard("Harf Notu Eşikleri");
        esikCard.setLayout(new GridLayout(2, 7, 8, 8));

        String[] harfler = { "DD", "DC", "CC", "CB", "BB", "BA", "AA" };
        double[] defaults = { DEFAULT_DD, DEFAULT_DC, DEFAULT_CC, DEFAULT_CB, DEFAULT_BB, DEFAULT_BA, DEFAULT_AA };

        for (String harf : harfler) {
            JLabel label = new JLabel(harf, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setForeground(ThemeManager.TEXT_PRIMARY);
            esikCard.add(label);
        }

        ddField = createSmallTextField(defaults[0]);
        esikCard.add(ddField);
        dcField = createSmallTextField(defaults[1]);
        esikCard.add(dcField);
        ccField = createSmallTextField(defaults[2]);
        esikCard.add(ccField);
        cbField = createSmallTextField(defaults[3]);
        esikCard.add(cbField);
        bbField = createSmallTextField(defaults[4]);
        esikCard.add(bbField);
        baField = createSmallTextField(defaults[5]);
        esikCard.add(baField);
        aaField = createSmallTextField(defaults[6]);
        esikCard.add(aaField);

        contentPanel.add(esikCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Eklenen notlar
        notlarArea = ThemeManager.createResultArea();
        notlarArea.setRows(4);
        JScrollPane notlarScroll = ThemeManager.createStyledScrollPane(notlarArea, "Eklenen Notlar");
        notlarScroll.setPreferredSize(new Dimension(640, 100));
        contentPanel.add(notlarScroll);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Butonlar
        JPanel butonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        butonPanel.setBackground(ThemeManager.BG_DARK);

        ModernButton analizButton = new ModernButton("🔍 Analiz Et", ThemeManager.PRIMARY);
        analizButton.setPreferredSize(new Dimension(150, 40));
        analizButton.addActionListener(this);
        butonPanel.add(analizButton);

        ModernButton temizleButton = new ModernButton("🗑️ Temizle", ThemeManager.DANGER);
        temizleButton.setPreferredSize(new Dimension(150, 40));
        temizleButton.addActionListener(e -> temizle());
        butonPanel.add(temizleButton);

        contentPanel.add(butonPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Sonuç
        sonucArea = ThemeManager.createResultArea();
        JScrollPane sonucScroll = ThemeManager.createStyledScrollPane(sonucArea, "Sonuç ve Analiz");
        sonucScroll.setPreferredSize(new Dimension(640, 150));
        contentPanel.add(sonucScroll);

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
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        return card;
    }

    private JTextField createSmallTextField(double value) {
        JTextField field = ThemeManager.createStyledTextField();
        field.setText(String.valueOf((int) value));
        field.setHorizontalAlignment(JTextField.CENTER);
        return field;
    }

    private void notEkle() {
        try {
            String notAdi = notAdiField.getText().trim();
            if (notAdi.isEmpty()) {
                notAdi = "Not " + (notlar.size() + 1);
            }

            double not = Double.parseDouble(notField.getText());
            double agirlik = Double.parseDouble(agirlikField.getText());

            if (!NotHesaplayici.notGecerliMi(not)) {
                JOptionPane.showMessageDialog(this, "Not 0-100 arasında olmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!NotHesaplayici.agirlikGecerliMi(agirlik)) {
                JOptionPane.showMessageDialog(this, "Ağırlık 0-100 arasında olmalıdır!", "Hata",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            double mevcutToplam = agirliklar.stream().mapToDouble(Double::doubleValue).sum();
            if (mevcutToplam + agirlik > 100) {
                JOptionPane.showMessageDialog(this, "Toplam ağırlık %100'ü geçemez!", "Hata",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            notlar.add(not);
            agirliklar.add(agirlik);
            notAdlari.add(notAdi);

            notlarArea.append(String.format("%-15s Not: %6.2f  Ağırlık: %%%5.1f%n", notAdi, not, agirlik));
            toplamAgirlikLabel.setText("Toplam Ağırlık: %" + (mevcutToplam + agirlik));

            notAdiField.setText("");
            notField.setText("");
            agirlikField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli sayılar girin!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void temizle() {
        notlar.clear();
        agirliklar.clear();
        notAdlari.clear();
        notlarArea.setText("");
        toplamAgirlikLabel.setText("Toplam Ağırlık: %0");
        sonucArea.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (notlar.isEmpty()) {
            sonucArea.setText("❌ Lütfen en az bir not ekleyin!");
            return;
        }

        try {
            double ddEsik = Double.parseDouble(ddField.getText().trim());
            double dcEsik = Double.parseDouble(dcField.getText().trim());
            double ccEsik = Double.parseDouble(ccField.getText().trim());
            double cbEsik = Double.parseDouble(cbField.getText().trim());
            double bbEsik = Double.parseDouble(bbField.getText().trim());
            double baEsik = Double.parseDouble(baField.getText().trim());
            double aaEsik = Double.parseDouble(aaField.getText().trim());

            double mevcutToplam = 0;
            double toplamAgirlik = 0;

            for (int i = 0; i < notlar.size(); i++) {
                mevcutToplam += notlar.get(i) * (agirliklar.get(i) / 100);
                toplamAgirlik += agirliklar.get(i);
            }

            double kalanAgirlik = 100 - toplamAgirlik;

            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════════\n");
            sb.append("                  DURUM ANALİZİ                \n");
            sb.append("═══════════════════════════════════════════════\n\n");

            sb.append(String.format("📊 Mevcut Katkınız: %.2f puan\n", mevcutToplam));
            sb.append(String.format("📊 Kullanılan Ağırlık: %%%.1f\n", toplamAgirlik));
            sb.append(String.format("📊 Kalan Ağırlık: %%%.1f\n\n", kalanAgirlik));

            if (kalanAgirlik == 0) {
                String mevcutHarf = getHarfNotu(mevcutToplam, ddEsik, dcEsik, ccEsik, cbEsik, bbEsik, baEsik, aaEsik);
                sb.append(String.format("🎓 Toplam Puanınız: %.2f\n", mevcutToplam));
                sb.append(String.format("🎓 Harf Notunuz: %s\n\n", mevcutHarf));
                sb.append("✅ Tüm notlarınız girilmiş!\n");
            } else {
                sb.append("───────────────────────────────────────────────\n");
                sb.append("   KALAN %%%.1f İÇİN GEREKLİ ORTALAMA NOT    \n".formatted(kalanAgirlik));
                sb.append("───────────────────────────────────────────────\n\n");

                sb.append(hesaplaGerekliNot("DD", ddEsik, mevcutToplam, kalanAgirlik));
                sb.append(hesaplaGerekliNot("DC", dcEsik, mevcutToplam, kalanAgirlik));
                sb.append(hesaplaGerekliNot("CC", ccEsik, mevcutToplam, kalanAgirlik));
                sb.append(hesaplaGerekliNot("CB", cbEsik, mevcutToplam, kalanAgirlik));
                sb.append(hesaplaGerekliNot("BB", bbEsik, mevcutToplam, kalanAgirlik));
                sb.append(hesaplaGerekliNot("BA", baEsik, mevcutToplam, kalanAgirlik));
                sb.append(hesaplaGerekliNot("AA", aaEsik, mevcutToplam, kalanAgirlik));
            }

            sb.append("\n═══════════════════════════════════════════════\n");

            sonucArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            sonucArea.setText("❌ Lütfen eşik değerlerini doğru girin!");
        }
    }

    private String hesaplaGerekliNot(String harfNotu, double esik, double mevcutToplam, double kalanAgirlik) {
        double gerekliPuan = esik - mevcutToplam;
        double gerekliNot = gerekliPuan / (kalanAgirlik / 100);

        if (gerekliNot <= 0) {
            return String.format("✅ %s için: Zaten garantilediniz!\n", harfNotu);
        } else if (gerekliNot > 100) {
            return String.format("❌ %s için: Maalesef ulaşılamaz\n", harfNotu);
        } else {
            return String.format("📌 %s için: Kalan notlardan ortalama %.2f almanız gerekiyor\n", harfNotu, gerekliNot);
        }
    }

    private String getHarfNotu(double toplam, double dd, double dc, double cc, double cb, double bb, double ba,
            double aa) {
        if (toplam >= aa)
            return "AA";
        if (toplam >= ba)
            return "BA";
        if (toplam >= bb)
            return "BB";
        if (toplam >= cb)
            return "CB";
        if (toplam >= cc)
            return "CC";
        if (toplam >= dc)
            return "DC";
        if (toplam >= dd)
            return "DD";
        return "FF (Kaldınız)";
    }
}
