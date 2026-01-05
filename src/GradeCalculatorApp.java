import javax.swing.*;
import java.awt.*;

/**
 * Main application class - Modern UI design
 * Grade Calculator Application
 */
public class GradeCalculatorApp extends JFrame {

    private final LanguageManager lang = LanguageManager.getInstance();
    private JLabel titleLabel, subtitleLabel, footerLabel;
    private ModernButton classicButton, customButton, russianButton;
    private JPanel mainPanel;

    public GradeCalculatorApp() {
        initializeUI();
        setupLanguageListener();
    }

    private void initializeUI() {
        setTitle(lang.get("app_title"));
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel - dark theme
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ThemeManager.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));

        // Language toggle button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(ThemeManager.BG_DARK);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton langButton = createLanguageButton();
        topPanel.add(langButton);
        mainPanel.add(topPanel);

        // Logo/Icon area
        JLabel iconLabel = new JLabel("📊", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(iconLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Title
        titleLabel = ThemeManager.createTitleLabel(lang.get("grade_calculation"));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Subtitle
        subtitleLabel = ThemeManager.createSubtitleLabel(lang.get("app_subtitle"));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(ThemeManager.BG_DARK);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Classic System Button
        classicButton = new ModernButton("📚  " + lang.get("classic_system"), ThemeManager.PRIMARY);
        classicButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        classicButton.setMaximumSize(new Dimension(280, 50));
        classicButton.setPreferredSize(new Dimension(280, 50));
        classicButton.addActionListener(e -> new ClassicSystemFrame());
        buttonPanel.add(classicButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Customizable Button
        customButton = new ModernButton("⚙️  " + lang.get("customizable"), ThemeManager.SECONDARY);
        customButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customButton.setMaximumSize(new Dimension(280, 50));
        customButton.setPreferredSize(new Dimension(280, 50));
        customButton.addActionListener(e -> new CustomSystemFrame());
        buttonPanel.add(customButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Russian Style Button
        russianButton = new ModernButton("🎓  " + lang.get("russian_style"), ThemeManager.ACCENT);
        russianButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        russianButton.setMaximumSize(new Dimension(280, 50));
        russianButton.setPreferredSize(new Dimension(280, 50));
        russianButton.addActionListener(e -> new RussianStyleFrame());
        buttonPanel.add(russianButton);

        mainPanel.add(buttonPanel);

        mainPanel.add(Box.createVerticalGlue());

        // Footer
        footerLabel = new JLabel(lang.get("version"), SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(107, 114, 128));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(footerLabel);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createLanguageButton() {
        String flag = lang.getCurrentLanguage().getFlag();
        JButton button = new JButton(flag) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        button.setBackground(ThemeManager.BG_CARD);
        button.setForeground(ThemeManager.TEXT_PRIMARY);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(lang.getCurrentLanguage() == LanguageManager.Language.TURKISH
                ? "Switch to English"
                : "Türkçe'ye geç");

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalBg = ThemeManager.BG_CARD;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(ThemeManager.PRIMARY);
                button.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(originalBg);
                button.repaint();
            }
        });

        button.addActionListener(e -> {
            lang.toggleLanguage();
            button.setText(lang.getCurrentLanguage().getFlag());
            button.setToolTipText(lang.getCurrentLanguage() == LanguageManager.Language.TURKISH
                    ? "Switch to English"
                    : "Türkçe'ye geç");
        });

        return button;
    }

    private void setupLanguageListener() {
        lang.addLanguageChangeListener(() -> {
            // Update window title
            setTitle(lang.get("app_title"));

            // Update labels
            titleLabel.setText(lang.get("grade_calculation"));
            subtitleLabel.setText(lang.get("app_subtitle"));
            footerLabel.setText(lang.get("version"));

            // Update buttons
            classicButton.setText("📚  " + lang.get("classic_system"));
            customButton.setText("⚙️  " + lang.get("customizable"));
            russianButton.setText("🎓  " + lang.get("russian_style"));
        });
    }

    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new GradeCalculatorApp());
    }
}
