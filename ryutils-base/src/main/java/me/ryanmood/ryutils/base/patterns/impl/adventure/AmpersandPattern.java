package me.ryanmood.ryutils.base.patterns.impl.adventure;

import com.google.common.collect.ImmutableMap;
import me.ryanmood.ryutils.base.patterns.Pattern;

import java.util.Map;
import java.util.regex.Matcher;

public class AmpersandPattern implements Pattern {

    private final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(?i)&([0-9A-FK-ORX#])");
    private final Map<String, String> codeTranslations = new ImmutableMap.Builder<String, String>()
            .put("0", "<black>")
            .put("1", "<dark_blue>")
            .put("2", "<dark_green>")
            .put("3", "<dark_aqua>")
            .put("4", "<dark_red>")
            .put("5", "<dark_purple>")
            .put("6", "<gold>")
            .put("7", "<gray>")
            .put("8", "<dark_gray>")
            .put("9", "<blue>")
            .put("a", "<green>")
            .put("b", "<aqua>")
            .put("c", "<red>")
            .put("d", "<light_purple>")
            .put("e", "<yellow>")
            .put("f", "<white>")
            .put("k", "<obfuscated>")
            .put("l", "<bold>")
            .put("m", "<strikethrough>")
            .put("n", "<underlined>")
            .put("o", "<italic>")
            .put("r", "<reset>")
            .build();

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
            String colour = matcher.group(1);
            String adventure = codeTranslations.get(colour.toLowerCase());

            if (adventure == null) {
                return matcher.group();
            }

            string = string.replace(matcher.group(), adventure);
        }
        return string;
    }
}
