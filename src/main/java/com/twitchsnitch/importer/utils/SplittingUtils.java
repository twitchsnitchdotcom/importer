package com.twitchsnitch.importer.utils;

import com.github.twitch4j.helix.domain.User;

import java.util.*;

public class SplittingUtils {

    private static Set<String> userIdSet;
    private static Set<String> userNameSet;

    public static <T> List<Set<T>> splitIntoMultipleSets(Set<T> original, int numberOfNewSets) {
        // Create a list of sets to return.
        ArrayList<Set<T>> result = new ArrayList<Set<T>>(numberOfNewSets);

        // Create an iterator for the original set.
        Iterator<T> it = original.iterator();

        // Calculate the required number of elements for each set.
        int each = original.size() / numberOfNewSets;

        // Create each new set.
        for (int i = 0; i < numberOfNewSets; i++) {
            HashSet<T> s = new HashSet<T>(original.size() / numberOfNewSets + 1);
            result.add(s);
            for (int j = 0; j < each && it.hasNext(); j++) {
                s.add(it.next());
            }
        }
        return result;
    }

    // chops a list into non-view sublists of length L
    public static <T> List<Set<T>> choppedSet(Set<T> set, final int L) {
        List<Set<T>> parts = new ArrayList<Set<T>>();
        List<T> list = new ArrayList<>(set);
        final int N = set.size();
        for (int i = 0; i < N; i += L) {
            ArrayList<T> arrayList = new ArrayList<>(
                    list.subList(i, Math.min(N, i + L)));
            parts.add(new HashSet<>(arrayList));
        }
        return parts;
    }

    public static Set<String> getUsernamesOnly(Set<User> users){
        if(userNameSet == null){
            Set<String> intanceSet = new HashSet<>();
            for(User user: users){
                intanceSet.add(user.getLogin());
            }
            userNameSet = intanceSet;
        }
        return userNameSet;

    }

    public static Set<String> getUserIdsOnly(Set<User> users){
        if(userIdSet == null){
            Set<String> intanceSet = new HashSet<>();
            for(User user: users){
                intanceSet.add(user.getId());
            }
            userIdSet = intanceSet;
        }
        return userIdSet;
    }

}
