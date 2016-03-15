package org.virutor.domain;

import com.google.common.collect.ImmutableMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;
import java.util.Map;

@Entity
public class User {

    /*
    name, salutation, email and email preferences.
    Email preferences denote whether the user is interested in obtaining newsletters from the following areas:
    Content → Hollywood, Bollywood, Jazwood; Promo offers and Service updates.
    */

    public static final Map<Long, User> users = ImmutableMap.of(
            1L, of(1).setEmail("vicsyl@seznam.cz").setSalutation("Mr.").setName("Venca").setEmailPreferences(EmailPreferenceEntity.preferenceEntityMap),
            2L, of(2).setEmail("mr.vaclav.vavra@gmail.com").setSalutation("Mrs.").setName("Kata").setEmailPreferences(EmailPreferenceEntity.preferenceEntityMap),
            10L, of(11).setEmail("mr.vaclav.vavra@gmail.com").setSalutation("Mrs.").setName("Kata").setEmailPreferences(EmailPreferenceEntity.preferenceEntityMap)
    );

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String salutation;
    private String email;
    //private Map<String, Collection<EmailPreferenceEntity>> emailPreferences;

    public static User of(long id) {
        return new User(id);
    }

    public User(long id) {
        this.id = id;
    }

    User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getEmail() {
        return email;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setSalutation(String salutation) {
        this.salutation = salutation;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
/*
    public Map<String, Collection<EmailPreferenceEntity>> getEmailPreferences() {
        return emailPreferences;
    }
*/
    public User setEmailPreferences(Map<String, Collection<EmailPreferenceEntity>> emailPreferences) {
        //this.emailPreferences = emailPreferences;
        return this;
    }


}
