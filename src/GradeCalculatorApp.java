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
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        JPanel langSelector = createLanguageSelector();
        topPanel.add(langSelector);
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

    private JPanel createLanguageSelector() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        container.setOpaque(false);

        // Create the toggle panel with rounded background
        JPanel togglePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Outer container with gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(31, 41, 55),
                        getWidth(), 0, new Color(45, 55, 72));
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Subtle border glow
                g2.setColor(new Color(75, 85, 99, 100));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        togglePanel.setOpaque(false);
        togglePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 2, 0, 2);

        // Turkish option
        JPanel trOption = createLanguageOption("🇹🇷", "TR", LanguageManager.Language.TURKISH);
        gbc.gridx = 0;
        togglePanel.add(trOption, gbc);

        // English option
        JPanel enOption = createLanguageOption("🇬🇧", "EN", LanguageManager.Language.ENGLISH);
        gbc.gridx = 1;
        togglePanel.add(enOption, gbc);

        container.add(togglePanel);
        return container;
    }

    private JPanel createLanguageOption(String flag, String code, LanguageManager.Language language) {
        boolean isSelected = lang.getCurrentLanguage() == language;

        JPanel option = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4)) {
            private boolean hovered = false;
            private float animationProgress = isSelected ? 1f : 0f;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean currentlySelected = lang.getCurrentLanguage() == language;

                if (currentlySelected) {
                    // Selected state - vibrant gradient
                    GradientPaint selectedGradient = new GradientPaint(
                            0, 0, ThemeManager.PRIMARY,
                            getWidth(), getHeight(), new Color(99, 102, 241));
                    g2.setPaint(selectedGradient);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);

                    // Glow effect
                    g2.setColor(new Color(79, 70, 229, 60));
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawRoundRect(-1, -1, getWidth() + 1, getHeight() + 1, 16, 16);
                } else if (hovered) {
                    // Hover state
                    g2.setColor(new Color(55, 65, 81, 180));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);

                    // Subtle border on hover
                    g2.setColor(new Color(107, 114, 128, 100));
                    g2.setStroke(new BasicStroke(1f));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                }

                g2.dispose();
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.hovered = hovered;
                repaint();
            }

            public boolean isHovered() {
                return hovered;
            }
        };

        option.setOpaque(false);
        option.setCursor(new Cursor(Cursor.HAND_CURSOR));
        option.setPreferredSize(new Dimension(70, 36));

        // Flag label with larger emoji
        JLabel flagLabel = new JLabel(flag);
        flagLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        option.add(flagLabel);

        // Language code with modern font
        JLabel codeLabel = new JLabel(code);
        codeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        codeLabel.setForeground(isSelected ? Color.WHITE : ThemeManager.TEXT_SECONDARY);
        option.add(codeLabel);

        // Store reference for updates
        option.putClientProperty("language", language);
        option.putClientProperty("codeLabel", codeLabel);

        // Mouse interactions
        option.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                try {
                    java.lang.reflect.Method setHovered = option.getClass().getMethod("setHovered", boolean.class);
                    setHovered.invoke(option, true);
                } catch (Exception ex) {
                    option.repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                try {
                    java.lang.reflect.Method setHovered = option.getClass().getMethod("setHovered", boolean.class);
                    setHovered.invoke(option, false);
                } catch (Exception ex) {
                    option.repaint();
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (lang.getCurrentLanguage() != language) {
                    lang.setLanguage(language);

                    // Update all language options in parent
                    Container parent = option.getParent();
                    if (parent != null) {
                        for (Component comp : parent.getComponents()) {
                            if (comp instanceof JPanel) {
                                JPanel optionPanel = (JPanel) comp;
                                LanguageManager.Language optLang = (LanguageManager.Language) optionPanel
                                        .getClientProperty("language");
                                JLabel optCodeLabel = (JLabel) optionPanel.getClientProperty("codeLabel");

                                if (optLang != null && optCodeLabel != null) {
                                    boolean selected = optLang == language;
                                    optCodeLabel.setForeground(selected ? Color.WHITE : ThemeManager.TEXT_SECONDARY);
                                }
                                optionPanel.repaint();
                            }
                        }
                    }
                }
            }
        });

        return option;
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
