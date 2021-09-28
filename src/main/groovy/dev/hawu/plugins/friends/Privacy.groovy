package dev.hawu.plugins.friends

import groovy.transform.CompileStatic

@CompileStatic
final enum Privacy {

    ANY, FRIENDS, NONE

    static Privacy fromID(final int id) {
        switch(id) {
            case 0: return ANY
            case 1: return FRIENDS
            case 2: return NONE
        }
        throw new IllegalArgumentException("Unknown privacy ID")
    }

}