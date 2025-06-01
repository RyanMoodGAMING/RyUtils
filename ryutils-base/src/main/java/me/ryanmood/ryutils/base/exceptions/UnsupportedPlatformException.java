package me.ryanmood.ryutils.base.exceptions;

import lombok.Getter;

@Getter
public class UnsupportedPlatformException extends RuntimeException {

    private String methodUsed;
    private String platform;

    public UnsupportedPlatformException(String method, String platform) {
        super("This method (" + method + ") doesn't support this platform (" + platform + ").");
        this.methodUsed = method;
        this.platform = platform;
    }

}
