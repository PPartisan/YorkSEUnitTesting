package com.github.ppartisan.yorktnine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.junit.jupiter.api.Assertions.*;

class T9PadTest {

    private T9Pad pad;

    @BeforeEach
    void setUp() {
        pad = YorkT9Pad.create();
    }

    @Test
    void givenNoKeysArePresentInPad_whenGetKeys_thenKeysEmpty() {
        assertTrue(pad.keySet().isEmpty());
    }

    @Test
    void givenKeyIsPresentInPad_whenGetKeys_thenKeysIsNotEmpty() {
        pad.addKey(0, "");
        assertFalse(pad.keySet().isEmpty());
    }

    @Test
    void givenKeyIsPresentInPad_whenGetKeys_thenKeySetContainsKey() {
        pad.addKey(0, "");
        assertEquals(pad.keySet(), Set.of(0));
    }

    @Test
    void whenAddKey_andKeyIsNull_thenThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> pad.addKey(null, "")
        );
    }

    @Test
    void whenAddKey_andLettersAreNull_thenThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> pad.addKey(0, null)
        );
    }

    @Test
    void whenAddKey_andKeyIsLessThanZero_thenThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> pad.addKey(MIN_VALUE, "")
        );
    }

    @Test
    void whenAddKey_andKeyIsGreaterThanNine_thenThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> pad.addKey(MAX_VALUE, "")
        );
    }

    @Test
    void whenAddKey_andKeyIsNine_thenDoNotThrowIllegalArgumentException() {
        assertDoesNotThrow(() -> pad.addKey(9, ""));
    }

    @Test
    void whenAddKey_andKeyIsValid_andCharacterAlreadyExistsInPad_thenThrowIllegalArgumentException() {
        assertDoesNotThrow(() -> pad.addKey(2, "a"));
        assertThrows(
                IllegalArgumentException.class,
                () -> pad.addKey(3, "a")
        );
    }

    @Test
    void givenNoMappingExistsForNumber_whenGetKeyCode_thenThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> pad.getKeyCode('a')
        );
    }

    @Test
    void givenMappingExistsForNumber_whenGetKeyCode_thenDoNotThrowIllegalArgumentException() {
        pad.addKey(2, "abc");
        assertDoesNotThrow(() -> pad.getKeyCode('a'));
    }

    @Test
    void givenMappingExistsForNumber_whenGetKeyCode_thenReturnCorrectKeyCode() {
        pad.addKey(2, "abc");
        assertEquals(2, pad.getKeyCode('a'));
    }

    @Test
    void givenNoMappingsExist_whenGetPadLetters_thenReturnEmptySet() {
        assertTrue(pad.getPadLetters().isEmpty());
    }

    @Test
    void givenMappingsExist_whenGetPadLetters_thenReturnMappedPadLetters() {
        pad.addKey(2, "abc");
        pad.addKey(3, "def");
        assertEquals(pad.getPadLetters(), Set.of('a','b','c','d','e','f'));
    }

    @Test
    void whenIsTextonymInputIsNull_thenReturnFalse() {
        assertFalse(pad.isTextonym(null, "not_null"));
    }

    @Test
    void givenTwoNonTextonymWords_whenIsTextonym_thenReturnFalse() {
        populatePad();
        assertFalse(pad.isTextonym("not", "textonyms"));
    }

    @Test
    void givenTwoTextonymWords_whenIsTextonym_thenReturnTrue() {
        populatePad();
        assertTrue(pad.isTextonym("home", "hood"));
    }

    private void populatePad() {
        pad.addKey(2, "abc");
        pad.addKey(3, "def");
        pad.addKey(4, "ghi");
        pad.addKey(5, "jkl");
        pad.addKey(6, "mno");
        pad.addKey(7, "pqrs");
        pad.addKey(8, "tuv");
        pad.addKey(9, "wxyz");
    }
}