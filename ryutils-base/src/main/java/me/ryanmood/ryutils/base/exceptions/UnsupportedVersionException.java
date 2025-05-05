package me.ryanmood.ryutils.base.exceptions;

import lombok.Getter;

@Getter
public class UnsupportedVersionException extends RuntimeException {

    private String platform;
    private boolean XReflection;

    public UnsupportedVersionException(String platform, boolean xreflection) {
        super("Unfortunately, this version of " + platform + " isn't supported by RyUtils."
                + (xreflection ? "This may be caused by XSeries been unable to get a version." : ""));
        this.platform = platform;
        this.XReflection = xreflection;
    }

    public boolean causedByXSeries() {
        return this.isXReflection();
    }

}
