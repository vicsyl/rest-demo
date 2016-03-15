package org.virutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.virutor.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
