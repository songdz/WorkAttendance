package com.songdz.fielddatacheck.app;

/**
 * Created by SongDz on 2014/6/13.
 */
public enum UserAuthority {
    INVALID_USER("INVALID_USER"), COMMON_USER("COMMON_USER"), SUPER_USER("SUPER_USER"), PRIVILEGED_USER("PRIVILEGED_USER") ;

    private String value;
    private UserAuthority(String value) {
        this.value = value;
    }

    public int getAuthorityIntValue() {
        return this.ordinal() - 1;
    }

    @Override
    public String toString() {
        return this.value;
    }
    public static UserAuthority valueOf(int i) {
        switch ( i ) {
            case 2:
                return PRIVILEGED_USER;
            case 1:
                return SUPER_USER;
            case 0:
                return COMMON_USER;
            case -1:
                return INVALID_USER;
            default:
                return INVALID_USER;
        }
    }

}
