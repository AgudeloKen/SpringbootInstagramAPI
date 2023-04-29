package com.ken.socialapi.repository;

import com.ken.socialapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findUserByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAllByUsernameContaining(String query);
}
