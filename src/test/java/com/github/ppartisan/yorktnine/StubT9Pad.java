package com.github.ppartisan.yorktnine;

import java.util.Set;

class StubT9Pad implements T9Pad {
    @Override
    public Set<Integer> keySet() {
        return Set.of();
    }

    @Override
    public void addKey(Integer key, String letters) {
        // No-Op
    }

    @Override
    public Integer getKeyCode(Character letter) {
        return 0;
    }

    @Override
    public Set<Character> getPadLetters() {
        return Set.of();
    }

    @Override
    public boolean isTextonym(String w1, String w2) {
        return false;
    }
}
