import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton language manager for internationalization (i18n)
 * Supports Turkish and English languages
 */
public class LanguageManager {

    public enum Language {
        TURKISH("Türkçe", "🇹🇷"),
        ENGLISH("English", "🇬🇧");

        private final String displayName;
        private final String flag;

        Language(String displayName, String flag) {
            this.displayName = displayName;
            this.flag = flag;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getFlag() {
            return flag;
        }
    }

    private static LanguageManager instance;
    private Language currentLanguage;
    private final Map<String, Map<Language, String>> texts;
    private final List<Runnable> listeners;
    private final Preferences prefs;

    private LanguageManager() {
        texts = new HashMap<>();
        listeners = new ArrayList<>();
        prefs = Preferences.userNodeForPackage(LanguageManager.class);

        // Load saved language preference
        String savedLang = prefs.get("language", "TURKISH");
        currentLanguage = Language.valueOf(savedLang);

        initializeTexts();
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    private void initializeTexts() {
        // App titles
        addText("app_title", "Not Hesaplama Uygulaması", "Grade Calculator App");
        addText("app_subtitle", "Bir hesaplama yöntemi seçin", "Choose a calculation method");
        addText("grade_calculation", "Not Hesaplama", "Grade Calculator");

        // Main menu buttons
        addText("classic_system", "Klasik Sistem", "Classic System");
        addText("customizable", "Kişiselleştirilir", "Customizable");
        addText("russian_style", "Rus Usulü", "Russian Style");

        // Classic System Frame
        addText("classic_title", "Klasik Sistem - Vize/Final", "Classic System - Midterm/Final");
        addText("grade_input", "Not Girişi", "Grade Input");
        addText("midterm_grade", "Vize Notu (%40):", "Midterm Grade (40%):");
        addText("final_grade", "Final Notu (%60):", "Final Grade (60%):");
        addText("letter_thresholds", "Harf Notu Eşikleri", "Letter Grade Thresholds");
        addText("analyze", "Analiz Et", "Analyze");
        addText("result_analysis", "Sonuç ve Analiz", "Result and Analysis");

        // Customizable Frame
        addText("custom_title", "Kişiselleştirilir Sistem", "Customizable System");
        addText("add_grade", "Not Ekle", "Add Grade");
        addText("grade_name", "Not Adı:", "Grade Name:");
        addText("grade", "Not:", "Grade:");
        addText("weight", "Ağırlık(%):", "Weight(%):");
        addText("add", "Ekle", "Add");
        addText("total_weight", "Toplam Ağırlık:", "Total Weight:");
        addText("added_grades", "Eklenen Notlar", "Added Grades");
        addText("clear", "Temizle", "Clear");

        // Russian Style Frame
        addText("russian_title", "Rus Usulü - Not Hesaplama", "Russian Style - Grade Calculation");
        addText("assignment_grade", "Ödev Notu (%15):", "Assignment Grade (15%):");
        addText("quiz_grade", "Quiz Notu (%9):", "Quiz Grade (9%):");
        addText("participation_grade", "Katılım Notu (%15):", "Participation Grade (15%):");
        addText("midterm_russian", "Vize Notu (%21):", "Midterm Grade (21%):");
        addText("final_russian", "Final Notu (%40):", "Final Grade (40%):");
        addText("calculate", "Hesapla", "Calculate");
        addText("result", "Sonuç", "Result");
        addText("waiting_result", "Sonuç bekleniyor...", "Waiting for result...");

        // Analysis texts
        addText("status_analysis", "DURUM ANALİZİ", "STATUS ANALYSIS");
        addText("your_midterm", "Vize Notunuz:", "Your Midterm:");
        addText("your_final", "Final Notunuz:", "Your Final:");
        addText("total_score", "Toplam Puanınız:", "Your Total Score:");
        addText("current_letter", "Mevcut Harf Notunuz:", "Your Current Letter Grade:");
        addText("required_final", "HER HARF NOTU İÇİN GEREKLİ FİNAL NOTU", "REQUIRED FINAL GRADE FOR EACH LETTER");
        addText("guaranteed", "Zaten garantilediniz!", "Already guaranteed!");
        addText("unreachable", "Maalesef ulaşılamaz", "Unfortunately unreachable");
        addText("need_minimum", "Finalden en az %.2f almanız gerekiyor", "You need at least %.2f from final");
        addText("midterm_not_entered", "Vize notu girilmedi - Senaryo Analizi",
                "Midterm not entered - Scenario Analysis");
        addText("final_not_entered", "Final Notu: Henüz girilmedi", "Final Grade: Not entered yet");
        addText("midterm_scenarios", "VİZE NOTUNA GÖRE HARF NOTU SENARYOLARI", "LETTER GRADE SCENARIOS BY MIDTERM");
        addText("if_midterm_was", "Vize %d olsaydı → Toplam: %.2f → %s", "If midterm was %d → Total: %.2f → %s");
        addText("contribution", "Katkı:", "Contribution:");
        addText("points", "puan", "points");

        // Customizable analysis
        addText("current_contribution", "Mevcut Katkınız:", "Your Current Contribution:");
        addText("used_weight", "Kullanılan Ağırlık:", "Used Weight:");
        addText("remaining_weight", "Kalan Ağırlık:", "Remaining Weight:");
        addText("your_total", "Toplam Puanınız:", "Your Total Score:");
        addText("your_letter", "Harf Notunuz:", "Your Letter Grade:");
        addText("all_grades_entered", "Tüm notlarınız girilmiş!", "All grades entered!");
        addText("required_avg_remaining", "KALAN %%%.1f İÇİN GEREKLİ ORTALAMA NOT",
                "REQUIRED AVG FOR REMAINING %.1f%%");
        addText("need_avg", "Kalan notlardan ortalama %.2f almanız gerekiyor",
                "You need avg %.2f from remaining grades");

        // Error messages
        addText("error_invalid_grade", "Notlar 0-100 arasında olmalıdır!", "Grades must be between 0-100!");
        addText("error_valid_numbers", "Lütfen geçerli sayılar girin!", "Please enter valid numbers!");
        addText("error_midterm_range", "Vize notu 0-100 arasında olmalıdır!", "Midterm must be between 0-100!");
        addText("error_final_range", "Final notu 0-100 arasında olmalıdır!", "Final must be between 0-100!");
        addText("error_enter_grade", "Lütfen en az bir not girin!", "Please enter at least one grade!");
        addText("error_add_grade", "Lütfen en az bir not ekleyin!", "Please add at least one grade!");
        addText("error_weight_range", "Ağırlık 0-100 arasında olmalıdır!", "Weight must be between 0-100!");
        addText("error_weight_exceed", "Toplam ağırlık %100'ü geçemez!", "Total weight cannot exceed 100%!");
        addText("error_threshold", "Lütfen eşik değerlerini doğru girin!", "Please enter threshold values correctly!");
        addText("error", "Hata", "Error");

        // Misc
        addText("version", "v2.0 - Modern UI", "v2.0 - Modern UI");
        addText("failed", "Kaldınız", "Failed");
    }

    private void addText(String key, String turkish, String english) {
        Map<Language, String> translations = new HashMap<>();
        translations.put(Language.TURKISH, turkish);
        translations.put(Language.ENGLISH, english);
        texts.put(key, translations);
    }

    public String get(String key) {
        Map<Language, String> translations = texts.get(key);
        if (translations == null) {
            return "[" + key + "]"; // Return key if not found for debugging
        }
        return translations.get(currentLanguage);
    }

    public String get(String key, Object... args) {
        String template = get(key);
        return String.format(template, args);
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public void setLanguage(Language language) {
        if (this.currentLanguage != language) {
            this.currentLanguage = language;
            prefs.put("language", language.name());
            notifyListeners();
        }
    }

    public void toggleLanguage() {
        setLanguage(currentLanguage == Language.TURKISH ? Language.ENGLISH : Language.TURKISH);
    }

    public void addLanguageChangeListener(Runnable listener) {
        listeners.add(listener);
    }

    public void removeLanguageChangeListener(Runnable listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}
