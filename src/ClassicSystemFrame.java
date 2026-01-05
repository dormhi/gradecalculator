import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classic System Frame - Modern UI with Midterm/Final system
 */
public class ClassicSystemFrame extends JFrame implements ActionListener {
    private JTextField midtermField, finalField;
    private JTextField ddField, dcField, ccField, cbField, bbField, baField, aaField;
    private JTextArea resultArea;
    private final LanguageManager lang = LanguageManager.getInstance();

    // Labels that need updating
    private JLabel titleLabel, midtermLabel, finalLabel;
    private ModernButton analyzeButton;

    private static final double DEFAULT_DD = 30;
    private static final double DEFAULT_DC = 35;
    private static final double DEFAULT_CC = 40;
    private static final double DEFAULT_CB = 50;
    private static final double DEFAULT_BB = 60;
    private static final double DEFAULT_BA = 70;
    private static final double DEFAULT_AA = 80;

    public ClassicSystemFrame() {
        initializeUI();
        setupLanguageListener();
    }

    private void initializeUI() {
        setTitle(lang.get("classic_title"));
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(ThemeManager.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ThemeManager.BG_DARK);
        titleLabel = ThemeManager.createTitleLabel("📚 " + lang.get("classic_system"));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ThemeManager.BG_DARK);

        // Grade input card
        JPanel gradeCard = createCard(lang.get("grade_input"));
        gradeCard.setLayout(new GridLayout(2, 2, 15, 10));

        midtermLabel = ThemeManager.createLabel(lang.get("midterm_grade"));
        gradeCard.add(midtermLabel);
        midtermField = ThemeManager.createStyledTextField();
        gradeCard.add(midtermField);

        finalLabel = ThemeManager.createLabel(lang.get("final_grade"));
        gradeCard.add(finalLabel);
        finalField = ThemeManager.createStyledTextField();
        gradeCard.add(finalField);

