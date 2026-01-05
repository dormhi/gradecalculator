# 📊 Grade Calculator / Not Hesaplama Uygulaması

> ⚠️ **This is a practice project / Bu bir deneme projesidir**

---

## 🇬🇧 English

A modern Java Swing grade calculator application with dark theme UI and internationalization support.

### Features
- **Classic System** - Midterm (40%) & Final (60%) calculation with letter grade analysis
- **Customizable** - Add custom grades with custom weights
- **Russian Style** - Assignment, Quiz, Participation, Midterm, Final system
- **Language Support** - Switch between English and Turkish in real-time

### Tech Stack
- Java Swing
- Custom UI components (ModernButton, ThemeManager)
- Dark theme with hover effects
- Internationalization (i18n) with LanguageManager

### How to Run
```bash
javac -d out src/*.java
java -cp out GradeCalculatorApp
```

### Screenshots
*Coming soon*

---

## 🇹🇷 Türkçe

Modern koyu tema arayüzüne ve çoklu dil desteğine sahip Java Swing not hesaplama uygulaması.

### Özellikler
- **Klasik Sistem** - Vize (%40) & Final (%60) hesaplama ve harf notu analizi
- **Kişiselleştirilir** - Özel notlar ve ağırlıklar ekleyin
- **Rus Usulü** - Ödev, Quiz, Katılım, Vize, Final sistemi
- **Dil Desteği** - Gerçek zamanlı İngilizce ve Türkçe arasında geçiş

### Teknolojiler
- Java Swing
- Özel UI bileşenleri (ModernButton, ThemeManager)
- Hover efektli koyu tema
- Uluslararasılaştırma (i18n) - LanguageManager

### Nasıl Çalıştırılır
```bash
javac -d out src/*.java
java -cp out GradeCalculatorApp
```

---

## 📁 Project Structure / Proje Yapısı

```
src/
├── GradeCalculatorApp.java     # Main menu / Ana menü
├── ClassicSystemFrame.java     # Classic system / Klasik sistem
├── CustomSystemFrame.java      # Customizable / Kişiselleştirilir
├── RussianStyleFrame.java      # Russian style / Rus usulü
├── GradeCalculator.java        # Calculator logic / Hesaplama mantığı
├── LanguageManager.java        # i18n manager / Dil yöneticisi
├── ModernButton.java           # Custom button / Özel buton
├── ThemeManager.java           # Theme manager / Tema yöneticisi
└── ButtonFactory.java          # Button factory / Buton fabrikası
```

---

## 📝 License / Lisans

This project is for educational purposes only.  
Bu proje sadece eğitim amaçlıdır.

---

*Made with ☕ and Java*
