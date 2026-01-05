/**
 * Not hesaplama mantığı için utility sınıfı
 * Tüm hesaplama işlemleri burada yapılır
 */
public class NotHesaplayici {

    /**
     * Ağırlıklı ortalama hesaplar
     * 
     * @param notlar     Not listesi
     * @param agirliklar Ağırlık listesi (yüzde olarak, örn: 15 = %15)
     * @return Hesaplanan ağırlıklı ortalama
     */
    public static double agirlikliOrtalamaHesapla(double[] notlar, double[] agirliklar) {
        if (notlar.length != agirliklar.length) {
            throw new IllegalArgumentException("Not ve ağırlık sayıları eşit olmalıdır!");
        }

        double toplam = 0;
        for (int i = 0; i < notlar.length; i++) {
            toplam += notlar[i] * (agirliklar[i] / 100);
        }
        return toplam;
    }

    /**
     * Klasik sistem için ağırlıklı ortalama hesaplar
     * Ağırlıklar: Ödev %15, Quiz %9, Katılım %15, Vize %21, Final %40
     */
    public static double klasikSistemHesapla(double odev, double quiz, double katilim, double vize, double finalNot) {
        return (odev * 0.15) + (quiz * 0.09) + (katilim * 0.15) + (vize * 0.21) + (finalNot * 0.40);
    }

    /**
     * Toplam puana göre harf notu belirler
     * 
     * @param toplam Hesaplanan toplam puan
     * @return Harf notu ve durum bilgisi
     */
    public static String harfNotuBelirle(double toplam) {
        if (toplam < 30) {
            return "Fail FF";
        } else if (toplam >= 30 && toplam < 35) {
            return "DC-DD";
        } else if (toplam >= 35 && toplam < 40) {
            return "CC";
        } else if (toplam >= 40 && toplam < 50) {
            return "CB";
        } else if (toplam >= 50 && toplam < 60) {
            return "BB";
        } else if (toplam >= 60 && toplam < 70) {
            return "BA";
        } else if (toplam >= 70 && toplam < 80) {
            return "AA-";
        } else {
            return "AA";
        }
    }

    /**
     * Not geçerli mi kontrol eder (0-100 arasında)
     */
    public static boolean notGecerliMi(double not) {
        return not >= 0 && not <= 100;
    }

    /**
     * Ağırlık geçerli mi kontrol eder (0-100 arasında)
     */
    public static boolean agirlikGecerliMi(double agirlik) {
        return agirlik >= 0 && agirlik <= 100;
    }
}
