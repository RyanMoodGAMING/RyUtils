package me.ryanmood.ryutils.base.exceptions;

import lombok.Getter;

@Getter
public class UninitializedUtilException extends NullPointerException {

  private String util;
  private String methodUsed;
  private String platform;

  public UninitializedUtilException(String util, String methodUsed, String platform) {
    super("The util " + util + " was attempted to be used however it has not yet been initialized. (Method: " + methodUsed + ", Platform: " + platform + ")");
  }
}
