package org.virutor.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virutor.domain.EmailPreferenceItem;
import org.virutor.domain.User;
import org.virutor.repository.EmailPreferenceItemRepository;
import org.virutor.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailPreferenceItemRepository emailPreferenceRepo;

    @Autowired
    EmailPreferencesSyncController emailPreferencesSyncController;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return userRepo.findOne(id);
    }

    private EmailPreferenceItem stripItem(EmailPreferenceItem item) {
        EmailPreferenceItem newItem = new EmailPreferenceItem();
        newItem.setId(item.getId());
        return newItem;
    }

    private List<User> users() {

        List<EmailPreferenceItem> allItems = emailPreferenceRepo.findAll();

        Map<String, EmailPreferenceItem> mappedNames =
                allItems.stream().collect(Collectors.toMap(EmailPreferenceItem::getName, Function.identity()));


        return ImmutableList.of(

                User.createUser("Mr.", "Alan Turing", "alan.turing@gmail.com",
                        stripItem(mappedNames.get("Hollywood")),
                        stripItem(mappedNames.get("Promo offers"))),
                User.createUser("Mr.", "Winston Churchill", "alan.turing@gmail.com",
                        stripItem(mappedNames.get("Bollywood")),
                        stripItem(mappedNames.get("Service updates"))),
                User.createUser("Mrs.", "Ken Thompson", "alan.turing@gmail.com",
                        stripItem(mappedNames.get("Hollywood")),
                        stripItem(mappedNames.get("Jazwood")),
                        stripItem(mappedNames.get("Promo offers")),
                        stripItem(mappedNames.get("Service updates")))
        );
    }


    @RequestMapping(method = RequestMethod.GET)
    public Map<String, List<User>> getAll() {

        List<User> all = userRepo.findAll();
        return ImmutableMap.of("Users", all);

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Collection<User> test() {

        List<User> users = users();
        Collection<User> ret = userRepo.save(users);

        for(User user : users) {
            emailPreferencesSyncController.update(user);
        }
        return ret;

    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody User user) {

        if (user.getId() != null) {
            throw new ClientErrorException("It's not allowed to provide an id when creating an entity");
        }

        User userCreated = userRepo.saveAndFlush(user);
        emailPreferencesSyncController.update(userCreated);

        return new ResponseEntity(userCreated, HttpStatus.CREATED);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {

        User existing = userRepo.findOne(id);
        if (existing == null) {
            throw new ClientErrorException("Cannot update a non-existing user");
        }

        if (user.getId() == null) {
            user.setId(id);
        }

        if (!id.equals(user.getId())) {
            throw new ClientErrorException("Updated entity id is not equal of the id contained in the path");
        }

        return new ResponseEntity(userRepo.saveAndFlush(user), HttpStatus.CREATED);

    }


}
