package org.virutor.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.Map;

public class EmailPreferenceEntity {

    public static final String NO_GROUP = "NO_GROUP";
    private static final String content = "Content";

    public static Map<String, Collection<EmailPreferenceEntity>> preferenceEntityMap =

        ImmutableMap.of(content, ImmutableList.of(of(1).setName("Boolywood").setGroup(content), of(2).setName("Jazzwood").setGroup(content)),
                        NO_GROUP, ImmutableList.of(of(3).setName("Promo offers").setGroup(NO_GROUP))
                );


    private final long id;
    private String name;
    private String group;

    public static EmailPreferenceEntity of(long id) {
        return new EmailPreferenceEntity(id);
    }

    public EmailPreferenceEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EmailPreferenceEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public EmailPreferenceEntity setGroup(String group) {
        this.group = group;
        return this;
    }
}
