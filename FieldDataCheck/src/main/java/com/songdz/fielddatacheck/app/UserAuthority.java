package com.songdz.fielddatacheck.app;

/**
 * Created by SongDz on 2014/6/13.
 */
public enum UserAuthority {
    PRIVILEGED_USER(0), SUPER_USER(1), COMMON_USER(2), INVALID_USER(-1);

    private int value;
    private UserAuthority(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
    public static UserAuthority valueOf(int i) {
        switch (i) {
            case 0:
                return PRIVILEGED_USER;
            case 1:
                return SUPER_USER;
            case 2:
                return COMMON_USER;
            case -1:
                return INVALID_USER;
            default:
                return INVALID_USER;
        }
    }
}
