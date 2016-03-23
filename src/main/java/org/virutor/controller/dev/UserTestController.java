package org.virutor.controller.dev;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virutor.controller.EmailPreferencesSyncController;
import org.virutor.domain.EmailPreferenceItem;
import org.virutor.domain.User;
import org.virutor.repository.EmailPreferenceItemRepository;
import org.virutor.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value="/dev")
@ConditionalOnExpression("${UserTestController.enabled:false}")
public class UserTestController {

    @Autowired
    private EmailPreferenceItemRepository emailPreferenceRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailPreferencesSyncController emailPreferencesSyncController;

    private final AtomicBoolean firstUsersCreated = new AtomicBoolean();

    private final AtomicInteger counter = new AtomicInteger();

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public Collection<User> createUsers() {

        List<User> users = users();
        Collection<User> ret = userRepo.save(users);

        for (User user : users) {
            emailPreferencesSyncController.update(user);
        }
        return ret;

    }

    private Map<String, EmailPreferenceItem> getMappedPreferences() {

        List<EmailPreferenceItem> allItems = emailPreferenceRepo.findAll();
        return allItems.stream().collect(Collectors.toMap(EmailPreferenceItem::getName, Function.identity()));
    }

    private List<User> pristineUsers() {

        Map<String, EmailPreferenceItem> mappedPrefs = getMappedPreferences();

        return ImmutableList.of(

                User.createUser("Mr.", "Alan Turing", "alan.turing@gmail.com",
                        mappedPrefs.get("Hollywood"),
                        mappedPrefs.get("Promo offers")),
                User.createUser("Mr.", "Winston Churchill", "Winston.Churchill@gmail.com",
                        mappedPrefs.get("Bollywood"),
                        mappedPrefs.get("Service updates")),
                User.createUser("Mrs.", "Maria Theresa", "maria.theresa@cak.gov",
                        mappedPrefs.get("Hollywood"),
                        mappedPrefs.get("Jazwood"),
                        mappedPrefs.get("Promo offers"),
                        mappedPrefs.get("Service updates"))
        );

    }

    private List<User> getNewUsers() {

        Map<String, EmailPreferenceItem> mappedPrefs = getMappedPreferences();

        String user1 = "User" + counter.incrementAndGet();
        String user2 = "User" + counter.incrementAndGet();
        String user3 = "User" + counter.incrementAndGet();

        return ImmutableList.of(

                User.createUser("Mr.", user1, user1 + "@gmail.com",
                        mappedPrefs.get("Hollywood"),
                        mappedPrefs.get("Promo offers")),
                User.createUser("Mr.", user2, user2 + "@gmail.com",
                        mappedPrefs.get("Bollywood"),
                        mappedPrefs.get("Service updates")),
                User.createUser("Mrs.", user3, user3 + "@hotmail.com",
                        mappedPrefs.get("Hollywood"),
                        mappedPrefs.get("Jazwood"),
                        mappedPrefs.get("Promo offers"),
                        mappedPrefs.get("Service updates"))
        );

    }

    private List<User> users() {

        if (firstUsersCreated.compareAndSet(false, true)) {
            return pristineUsers();
        } else {
            return getNewUsers();
        }
    }

}
