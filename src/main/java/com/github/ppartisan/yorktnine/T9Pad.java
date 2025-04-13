package com.github.ppartisan.yorktnine;

import java.util.Set;

interface T9Pad {
    /**
     * @return The set of keys used in the keypad, that is all the digits that are paired with at least one character.
     */
    Set<Integer> keySet();

    /**
     * Adds a mapping between {@code key} and every character in the String {@code letters}. The method throws an
     * {@link IllegalArgumentException} if any of the arguments is {@code null}, or if the key is not comprised between
     * 0 and 9 inclusive, or if one of the character from letters already exists in pad.
     * @param key A key on the numeric pad, between 0 and 9.
     * @param letters The letters associated with this key.
     */
    void addKey(Integer key, String letters);

    /**
     * @param letter A letter of the alphabet (in range {@code [a-z]}).
     * @return The digit associated with the character {@code letter}. If there is no mapping for this character, the
     * method throws an {@link IllegalArgumentException}.
     */
    Integer getKeyCode(Character letter);

    /**
     * @return All characters represented in the pad. For example, this method would return an empty set if there
     * are no mappings.
     * @see #addKey(Integer, String)
     */
    Set<Character> getPadLetters();

    /**
     * @param w1 The first word.
     * @param w2 The second word.
     * @return {@code true} if {@code word1} and {@code word2} are
     * <a href="https://en.wiktionary.org/wiki/textonym">textonyms</a>; {@code false} otherwise.
     */
    boolean isTextonym(String w1, String w2);
}
