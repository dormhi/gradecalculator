import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom System Frame - Modern UI design
 */
public class CustomSystemFrame extends JFrame implements ActionListener {
    private JTextField gradeField, weightField, gradeNameField;
    private JTextField ddField, dcField, ccField, cbField, bbField, baField, aaField;
    private JTextArea gradesArea, resultArea;
    private JLabel totalWeightLabel;
    private List<Double> grades = new ArrayList<>();
    private List<Double> weights = new ArrayList<>();
    private List<String> gradeNames = new ArrayList<>();
    private final LanguageManager lang = LanguageManager.getInstance();

    // Labels for language updates
    private JLabel titleLabel, gradeNameLabel, gradeLabel, weightLabel;
    private ModernButton addButton, analyzeButton, clearButton;

    private static final double DEFAULT_DD = 30;
    private static final double DEFAULT_DC = 35;
    private static final double DEFAULT_CC = 40;
    private static final double DEFAULT_CB = 50;
    private static final double DEFAULT_BB = 60;
    private static final double DEFAULT_BA = 70;
    private static final double DEFAULT_AA = 80;

    public CustomSystemFrame() {
        initializeUI();
        setupLanguageListener();
    }

    private void initializeUI() {
        setTitle(lang.get("custom_title"));
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(ThemeManager.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        titleLabel = ThemeManager.createTitleLabel("⚙️ " + lang.get("customizable"));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ThemeManager.BG_DARK);

        // Add grade card
        JPanel addCard = createCard(lang.get("add_grade"));
        addCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gradeNameLabel = ThemeManager.createLabel(lang.get("grade_name"));
        addCard.add(gradeNameLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gradeNameField = ThemeManager.createStyledTextField();
        addCard.add(gradeNameField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gradeLabel = ThemeManager.createLabel(lang.get("grade"));
        addCard.add(gradeLabel, gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        gradeField = ThemeManager.createStyledTextField();
        addCard.add(gradeField, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0;
        weightLabel = ThemeManager.createLabel(lang.get("weight"));
        addCard.add(weightLabel, gbc);
        gbc.gridx = 5;
        gbc.weightx = 0.5;
        weightField = ThemeManager.createStyledTextField();
        addCard.add(weightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        addButton = new ModernButton("➕ " + lang.get("add"), ThemeManager.SECONDARY);
        addButton.setPreferredSize(new Dimension(120, 35));
        addButton.addActionListener(e -> addGrade());
        addCard.add(addButton, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 3;
        totalWeightLabel = new JLabel(lang.get("total_weight") + " %0");
        totalWeightLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        totalWeightLabel.setForeground(ThemeManager.SECONDARY);
        addCard.add(totalWeightLabel, gbc);

        contentPanel.add(addCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

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
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Added grades
        gradesArea = ThemeManager.createResultArea();
        gradesArea.setRows(4);
        JScrollPane gradesScroll = ThemeManager.createStyledScrollPane(gradesArea, lang.get("added_grades"));
        gradesScroll.setPreferredSize(new Dimension(640, 100));
        contentPanel.add(gradesScroll);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(ThemeManager.BG_DARK);

        analyzeButton = new ModernButton("🔍 " + lang.get("analyze"), ThemeManager.PRIMARY);
        analyzeButton.setPreferredSize(new Dimension(150, 40));
        analyzeButton.addActionListener(this);
        buttonPanel.add(analyzeButton);

        clearButton = new ModernButton("🗑️ " + lang.get("clear"), ThemeManager.DANGER);
        clearButton.setPreferredSize(new Dimension(150, 40));
        clearButton.addActionListener(e -> clearAll());
        buttonPanel.add(clearButton);

        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Result
        resultArea = ThemeManager.createResultArea();
        JScrollPane resultScroll = ThemeManager.createStyledScrollPane(resultArea, lang.get("result_analysis"));
        resultScroll.setPreferredSize(new Dimension(640, 150));
        contentPanel.add(resultScroll);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void setupLanguageListener() {
        lang.addLanguageChangeListener(() -> {
            setTitle(lang.get("custom_title"));
            titleLabel.setText("⚙️ " + lang.get("customizable"));
            gradeNameLabel.setText(lang.get("grade_name"));
            gradeLabel.setText(lang.get("grade"));
            weightLabel.setText(lang.get("weight"));
            addButton.setText("➕ " + lang.get("add"));
            analyzeButton.setText("🔍 " + lang.get("analyze"));
            clearButton.setText("🗑️ " + lang.get("clear"));
            updateTotalWeightLabel();
        });
    }

    private void updateTotalWeightLabel() {
        double total = weights.stream().mapToDouble(Double::doubleValue).sum();
        totalWeightLabel.setText(lang.get("total_weight") + " %" + total);
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

    private void addGrade() {
        try {
            String gradeName = gradeNameField.getText().trim();
            if (gradeName.isEmpty()) {
                gradeName = "Grade " + (grades.size() + 1);
            }

            double grade = Double.parseDouble(gradeField.getText());
            double weight = Double.parseDouble(weightField.getText());

            if (!GradeCalculator.isValidGrade(grade)) {
                JOptionPane.showMessageDialog(this, lang.get("error_invalid_grade"), lang.get("error"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!GradeCalculator.isValidWeight(weight)) {
                JOptionPane.showMessageDialog(this, lang.get("error_weight_range"), lang.get("error"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            double currentTotal = weights.stream().mapToDouble(Double::doubleValue).sum();
            if (currentTotal + weight > 100) {
                JOptionPane.showMessageDialog(this, lang.get("error_weight_exceed"), lang.get("error"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            grades.add(grade);
            weights.add(weight);
            gradeNames.add(gradeName);

            gradesArea.append(String.format("%-15s %s %6.2f  %s %%%5.1f%n", gradeName, lang.get("grade"), grade,
                    lang.get("weight"), weight));
            updateTotalWeightLabel();

            gradeNameField.setText("");
            gradeField.setText("");
            weightField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, lang.get("error_valid_numbers"), lang.get("error"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearAll() {
        grades.clear();
        weights.clear();
        gradeNames.clear();
        gradesArea.setText("");
        updateTotalWeightLabel();
        resultArea.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (grades.isEmpty()) {
            resultArea.setText("❌ " + lang.get("error_add_grade"));
            return;
        }

        try {
            double ddThreshold = Double.parseDouble(ddField.getText().trim());
            double dcThreshold = Double.parseDouble(dcField.getText().trim());
            double ccThreshold = Double.parseDouble(ccField.getText().trim());
            double cbThreshold = Double.parseDouble(cbField.getText().trim());
            double bbThreshold = Double.parseDouble(bbField.getText().trim());
            double baThreshold = Double.parseDouble(baField.getText().trim());
            double aaThreshold = Double.parseDouble(aaField.getText().trim());

            double currentTotal = 0;
            double totalWeight = 0;

            for (int i = 0; i < grades.size(); i++) {
                currentTotal += grades.get(i) * (weights.get(i) / 100);
                totalWeight += weights.get(i);
            }

            double remainingWeight = 100 - totalWeight;

            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════════\n");
            sb.append("                  ").append(lang.get("status_analysis")).append("                \n");
            sb.append("═══════════════════════════════════════════════\n\n");

            sb.append(String.format("📊 %s %.2f %s\n", lang.get("current_contribution"), currentTotal,
                    lang.get("points")));
            sb.append(String.format("📊 %s %%%.1f\n", lang.get("used_weight"), totalWeight));
            sb.append(String.format("📊 %s %%%.1f\n\n", lang.get("remaining_weight"), remainingWeight));

            if (remainingWeight == 0) {
                String currentLetter = getLetterGrade(currentTotal, ddThreshold, dcThreshold, ccThreshold, cbThreshold,
                        bbThreshold, baThreshold, aaThreshold);
                sb.append(String.format("🎓 %s %.2f\n", lang.get("your_total"), currentTotal));
                sb.append(String.format("🎓 %s %s\n\n", lang.get("your_letter"), currentLetter));
                sb.append("✅ ").append(lang.get("all_grades_entered")).append("\n");
            } else {
                sb.append("───────────────────────────────────────────────\n");
                sb.append(String.format("   %s    \n", lang.get("required_avg_remaining", remainingWeight)));
                sb.append("───────────────────────────────────────────────\n\n");

                sb.append(calculateRequiredGrade("DD", ddThreshold, currentTotal, remainingWeight));
                sb.append(calculateRequiredGrade("DC", dcThreshold, currentTotal, remainingWeight));
                sb.append(calculateRequiredGrade("CC", ccThreshold, currentTotal, remainingWeight));
                sb.append(calculateRequiredGrade("CB", cbThreshold, currentTotal, remainingWeight));
                sb.append(calculateRequiredGrade("BB", bbThreshold, currentTotal, remainingWeight));
                sb.append(calculateRequiredGrade("BA", baThreshold, currentTotal, remainingWeight));
                sb.append(calculateRequiredGrade("AA", aaThreshold, currentTotal, remainingWeight));
            }

            sb.append("\n═══════════════════════════════════════════════\n");

            resultArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            resultArea.setText("❌ " + lang.get("error_threshold"));
        }
    }

    private String calculateRequiredGrade(String letterGrade, double threshold, double currentTotal,
            double remainingWeight) {
        double requiredPoints = threshold - currentTotal;
        double requiredGrade = requiredPoints / (remainingWeight / 100);

        if (requiredGrade <= 0) {
            return String.format("✅ %s: %s\n", letterGrade, lang.get("guaranteed"));
        } else if (requiredGrade > 100) {
            return String.format("❌ %s: %s\n", letterGrade, lang.get("unreachable"));
        } else {
            return String.format("📌 %s: %s\n", letterGrade, lang.get("need_avg", requiredGrade));
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
