package me.ryanmood.ryutils.java.misc;

import lombok.Getter;

@Getter
public enum Colours {
    RESET("&r", "\033[0m"),
    BLACK("&0", "\033[0;30m"),
    RED("&c", "\033[0;31m"),
    GREEN("&a", "\033[0;32m"),
    YELLOW("&e", "\033[0;33m"),
    BLUE("&b", "\033[0;34m"),
    PURPLE("&d", "\033[0;35m"),
    CYAN("&3", "\033[0;36m"),
    WHITE("&f", "\033[0;37m");


    private final String code;
    private final String translation;

    Colours(String code, String translation) {
        this.code = code;
        this.translation = translation;
    }

}
