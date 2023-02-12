package ru.job4j.question;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int changed = 0;
        int deleted = 0;
        Map<Integer, String> userMapCurrent = current.stream().collect(Collectors.toMap(User::getId, User::getName));
        for (User el : previous) {
            String elCurrent = userMapCurrent.get(el.getId());
            if (elCurrent == null) {
                deleted++;
            } else if (!elCurrent.equals(el.getName())) {
                    changed++;
            }
        }
        return new Info(current.size() - previous.size() + deleted, changed, deleted);
    }
}