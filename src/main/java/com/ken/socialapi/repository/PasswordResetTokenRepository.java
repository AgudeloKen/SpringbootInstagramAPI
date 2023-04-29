package com.ken.socialapi.repository;

import com.ken.socialapi.models.PasswordResetToken;
import com.ken.socialapi.models.User;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    boolean existsTokenByUser(User user);

    PasswordResetToken findByUser(User user);

}