        contentPanel.add(gradeCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Threshold card
        JPanel thresholdCard = createCard(lang.get("letter_thresholds"));
        thresholdCard.setLayout(new GridLayout(2, 7, 8, 8));

        String[] letters = { "DD", "DC", "CC", "CB", "BB", "BA", "AA" };
        double[] defaults = { DEFAULT_DD, DEFAULT_DC, DEFAULT_CC, DEFAULT_CB, DEFAULT_BB, DEFAULT_BA, DEFAULT_AA };

        for (String letter : letters) {
            JLabel label = new JLabel(letter, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setForeground(ThemeManager.TEXT_PRIMARY);
            thresholdCard.add(label);
        }

        ddField = createSmallTextField(defaults[0]);
        thresholdCard.add(ddField);
        dcField = createSmallTextField(defaults[1]);
        thresholdCard.add(dcField);
        ccField = createSmallTextField(defaults[2]);
        thresholdCard.add(ccField);
        cbField = createSmallTextField(defaults[3]);
        thresholdCard.add(cbField);
        bbField = createSmallTextField(defaults[4]);
        thresholdCard.add(bbField);
        baField = createSmallTextField(defaults[5]);
        thresholdCard.add(baField);
        aaField = createSmallTextField(defaults[6]);
        thresholdCard.add(aaField);

        contentPanel.add(thresholdCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Analyze button
        analyzeButton = new ModernButton("🔍  " + lang.get("analyze"), ThemeManager.PRIMARY);
        analyzeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        analyzeButton.addActionListener(this);
        contentPanel.add(analyzeButton);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Result area
        resultArea = ThemeManager.createResultArea();
        JScrollPane scrollPane = ThemeManager.createStyledScrollPane(resultArea, lang.get("result_analysis"));
        scrollPane.setPreferredSize(new Dimension(580, 180));
        contentPanel.add(scrollPane);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void setupLanguageListener() {
        lang.addLanguageChangeListener(() -> {
            setTitle(lang.get("classic_title"));
            titleLabel.setText("📚 " + lang.get("classic_system"));
            midtermLabel.setText(lang.get("midterm_grade"));
            finalLabel.setText(lang.get("final_grade"));
            analyzeButton.setText("🔍  " + lang.get("analyze"));
        });
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
            String midtermText = midtermField.getText().trim();
            String finalText = finalField.getText().trim();

            Double midterm = midtermText.isEmpty() ? null : Double.parseDouble(midtermText);
            Double finalGrade = finalText.isEmpty() ? null : Double.parseDouble(finalText);

            double ddThreshold = Double.parseDouble(ddField.getText().trim());
            double dcThreshold = Double.parseDouble(dcField.getText().trim());
            double ccThreshold = Double.parseDouble(ccField.getText().trim());
            double cbThreshold = Double.parseDouble(cbField.getText().trim());
            double bbThreshold = Double.parseDouble(bbField.getText().trim());
            double baThreshold = Double.parseDouble(baField.getText().trim());
            double aaThreshold = Double.parseDouble(aaField.getText().trim());

            if (midterm != null && !GradeCalculator.isValidGrade(midterm)) {
                resultArea.setText("❌ " + lang.get("error_midterm_range"));
                return;
            }

            if (finalGrade != null && !GradeCalculator.isValidGrade(finalGrade)) {
                resultArea.setText("❌ " + lang.get("error_final_range"));
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════════\n");
            sb.append("                  ").append(lang.get("status_analysis")).append("                \n");
            sb.append("═══════════════════════════════════════════════\n\n");

            // Midterm not entered but final entered - scenario analysis
            if (midterm == null && finalGrade != null) {
                sb.append("⚠️ ").append(lang.get("midterm_not_entered")).append("\n");
                sb.append(String.format("📝 %s %.2f\n\n", lang.get("your_final"), finalGrade));

                sb.append("───────────────────────────────────────────────\n");
                sb.append("   ").append(lang.get("midterm_scenarios")).append("     \n");
                sb.append("───────────────────────────────────────────────\n\n");

                int[] midtermScenarios = { 0, 20, 30, 40, 50, 60, 70, 80, 90, 100 };
                for (int midtermScenario : midtermScenarios) {
                    double total = (midtermScenario * 0.40) + (finalGrade * 0.60);
                    String letter = getLetterGrade(total, ddThreshold, dcThreshold, ccThreshold, cbThreshold,
                            bbThreshold, baThreshold, aaThreshold);
                    sb.append(String.format("📌 %s\n", lang.get("if_midterm_was", midtermScenario, total, letter)));
                }
            }
            // Normal calculation - midterm entered
            else if (midterm != null) {
                double midtermContribution = midterm * 0.40;
                sb.append(String.format("📝 %s %.2f (%s %.2f %s)\n", lang.get("your_midterm"), midterm,
                        lang.get("contribution"), midtermContribution, lang.get("points")));

                if (finalGrade != null) {
                    double total = midtermContribution + (finalGrade * 0.60);
                    String currentLetter = getLetterGrade(total, ddThreshold, dcThreshold, ccThreshold, cbThreshold,
                            bbThreshold, baThreshold, aaThreshold);

                    sb.append(String.format("📝 %s %.2f (%s %.2f %s)\n", lang.get("your_final"), finalGrade,
                            lang.get("contribution"), finalGrade * 0.60, lang.get("points")));
                    sb.append(String.format("📊 %s %.2f\n", lang.get("total_score"), total));
                    sb.append(String.format("🎓 %s %s\n\n", lang.get("current_letter"), currentLetter));
                } else {
                    sb.append("📝 ").append(lang.get("final_not_entered")).append("\n\n");
                }

                sb.append("───────────────────────────────────────────────\n");
                sb.append("   ").append(lang.get("required_final")).append("      \n");
                sb.append("───────────────────────────────────────────────\n\n");

                sb.append(calculateRequiredFinal("DD", ddThreshold, midtermContribution));
                sb.append(calculateRequiredFinal("DC", dcThreshold, midtermContribution));
                sb.append(calculateRequiredFinal("CC", ccThreshold, midtermContribution));
                sb.append(calculateRequiredFinal("CB", cbThreshold, midtermContribution));
                sb.append(calculateRequiredFinal("BB", bbThreshold, midtermContribution));
                sb.append(calculateRequiredFinal("BA", baThreshold, midtermContribution));
                sb.append(calculateRequiredFinal("AA", aaThreshold, midtermContribution));
            }
            // Both empty
            else {
                sb.append("❌ ").append(lang.get("error_enter_grade")).append("\n");
            }

            sb.append("\n═══════════════════════════════════════════════\n");

            resultArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            resultArea.setText("❌ " + lang.get("error_valid_numbers"));
        }
    }

    private String calculateRequiredFinal(String letterGrade, double threshold, double midtermContribution) {
        double requiredPoints = threshold - midtermContribution;
        double requiredFinal = requiredPoints / 0.60;

        if (requiredFinal <= 0) {
            return String.format("✅ %s: %s\n", letterGrade, lang.get("guaranteed"));
        } else if (requiredFinal > 100) {
            return String.format("❌ %s: %s\n", letterGrade, lang.get("unreachable"));
        } else {
            return String.format("📌 %s: %s\n", letterGrade, lang.get("need_minimum", requiredFinal));
        }
    }

    private String getLetterGrade(double total, double dd, double dc, double cc, double cb, double bb, double ba,
            double aa) {
        if (total >= aa)
            return "AA";
        if (total >= ba)
            return "BA";
        if (total >= bb)
            return "BB";
        if (total >= cb)
            return "CB";
        if (total >= cc)
            return "CC";
        if (total >= dc)
            return "DC";
        if (total >= dd)
            return "DD";
        return "FF (" + lang.get("failed") + ")";
    }
}
