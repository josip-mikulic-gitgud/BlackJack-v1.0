package com.example.blackjack;

public enum Karte {

    // Herz (♥)
    HERZ_2("♥ 2", 2),
    HERZ_3("♥ 3", 3),
    HERZ_4("♥ 4", 4),
    HERZ_5("♥ 5", 5),
    HERZ_6("♥ 6", 6),
    HERZ_7("♥ 7", 7),
    HERZ_8("♥ 8", 8),
    HERZ_9("♥ 9", 9),
    HERZ_10("♥ 10", 10),
    HERZ_BUBE("♥ J", 10),
    HERZ_DAME("♥ Q", 10),
    HERZ_KOENIG("♥ K", 10),
    HERZ_AS("♥ A", 11),

    // Karo (♦)
    KARO_2("♦ 2", 2),
    KARO_3("♦ 3", 3),
    KARO_4("♦ 4", 4),
    KARO_5("♦ 5", 5),
    KARO_6("♦ 6", 6),
    KARO_7("♦ 7", 7),
    KARO_8("♦ 8", 8),
    KARO_9("♦ 9", 9),
    KARO_10("♦ 10", 10),
    KARO_BUBE("♦ J", 10),
    KARO_DAME("♦ Q", 10),
    KARO_KOENIG("♦ K", 10),
    KARO_AS("♦ A", 11),

    // Kreuz (♣)
    KREUZ_2("♣ 2", 2),
    KREUZ_3("♣ 3", 3),
    KREUZ_4("♣ 4", 4),
    KREUZ_5("♣ 5", 5),
    KREUZ_6("♣ 6", 6),
    KREUZ_7("♣ 7", 7),
    KREUZ_8("♣ 8", 8),
    KREUZ_9("♣ 9", 9),
    KREUZ_10("♣ 10", 10),
    KREUZ_BUBE("♣ J", 10),
    KREUZ_DAME("♣ Q", 10),
    KREUZ_KOENIG("♣ K", 10),
    KREUZ_AS("♣ A", 11),

    // Pik (♠)
    PIK_2("♠ 2", 2),
    PIK_3("♠ 3", 3),
    PIK_4("♠ 4", 4),
    PIK_5("♠ 5", 5),
    PIK_6("♠ 6", 6),
    PIK_7("♠ 7", 7),
    PIK_8("♠ 8", 8),
    PIK_9("♠ 9", 9),
    PIK_10("♠ 10", 10),
    PIK_BUBE("♠ J", 10),
    PIK_DAME("♠ Q", 10),
    PIK_KOENIG("♠ K", 10),
    PIK_AS("♠ A", 11);

    private final String name;
    private final int value;

    Karte(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public boolean istAs() {
        return this == HERZ_AS || this == KARO_AS || this == KREUZ_AS || this == PIK_AS;
    }

    @Override
    public String toString() {
        return name;
    }
}