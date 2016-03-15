package org.virutor.controller;

import org.springframework.stereotype.Controller;
import org.virutor.domain.EmailPreferenceEntity;

import java.util.Collection;
import java.util.Map;

@Controller
public class EmailPreferenceService {

    public Map<String, Collection<EmailPreferenceEntity>> getOfferedPreferences(long userId) {
        return EmailPreferenceEntity.preferenceEntityMap;
    }

    public Map<String, Collection<EmailPreferenceEntity>> getUserPreferences(long userId) {
        return EmailPreferenceEntity.preferenceEntityMap;
    }

}
