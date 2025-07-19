package me.ryanmood.ryutils.java;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.ryanmood.ryutils.java.misc.Colours;

import java.util.Arrays;

@Getter
@Setter
public class RyMessageUtil {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private static RyMessageUtil instance;

    private String prefix = "&0» &f";
    private String errorPrefix = "&cError &0» &c";
    private String breaker = "&0------------------------------------";
    private String supportMessage = "Please contact the plugin author for support.";

    public RyMessageUtil() {
        instance = this;
    }

    public RyMessageUtil(String prefix) {
        instance = this;
        this.prefix = prefix;
    }

    /**
     * Translate the colours in a message.
     *
     * @param message The message to translate.
     * @return {@link String} of the translation.
     */
    public String translate(String message) {
        for (Colours colour : Colours.values()) {
            message.replace(colour.getCode(), colour.getTranslation());
        }

        return message;
    }

    /**
     * Send a log to console.
     *
     * @param message The message to be sent.
     */
    public void sendLog(String message) {
        this.sendLog(false, this.translate(message));
    }

    /**
     * Send a log to console.
     *
     * @param prefix  Should the prefix be sent at the beginning of the log?
     * @param message The message to be sent.
     */
    public void sendLog(boolean prefix, String message) {
        if (prefix) message = this.prefix + message;
        System.out.println(this.translate(message));
    }

    /**
     * Send a log to console.
     *
     * @param messages The messages to be sent.
     */
    public void sendLog(String... messages) {
        this.sendLog(false, messages);
    }

    public void sendLog(boolean prefix,  String... messages) {
        for (String message : messages) {
            this.sendLog(prefix, this.translate(message));
        }
    }

    /**
     * Send an error log in console.
     *
     * @param error The error that has occurred.
     */
    public void sendError(String error) {
        this.sendLog(false, this.getBreaker(),
                "&fAn error has occurred.",
                "&fError: &c" + error,
                this.getSupportMessage(),
                this.breaker);
    }

    /**
     * Send an error in console.
     *
     * @param error    The error that has occurred.
     * @param shutdown Should the java program be shutdown?
     */
    public void sendError(String error, boolean shutdown) {
        this.sendLog(false, this.getBreaker(),
                "&fAn error has occurred.",
                "&fError: &c" + error,
                this.getSupportMessage(),
                this.breaker);

        if (shutdown) System.exit(0);
    }

    /**
     * Send an error in console.
     *
     * @param error     The error that has occurred.
     * @param exception The error exception that has occurred.
     */
    public void sendError(String error, Exception exception) {
        this.sendLog(false, this.getBreaker(),
                "&fAn error has occurred.",
                "&fError: &c" + error,
                this.getSupportMessage(),
                "&f(Debug) Stacktrace from Exception: ");

        exception.printStackTrace();
    }

    /**
     * Send an error log in console with an exception if enabled.
     *
     * @param error     The error that has occurred.
     * @param exception The exception which occurred to post.
     * @param debug     Should the exception be posted?
     */
    public void sendError(String error, Exception exception, boolean debug) {
        if (!debug) this.sendError(error);
        else if (debug) this.sendError(error, exception);
    }

    /**
     * Send an error log in console with an exception (if enabled) and shutdown the application.
     *
     * @param error     The error that has occurred.
     * @param exception The exception which occurred to post.
     * @param debug     Should the exception be posted?
     * @param shutdown   Should the program be shutdown?
     */
    public void sendError(String error, Exception exception, boolean debug, boolean shutdown) {
        if (!debug) this.sendError(error);
        else if (debug) this.sendError(error, exception);

        if (shutdown) System.exit(0);
    }

    /**
     * Send a long error in console.
     *
     * @param errors The errors.
     */
    public void sendLongError(String... errors) {
        this.sendLog(false, this.getBreaker(),
                "&fAn error has occurred.",
                "&fError(s): &c");

        Arrays.stream(errors).forEach(error -> this.sendLog(false, "&c" + error));
        this.sendLog(false, this.getSupportMessage(),
                this.breaker);
    }

    /**
     * Send a message in console saying that license verification was successful.
     */
    public void sendLicenseSuccessful() {
        this.sendLog(false, this.getBreaker(),
                "&aLicense has been authenticated.",
                this.getBreaker());
    }

    /**
     * Send an error in regard to licenses.
     *
     * @param error The error that occurred.
     */
    public void sendLicenseError(String error) {
        this.sendLog(false, this.getBreaker(),
                "&fAn error occurred while verifying your license.",
                "&fError: &c" + error,
                this.getSupportMessage(),
                this.getBreaker());
    }

    /**
     * Send an error in regard to licenses and shutdown the application.
     *
     * @param error    The error that occurred.
     * @param shutdown Should the java program be disabled?
     */
    public void sendLicenseError(String error, boolean shutdown) {
        this.sendLicenseError(error);

        if (shutdown) System.exit(0);
    }

    public static RyMessageUtil getUtil() {
        return instance;
    }

}
