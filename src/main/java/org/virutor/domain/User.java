package org.virutor.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String salutation;
    private String email;

    @ManyToMany
    private Set<EmailPreferenceItem> emailPreferences;

    public static User createUser(String salutation, String name, String email, EmailPreferenceItem... emailPreferences) {
        User user = new User();
        user.name = name;
        user.salutation = salutation;
        user.email = email;
        user.emailPreferences = new HashSet(Arrays.asList(emailPreferences));
        return user;
    }

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

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setSalutation(String salutation) {
        this.salutation = salutation;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<EmailPreferenceItem> getEmailPreferences() {
        return emailPreferences;
    }

    public void setEmailPreferences(Set<EmailPreferenceItem> emailPreferences) {
        this.emailPreferences = emailPreferences;
    }
}
