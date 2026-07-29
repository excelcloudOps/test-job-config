package com.excelcloud.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GreetingTest {
  @Test
  void helloWithName() {
    assertEquals("Hello, Nexus!", Greeting.hello("Nexus"));
  }

  @Test
  void helloWithBlankFallsBack() {
    assertEquals("Hello, world!", Greeting.hello("  "));
  }
}
