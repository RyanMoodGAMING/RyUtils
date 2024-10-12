package me.ryanmood.ryutils.base.patterns;

import java.util.regex.Matcher;

public class StripPattern implements Pattern {

    private final java.util.regex.Pattern patternOne = java.util.regex.Pattern.compile("(?i)&#([0-9A-F]{6})");
    private final java.util.regex.Pattern patternTwo = java.util.regex.Pattern.compile("(?i)<#([0-9A-F]{6}>)");
    private final java.util.regex.Pattern ampersandPattern = java.util.regex.Pattern.compile("(?i)&([0-9A-FK-ORX#])");

    /**
     * Applies this pattern to the provided String.
     * Output might be the same as the input if this pattern is not present.
     *
     * @param string The String to which this pattern should be applied to
     * @return The new String with applied pattern
     */
    @Override
    public String process(String string) {
        Matcher matcherOne = patternOne.matcher(string);
        while (matcherOne.find()) {
            String hex = matcherOne.group(1);

            string = string.replace(matcherOne.group(), "");
        }

        Matcher matcherTwo = patternTwo.matcher(string);
        while (matcherTwo.find()) {
            String hex = matcherTwo.group(1);

            string = string.replace(matcherTwo.group(), "");
        }

        string.replaceAll("§", "&");

        Matcher ampersandMatcher = ampersandPattern.matcher(string);
        while (ampersandMatcher.find()) {
            String code = ampersandMatcher.group(1);

            string = string.replace(ampersandMatcher.group(), "");
        }

        return string;
    }

}
