package org.virutor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virutor.domain.User;

import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping(value = "preferencesUpdateMock")
public class PreferencesUpdateMock {

    private final AtomicInteger aInt = new AtomicInteger();

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(User user) {

        int i = aInt.incrementAndGet();
        if(i % 10 > 5) {
            throw new RuntimeException();
        }
    }
}
