package org.virutor.controller.dev;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virutor.domain.User;

import java.util.concurrent.atomic.AtomicInteger;


@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "preferencesUpdateMock/userPrefs")
@ConditionalOnExpression("${PreferencesUpdateMock.enabled:false}")
public class PreferencesUpdateMock {

    private final AtomicInteger aInt = new AtomicInteger();

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public void add(User user) {

        mockOperation();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id) {

        mockOperation();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {

        mockOperation();
    }

    private void mockOperation() {
        int i = aInt.incrementAndGet();

        //this is to test the retry functionality
        if(i % 10 > 5) {
            throw new RuntimeException("Even a mock will sometimes let you down");
        }
    }

}
