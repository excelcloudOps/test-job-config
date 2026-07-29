package com.excelcloud.demo;

/**
 * Tiny demo library used to exercise Nexus publish from GitHub Actions.
 */
public final class Greeting {
  private Greeting() {}

  public static String hello(String name) {
    if (name == null || name.isBlank()) {
      return "Hello, world!";
    }
    return "Hello, " + name.trim() + "!";
  }
}
