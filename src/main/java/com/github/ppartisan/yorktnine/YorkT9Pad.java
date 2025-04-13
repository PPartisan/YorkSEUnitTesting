package com.github.ppartisan.yorktnine;

import java.util.*;

class YorkT9Pad implements T9Pad {

    HashMap<Integer, Set<Character>> pad;

    YorkT9Pad() {
        pad = new HashMap<Integer, Set<Character>>();
    }

    /**
     * Construct an empty keypad. Numeric key and mapped characters must be added
     * via the method addKey(Integer, String).
     */
    YorkT9Pad(HashMap<Integer, Set<Character>> pad) {
        this.pad = pad;
    }

    @Override
    public void addKey(Integer key, String letters) {
        if (letters == null || key == null || key < 0 || key > 9) {
            throw new IllegalArgumentException();
        }
        Set<Character> currentLetters = pad.get(key);
        if (currentLetters == null) {
            currentLetters = new HashSet<>();
        }
        for (int i = 0; i < letters.length() - 1; i++) {
            if (getPadLetters().contains(letters.charAt(i))) {
                throw new IllegalArgumentException();
            }
            currentLetters.add(letters.charAt(i));
        }
        pad.put(key, currentLetters);
    }
    @Override
    public String toString() {
        String output = "<T9Pad:\n";
        for (Integer key : pad.keySet()) {
            output += key + ":";
            for (Character letter : pad.get(key)) {
                output += letter;
            }
            output += "\n";
        }
        output += ">";
        return output;
    }

    @Override
    public Set<Integer> keySet() {
        return pad.keySet();
    }

    @Override
    public Set<Character> getPadLetters() {
        Set<Character> letters = new HashSet<>();
        for (Integer key : pad.keySet()) {
            letters.addAll(pad.get(key));
        }
        return letters;
    }
    public Integer getKeyCode(Character letter) {
        for (Integer key : pad.keySet()) {
            if (pad.get(key).contains(letter)) { // found the key
                return key;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isTextonym(String word1, String word2) {
        return word2T9(word1).equals(word2T9(word2));
    }

    private String word2T9(String word) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < word.length() - 1; i++) {
            output.append(getKeyCode(word.charAt(i)));
        }
        return output.toString();
    }

}
