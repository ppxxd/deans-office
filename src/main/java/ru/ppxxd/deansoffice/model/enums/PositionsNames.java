package ru.ppxxd.deansoffice.model.enums;

public enum PositionsNames {
    TEACHER("Преподаватель"),
    SENIOR_TEACHER("СТАРШИЙ_ПРЕПОДАВАТЕЛЬ"),
    DOCENT("ДОЦЕНТ"),
    PROFESSOR("ПРОФЕССОР");

    private String russianName;

    PositionsNames(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }
}
