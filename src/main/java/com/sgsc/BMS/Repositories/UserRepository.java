package com.sgsc.BMS.Repositories;

import com.sgsc.BMS.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> { // if u want to use JPA create interface of repos
    @Override
    Optional<User> findById(Long aLong);
    //Optional cause user might not be present


    Optional<User> findByEmail(String email);

    @Override
    User save(User user);
}
