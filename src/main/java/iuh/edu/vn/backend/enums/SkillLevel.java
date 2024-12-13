package iuh.edu.vn.backend.enums;

public enum SkillLevel {
    BEGINNER(1),
    INTERMEDIATE(2),
    ADVANCED(3),
    PROFESSIONAL(4),
    MASTER(5);
    private final int value;

    SkillLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SkillLevel fromValue(int value) {
        for (SkillLevel level : values()) {
            if (level.value == value) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid skill level value: " + value);
    }
}
