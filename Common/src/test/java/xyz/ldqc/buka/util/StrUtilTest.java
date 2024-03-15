package xyz.ldqc.buka.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StrUtilTest {

    @Test
    void testFormatWithOneArgument() {
        String result = StrUtil.format("Hello, {0}!", "World");
        assertEquals("Hello, World!", result);
    }

    @Test
    void testFormatWithMultipleArguments() {
        String result = StrUtil.format("Welcome {0} to {1}!", "Alice", "Buka");
        assertEquals("Welcome Alice to Buka!", result);
    }

    @Test
    void testFormatWithNoArguments() {
        String result = StrUtil.format("No arguments provided.");
        assertEquals("No arguments provided.", result);
    }

    @Test
    void testFormatWithExtraArguments() {
        String result = StrUtil.format("First is {0}, then {1}!", "one", "two", "three");
        assertEquals("First is one, then two!", result);
    }

    @Test
    void testFormatWithMissingArguments() {
        String result = StrUtil.format("Missing arguments: {0}, {1}", "one");
        assertEquals("Missing arguments: one, null", result);
    }

    @Test
    void testFormatWithEmptyFormat() {
        String result = StrUtil.format("");
        assertEquals("", result);
    }

    @Test
    void testFormatWithComplexFormat() {
        String result = StrUtil.format("User {0} has {1} points ({2}%)", "JohnDoe", 100, 75);
        assertEquals("User JohnDoe has 100 points (75%)", result);
    }

    // Add more test cases if necessary...
}
