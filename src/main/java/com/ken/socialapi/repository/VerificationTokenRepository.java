package com.ken.socialapi.repository;

import com.ken.socialapi.models.User;
import com.ken.socialapi.models.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    boolean existsVerificationTokenByUser(User user);

    VerificationToken findByUser(User user);
}
