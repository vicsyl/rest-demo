package org.virutor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmailPreferenceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(name = "preference_group")
    private String group;

    public static EmailPreferenceItem createContent(String name) {
        return EmailPreferenceItem.createItem(name, "Content");
    }

    private static EmailPreferenceItem createItem(String name, String group) {
        EmailPreferenceItem item = new EmailPreferenceItem();
        item.name = name;
        item.group = group;
        return item;
    }

    public static EmailPreferenceItem createRootItem(String name) {
        return EmailPreferenceItem.createItem(name, null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}