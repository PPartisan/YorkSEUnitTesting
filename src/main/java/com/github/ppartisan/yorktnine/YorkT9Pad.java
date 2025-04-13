package com.github.ppartisan.yorktnine;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableSet;

class YorkT9Pad implements T9Pad {

    private static final String KEY_NULL = "'key' must not be null.";
    private static final String LETTERS_NULL = "'letters' must not be null.";
    private static final String KEY_NOT_IN_RANGE = "'key' not in range 0 to 9 (inclusive).";
    private static final String LETTERS_ALREADY_MAPPED = "Character in 'letters' already has a mapping.";

    private final Map<Integer, Set<Character>> pad;

    static T9Pad create() {
        return new YorkT9Pad();
    }

    private YorkT9Pad() {
        pad = new HashMap<>();
    }

    @Override
    public void addKey(Integer key, String letters) {
        raiseIf(isNull(key), KEY_NULL);
        raiseIf(isNull(letters), LETTERS_NULL);
        raiseIf(keyNotInRange(key), KEY_NOT_IN_RANGE);
        raiseIf(characterAlreadyMapped(letters), LETTERS_ALREADY_MAPPED);
        pad.put(key, asCharacterSet(letters));
    }

    @Override
    public Set<Integer> keySet() {
        return pad.keySet();
    }

    @Override
    public Set<Character> getPadLetters() {
        return currentCharacters();
    }

    public Integer getKeyCode(Character letter) {
        return pad.entrySet().stream()
                .filter(hasLetter(letter))
                .findAny()
                .map(Map.Entry::getKey)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean isTextonym(String w1, String w2) {
        //Get codes for both words. Could check equals?
        if(w1 == null || w2 == null || !Objects.equals(w1.length(), w2.length()))
            return false;
        for(int i = 0; i < w1.length(); i++){
            final Integer kc1 = getKeyCode(w1.charAt(i));
            final Integer kc2 = getKeyCode(w2.charAt(i));
            if(!Objects.equals(kc1, kc2))
                return false;
        }
        return true;
    }

    private Set<Character> currentCharacters() {
        return pad.values().stream()
                .flatMap(Collection::stream)
                .collect(toUnmodifiableSet());
    }

    private Supplier<Boolean> characterAlreadyMapped(String input) {
        return  () -> {
            final Set<Character> current = currentCharacters();
            final Set<Character> proposed = asCharacterSet(input);
            for(Character c : proposed) {
                if (current.contains(c))
                    return true;
            }
            return false;
        };
    }

    private Predicate<Map.Entry<Integer, Set<Character>>> hasLetter(Character letter) {
        return entry -> entry.getValue().contains(letter);
    }

    private Supplier<Boolean> isNull(Object o) {
        return () -> Objects.isNull(o);
    }

    private Supplier<Boolean> keyNotInRange(int key) {
        return () -> key < 0 || key > 9;
    }

    private void raiseIf(Supplier<Boolean> cond) {
        raiseIf(cond, null);
    }

    private void raiseIf(Supplier<Boolean> cond, String msg) {
        if(cond.get())
            throw new IllegalArgumentException(msg);
    }

    private static Set<Character> asCharacterSet(String in) {
        return in.chars().mapToObj(code -> (char) code).collect(toSet());
    }
}
