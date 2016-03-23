package org.virutor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.virutor.domain.User;

import java.util.function.Consumer;

@Controller
public class EmailPreferencesSyncController {

    private static final Logger log = LoggerFactory.getLogger(EmailPreferencesSyncController.class);

    @Value("${email.sync.url}")
    private String restUrl;

    @Value("${email.sync.retries:3}")
    private int retries;

    private String getIdUrl(User user) {
        return getIdUrl(user.getId());
    }

    private String getIdUrl(long id) {
        return restUrl + "/" + id;
    }

    @Async
    public void add(User user) {
        doOperation("Add", (rt) -> rt.postForEntity(getIdUrl(user), user, Void.class));
    }

    @Async
    public void update(User user) {
        doOperation("Update", (rt) -> rt.put(getIdUrl(user), user));
    }

    @Async
    public void delete(long id) {
        doOperation("Remove", (rt) -> rt.delete(getIdUrl(id)));
    }

    private void doOperation(String operationName, Consumer<RestTemplate> action) {

        for(int i = 0; i < retries + 1; i++) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                action.accept(restTemplate);
                return;
            } catch (Exception e) {
                log.error("Operation failed: " + operationName);
                log.error("Exception thrown while trying to sync with the email sync remote service", e);
            }
        }

    }

    private void doRemove(long id) {
        doOperation("Remove", (rt) -> rt.delete(getIdUrl(id)));
    }

    private void doAdd(User user) {
        doOperation("Add", (rt) -> rt.postForEntity(getIdUrl(user), user, Void.class));
    }

    private void doUpdate(User user) {
        doOperation("Update", (rt) -> rt.put(getIdUrl(user), user));
    }
}
