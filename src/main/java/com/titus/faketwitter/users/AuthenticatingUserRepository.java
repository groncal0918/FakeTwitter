package com.titus.faketwitter.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticatingUserRepository extends CrudRepository<AuthenticatingUser, Long> {

}
