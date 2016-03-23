package org.virutor.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.virutor.domain.User;
import org.virutor.repository.UserRepository;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@RequestMapping(value = "/user")
@RestController
public class UserController {

    private static final String USERS = "Users";

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailPreferencesSyncController emailPreferencesSyncController;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public User get(@PathVariable Long id) {
        return userRepo.findOne(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Map<String, List<User>> getAll() {

        List<User> all = userRepo.findAll();
        return ImmutableMap.of(USERS, all);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody User user) {

        if (user.getId() != null) {
            throw new ClientErrorException("It's not allowed to provide an id when creating an entity");
        }

        User userCreated = userRepo.saveAndFlush(user);
        emailPreferencesSyncController.add(userCreated);

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

        User newUser = userRepo.saveAndFlush(user);
        emailPreferencesSyncController.update(newUser);

        return new ResponseEntity(newUser, HttpStatus.CREATED);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        userRepo.delete(id);
        emailPreferencesSyncController.delete(id);
    }


}
