package com.github.ppartisan.yorktnine;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class YorkT9PadTests {
    T9Pad pad;

    @Test
    public void testGetKeyCode() {
        pad = YorkT9Pad.create();
        pad.addKey(2, "abc");
        pad.addKey(3, "def");
        Integer expected = 2;
        assertEquals(expected, pad.getKeyCode('a'));
        assertEquals(expected, pad.getKeyCode('b'));
        assertEquals(expected, pad.getKeyCode('c'));
        expected = 3;
        assertEquals(expected, pad.getKeyCode('d'));
        assertEquals(expected, pad.getKeyCode('e'));
        assertEquals(expected, pad.getKeyCode('f'));
    }

    @Test
    public void testGetKeyCodeError() {
        pad = YorkT9Pad.create();
        pad.addKey(2, "abc");
        pad.addKey(3, "def");
        assertThrows(IllegalArgumentException.class, () -> {
            pad.getKeyCode('z');
        });
    }

    @Test
    public void testGetPadLetters() {
        pad = YorkT9Pad.create();
        pad.addKey(2, "abc");
        pad.addKey(3, "def");
        pad.addKey(4, "ghi");
        pad.addKey(5, "jkl");
        pad.addKey(6, "mno");
        pad.addKey(7, "pqrs");
        pad.addKey(8, "tuv");
// Test a partially completed numeric pad
        Character[] letters = new Character[]{'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v'};
        assertTrue(pad.getPadLetters().containsAll(Arrays.asList(letters)));
        assertTrue(Arrays.asList(letters).containsAll(pad.getPadLetters()));
        assertEquals(Arrays.asList(letters).size(),
                pad.getPadLetters().size());
// test a fully completed numeric pad
        pad.addKey(9, "wxyz");
        letters = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        assertTrue(pad.getPadLetters().containsAll(Arrays.asList(letters)));
        assertTrue(Arrays.asList(letters).containsAll(pad.getPadLetters()));
        assertEquals(Arrays.asList(letters).size(),
                pad.getPadLetters().size());
    }

    @Test
    public void testGetPadLettersEmpty() {
        pad = YorkT9Pad.create();
        assertTrue((pad.getPadLetters()).isEmpty());
    }

    @Test
    public void testWord2T9() {
        pad = YorkT9Pad.create();
        pad.addKey(2, "abc");
        pad.addKey(3, "def");
        pad.addKey(4, "ghi");
        pad.addKey(5, "jkl");
        pad.addKey(6, "mno");
        pad.addKey(7, "pqrs");
        pad.addKey(8, "tuv");
        pad.addKey(9, "wxyz");
        assertTrue(pad.isTextonym("good", "home"));
        assertTrue(pad.isTextonym("hood", "home"));
        assertTrue(pad.isTextonym("good", "hood"));
    }

    @Test
    public void testWord2T9False() {
        pad = YorkT9Pad.create();
        pad.addKey(2, "abc");
        pad.addKey(3, "def");
        pad.addKey(4, "ghi");
        pad.addKey(5, "jkl");
        pad.addKey(6, "mno");
        pad.addKey(7, "pqrs");
        pad.addKey(8, "tuv");
        pad.addKey(9, "wxyz");
        assertFalse(pad.isTextonym("good", "hoods"));
        assertFalse(pad.isTextonym("good", "hogs"));
        assertFalse(pad.isTextonym("boo", "goo"));
        assertFalse(pad.isTextonym("doly", "doll"));
    }
}
