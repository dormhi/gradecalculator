import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Russian Style Frame - Modern UI design
 */
public class RussianStyleFrame extends JFrame implements ActionListener {
    private JTextField assignmentField, quizField, participationField, midtermField, finalField;
    private JLabel resultLabel;
    private final LanguageManager lang = LanguageManager.getInstance();

    // Labels for language updates
    private JLabel titleLabel, assignmentLabel, quizLabel, participationLabel, midtermLabel, finalLabel;
    private ModernButton calculateButton;

    public RussianStyleFrame() {
        initializeUI();
        setupLanguageListener();
    }

    private void initializeUI() {
        setTitle(lang.get("russian_title"));
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(ThemeManager.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        titleLabel = ThemeManager.createTitleLabel("🎓 " + lang.get("russian_style"));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ThemeManager.BG_DARK);

        // Grade input card
        JPanel gradeCard = createCard(lang.get("grade_input"));
        gradeCard.setLayout(new GridLayout(5, 2, 15, 12));

        assignmentLabel = ThemeManager.createLabel(lang.get("assignment_grade"));
        gradeCard.add(assignmentLabel);
        assignmentField = ThemeManager.createStyledTextField();
        gradeCard.add(assignmentField);

        quizLabel = ThemeManager.createLabel(lang.get("quiz_grade"));
        gradeCard.add(quizLabel);
        quizField = ThemeManager.createStyledTextField();
        gradeCard.add(quizField);

        participationLabel = ThemeManager.createLabel(lang.get("participation_grade"));
        gradeCard.add(participationLabel);
        participationField = ThemeManager.createStyledTextField();
        gradeCard.add(participationField);

        midtermLabel = ThemeManager.createLabel(lang.get("midterm_russian"));
        gradeCard.add(midtermLabel);
        midtermField = ThemeManager.createStyledTextField();
        gradeCard.add(midtermField);

        finalLabel = ThemeManager.createLabel(lang.get("final_russian"));
        gradeCard.add(finalLabel);
        finalField = ThemeManager.createStyledTextField();
        gradeCard.add(finalField);

        contentPanel.add(gradeCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Calculate button
        calculateButton = new ModernButton("🔍  " + lang.get("calculate"), ThemeManager.ACCENT);
        calculateButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        calculateButton.addActionListener(this);
        contentPanel.add(calculateButton);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Result card
        JPanel resultCard = createCard(lang.get("result"));
        resultCard.setLayout(new BorderLayout());
        resultLabel = new JLabel(lang.get("waiting_result"), SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultLabel.setForeground(ThemeManager.TEXT_PRIMARY);
        resultCard.add(resultLabel, BorderLayout.CENTER);
        resultCard.setPreferredSize(new Dimension(480, 60));
        contentPanel.add(resultCard);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void setupLanguageListener() {
        lang.addLanguageChangeListener(() -> {
            setTitle(lang.get("russian_title"));
            titleLabel.setText("🎓 " + lang.get("russian_style"));
            assignmentLabel.setText(lang.get("assignment_grade"));
            quizLabel.setText(lang.get("quiz_grade"));
            participationLabel.setText(lang.get("participation_grade"));
            midtermLabel.setText(lang.get("midterm_russian"));
            finalLabel.setText(lang.get("final_russian"));
            calculateButton.setText("🔍  " + lang.get("calculate"));
            if (resultLabel.getText().contains("bekleniyor") || resultLabel.getText().contains("Waiting")) {
                resultLabel.setText(lang.get("waiting_result"));
            }
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
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        return card;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double assignment = Double.parseDouble(assignmentField.getText());
            double quiz = Double.parseDouble(quizField.getText());
            double participation = Double.parseDouble(participationField.getText());
            double midterm = Double.parseDouble(midtermField.getText());
            double finalGrade = Double.parseDouble(finalField.getText());

            if (!GradeCalculator.isValidGrade(assignment) || !GradeCalculator.isValidGrade(quiz) ||
                    !GradeCalculator.isValidGrade(participation) || !GradeCalculator.isValidGrade(midterm) ||
                    !GradeCalculator.isValidGrade(finalGrade)) {
                resultLabel.setText("❌ " + lang.get("error_invalid_grade"));
                resultLabel.setForeground(ThemeManager.DANGER);
                return;
            }

            double total = GradeCalculator.calculateClassicSystem(assignment, quiz, participation, midterm, finalGrade);
            String letterGrade = GradeCalculator.determineLetterGrade(total);

            resultLabel.setText("🎓 " + letterGrade + " (Total: " + String.format("%.2f", total) + ")");

            // Color based on result
            if (total < 30) {
                resultLabel.setForeground(ThemeManager.DANGER);
            } else if (total < 50) {
                resultLabel.setForeground(ThemeManager.WARNING);
            } else {
                resultLabel.setForeground(ThemeManager.SECONDARY);
            }

        } catch (NumberFormatException ex) {
            resultLabel.setText("❌ " + lang.get("error_valid_numbers"));
            resultLabel.setForeground(ThemeManager.DANGER);
        }
    }
}
