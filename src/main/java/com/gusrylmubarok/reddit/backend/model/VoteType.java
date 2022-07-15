package com.gusrylmubarok.reddit.backend.model;

import com.gusrylmubarok.reddit.backend.exceptions.BackendRedditException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);
    private int direction;
    VoteType(int direction) {
    }
    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new BackendRedditException("Vote type is not found!"));
    }
    public Integer getDirection() {
        return direction;
    }
}
