import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasik Sistem Frame - Modern UI ile Vize/Final sistemi
 */
public class KlasikSistemFrame extends JFrame implements ActionListener {
    private JTextField vizeField, finalField;
    private JTextField ddField, dcField, ccField, cbField, bbField, baField, aaField;
    private JTextArea sonucArea;

    private static final double DEFAULT_DD = 30;
    private static final double DEFAULT_DC = 35;
    private static final double DEFAULT_CC = 40;
    private static final double DEFAULT_CB = 50;
    private static final double DEFAULT_BB = 60;
    private static final double DEFAULT_BA = 70;
    private static final double DEFAULT_AA = 80;

    public KlasikSistemFrame() {
        setTitle("Klasik Sistem - Vize/Final");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(ThemeManager.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Başlık
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ThemeManager.BG_DARK);
        JLabel titleLabel = ThemeManager.createTitleLabel("📚 Klasik Sistem");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // İçerik paneli
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ThemeManager.BG_DARK);

        // Not girişi kartı
        JPanel notCard = createCard("Not Girişi");
        notCard.setLayout(new GridLayout(2, 2, 15, 10));

        notCard.add(ThemeManager.createLabel("Vize Notu (%40):"));
        vizeField = ThemeManager.createStyledTextField();
        notCard.add(vizeField);

        notCard.add(ThemeManager.createLabel("Final Notu (%60):"));
        finalField = ThemeManager.createStyledTextField();
        notCard.add(finalField);

