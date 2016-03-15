package org.virutor.controller;

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

import java.util.Collection;
import java.util.List;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {

        return userRepo.findOne(id);

    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAll() {

        return userRepo.findAll();

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Collection<User> test() {

        return userRepo.save(User.users.values());

    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody User user) {

        if(user.getId() != null) {
            throw new ClientErrorException("It's not allowed to provide an id when creating an entity");
        }

        return new ResponseEntity(userRepo.saveAndFlush(user), HttpStatus.CREATED);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {

        User existing = userRepo.findOne(id);
        if(existing == null) {
            throw new ClientErrorException("Cannot update a non-existing user");
        }

        if(user.getId() == null) {
            user.setId(id);
        }

        if(!id.equals(user.getId())) {
            throw new ClientErrorException("Updated entity id is not equal of the id contained in the path");
        }

        return new ResponseEntity(userRepo.saveAndFlush(user), HttpStatus.CREATED);

    }


}
