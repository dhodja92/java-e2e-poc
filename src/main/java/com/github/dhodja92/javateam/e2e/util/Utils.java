package com.github.dhodja92.javateam.e2e.util;

import java.util.regex.Pattern;

public final class Utils {

    private Utils() {
        //
    }

    public static final Pattern TC_PATTERN = Pattern.compile("^TC-\\d+");
}
