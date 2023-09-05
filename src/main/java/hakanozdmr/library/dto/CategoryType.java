package hakanozdmr.library.dto;

public enum CategoryType {
    RESEARCH_HISTORY("Arastirma - Tarih"),
    SCIENCE("Bilim"),
    COMIC("Çizgi Roman"),
    CHILD_AND_YOUTH("Çocuk ve Gençlik"),
    LESSON_TEST_BOOKS("Ders / Sinav Kitaplari"),
    RELIGION("Din Tasavvuf"),
    LITERATURE("Edebiyat"),
    EDUCATION_REFERENCE("Egitim Basvuru"),
    PHILOSOPHY("Felsefe"),
    FOREING_LANGUAGES("Foreign Languages"),
    HOBBY("Hobi"),
    MYTH_LEGEND("Mitoloji Efsane"),
    HUMOR("Mizah"),
    PRESTIGE_BOOK("Prestij Kitaplari"),
    ART_DESING("Sanat - Tasarim"),
    AUDI0_BOOKS("Sesli Kitaplar"),
    OTHER("Diger");

    private final String value;

    CategoryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