        contentPanel.add(notCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Eşik değerleri kartı
        JPanel esikCard = createCard("Harf Notu Eşikleri");
        esikCard.setLayout(new GridLayout(2, 7, 8, 8));

        String[] harfler = { "DD", "DC", "CC", "CB", "BB", "BA", "AA" };
        double[] defaults = { DEFAULT_DD, DEFAULT_DC, DEFAULT_CC, DEFAULT_CB, DEFAULT_BB, DEFAULT_BA, DEFAULT_AA };
        JTextField[] fields = new JTextField[7];

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
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Hesapla butonu
        ModernButton hesaplaButton = new ModernButton("🔍  Analiz Et", ThemeManager.PRIMARY);
        hesaplaButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        hesaplaButton.addActionListener(this);
        contentPanel.add(hesaplaButton);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Sonuç alanı
        sonucArea = ThemeManager.createResultArea();
        JScrollPane scrollPane = ThemeManager.createStyledScrollPane(sonucArea, "Sonuç ve Analiz");
        scrollPane.setPreferredSize(new Dimension(580, 180));
        contentPanel.add(scrollPane);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String vizeText = vizeField.getText().trim();
            String finalText = finalField.getText().trim();

            // Vize boşsa 0, değilse girilen değer
            Double vize = vizeText.isEmpty() ? null : Double.parseDouble(vizeText);
            Double finalNotu = finalText.isEmpty() ? null : Double.parseDouble(finalText);

            double ddEsik = Double.parseDouble(ddField.getText().trim());
            double dcEsik = Double.parseDouble(dcField.getText().trim());
            double ccEsik = Double.parseDouble(ccField.getText().trim());
            double cbEsik = Double.parseDouble(cbField.getText().trim());
            double bbEsik = Double.parseDouble(bbField.getText().trim());
            double baEsik = Double.parseDouble(baField.getText().trim());
            double aaEsik = Double.parseDouble(aaField.getText().trim());

            if (vize != null && !NotHesaplayici.notGecerliMi(vize)) {
                sonucArea.setText("❌ Vize notu 0-100 arasında olmalıdır!");
                return;
            }

            if (finalNotu != null && !NotHesaplayici.notGecerliMi(finalNotu)) {
                sonucArea.setText("❌ Final notu 0-100 arasında olmalıdır!");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════════\n");
            sb.append("                  DURUM ANALİZİ                \n");
            sb.append("═══════════════════════════════════════════════\n\n");

            // Vize girilmedi ama final girildi - senaryo analizi
            if (vize == null && finalNotu != null) {
                sb.append("⚠️ Vize notu girilmedi - Senaryo Analizi\n");
                sb.append(String.format("📝 Final Notunuz: %.2f\n\n", finalNotu));

                sb.append("───────────────────────────────────────────────\n");
                sb.append("   VİZE NOTUNA GÖRE HARF NOTU SENARYOLARI     \n");
                sb.append("───────────────────────────────────────────────\n\n");

                // Farklı vize senaryoları
                int[] vizeSenaryolari = { 0, 20, 30, 40, 50, 60, 70, 80, 90, 100 };
                for (int vizeSenaryo : vizeSenaryolari) {
                    double toplam = (vizeSenaryo * 0.40) + (finalNotu * 0.60);
                    String harf = getHarfNotu(toplam, ddEsik, dcEsik, ccEsik, cbEsik, bbEsik, baEsik, aaEsik);
                    sb.append(String.format("📌 Vize %3d olsaydı → Toplam: %5.2f → %s\n", vizeSenaryo, toplam, harf));
                }
            }
            // Normal hesaplama - vize girildi
            else if (vize != null) {
                double vizeKatkisi = vize * 0.40;
                sb.append(String.format("📝 Vize Notunuz: %.2f (Katkı: %.2f puan)\n", vize, vizeKatkisi));

                if (finalNotu != null) {
                    double toplam = vizeKatkisi + (finalNotu * 0.60);
                    String mevcutHarf = getHarfNotu(toplam, ddEsik, dcEsik, ccEsik, cbEsik, bbEsik, baEsik, aaEsik);

                    sb.append(
                            String.format("📝 Final Notunuz: %.2f (Katkı: %.2f puan)\n", finalNotu, finalNotu * 0.60));
                    sb.append(String.format("📊 Toplam Puanınız: %.2f\n", toplam));
                    sb.append(String.format("🎓 Mevcut Harf Notunuz: %s\n\n", mevcutHarf));
                } else {
                    sb.append("📝 Final Notu: Henüz girilmedi\n\n");
                }

                sb.append("───────────────────────────────────────────────\n");
                sb.append("   HER HARF NOTU İÇİN GEREKLİ FİNAL NOTU      \n");
                sb.append("───────────────────────────────────────────────\n\n");

                sb.append(hesaplaGerekliFinnal("DD", ddEsik, vizeKatkisi));
                sb.append(hesaplaGerekliFinnal("DC", dcEsik, vizeKatkisi));
                sb.append(hesaplaGerekliFinnal("CC", ccEsik, vizeKatkisi));
                sb.append(hesaplaGerekliFinnal("CB", cbEsik, vizeKatkisi));
                sb.append(hesaplaGerekliFinnal("BB", bbEsik, vizeKatkisi));
                sb.append(hesaplaGerekliFinnal("BA", baEsik, vizeKatkisi));
                sb.append(hesaplaGerekliFinnal("AA", aaEsik, vizeKatkisi));
            }
            // İkisi de boş
            else {
                sb.append("❌ Lütfen en az bir not girin!\n");
            }

            sb.append("\n═══════════════════════════════════════════════\n");

            sonucArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            sonucArea.setText("❌ Lütfen geçerli sayılar girin!");
        }
    }

    private String hesaplaGerekliFinnal(String harfNotu, double esik, double vizeKatkisi) {
        double gerekliPuan = esik - vizeKatkisi;
        double gerekliFinal = gerekliPuan / 0.60;

        if (gerekliFinal <= 0) {
            return String.format("✅ %s için: Zaten garantilediniz!\n", harfNotu);
        } else if (gerekliFinal > 100) {
            return String.format("❌ %s için: Maalesef ulaşılamaz\n", harfNotu);
        } else {
            return String.format("📌 %s için: Finalden en az %.2f almanız gerekiyor\n", harfNotu, gerekliFinal);
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
