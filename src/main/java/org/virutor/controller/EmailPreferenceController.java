package org.virutor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virutor.domain.EmailPreferenceItem;
import org.virutor.repository.EmailPreferenceItemRepository;

import java.util.List;


@RequestMapping(value = "/emailPreferences")
@RestController
public class EmailPreferenceController {

    @Autowired
    private EmailPreferenceItemRepository emailPreferenceRepo;

    @RequestMapping (value = "/all", method = RequestMethod.GET)
    public List<EmailPreferenceItem> getAll() {
        return emailPreferenceRepo.findAll();
    }


}
