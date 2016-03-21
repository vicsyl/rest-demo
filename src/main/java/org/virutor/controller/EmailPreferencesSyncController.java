package org.virutor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.virutor.domain.User;

@Controller
public class EmailPreferencesSyncController {

    private static final Logger log = LoggerFactory.getLogger(EmailPreferencesSyncController.class);

    @Autowired
    private TaskExecutor taskExecutor;

    @Value("${email.sync.baseUrl}")
    private String restServerBaseUrl;

    @Value("${email.sync.path}")
    private String restPath;

    @Value("${email.sync.retries:3}")
    private int retries;

    public void update(User user) {

        System.out.println("Outside : this thread id: " + Thread.currentThread().getId());

        taskExecutor.execute(() -> doUpdate(user));
    }

    private void doUpdate(User user) {
        System.out.println("This thread id: " + Thread.currentThread().getId());

        String url = restServerBaseUrl.trim() + restPath.trim();

        for(int i = 0; i < retries + 1; i++) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForEntity(url, user, Void.class);
                return;
            } catch (Exception e) {
                log.error("Exception thrown while trying sync an update of a user with a email sync remote service", e);
            }
        }
    }
}
