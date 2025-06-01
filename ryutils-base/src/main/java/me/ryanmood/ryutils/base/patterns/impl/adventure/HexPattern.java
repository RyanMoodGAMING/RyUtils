package me.ryanmood.ryutils.base.patterns.impl.adventure;

import me.ryanmood.ryutils.base.patterns.Pattern;

import java.util.regex.Matcher;

public class HexPattern implements Pattern {

    private final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(?i)&#([0-9A-F]{6})");

    /**
     * Applies this pattern to the provided String.
     * Output might me the same as the input if this pattern is not present.
     *
     * @param string The String to which this pattern should be applied to
     * @return The new String with applied pattern
     */
    @Override
    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String hex = matcher.group(1);

            string = string.replace(matcher.group(), "<#" + hex + ">");
        }
        return string;
    }

}
