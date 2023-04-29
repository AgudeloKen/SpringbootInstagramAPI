package com.ken.socialapi.services;

import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.PasswordResetToken;
import com.ken.socialapi.models.User;
import com.ken.socialapi.repository.PasswordResetTokenRepository;
import com.ken.socialapi.repository.UserRepository;
import com.ken.socialapi.requests.PasswordResetRequest;
import com.ken.socialapi.responses.EmailResponse;
import com.ken.socialapi.responses.PasswordResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    @Value("${url.password-reset}")
    private String url;

    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder){
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<PasswordResponse> resetPassword(String token, PasswordResetRequest request) throws UserException {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null){
            return ResponseEntity.status(404).body(new PasswordResponse("Not found."));
        }
        User user = passwordResetToken.getUser();
        if(user == null){
            throw new UserException("User not found.");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        passwordResetToken.setUser(null);
        passwordResetTokenRepository.delete(passwordResetToken);
        userRepository.save(user);

        return ResponseEntity.ok(new PasswordResponse("Password has been changed."));
    }

    public ResponseEntity<EmailResponse> sendPasswordResetEmail(Long id) throws UserException, MessagingException {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserException("User not found.");
        }
        createPasswordResetToken(user);
        return sendPasswordResetEmail(user);
    }

    private void createPasswordResetToken(User user){
        if(!passwordResetTokenRepository.existsTokenByUser(user)){
            PasswordResetToken passwordResetToken = new PasswordResetToken();
            passwordResetToken.setToken(UUID.randomUUID().toString());
            passwordResetToken.setUser(user);
            passwordResetTokenRepository.save(passwordResetToken);
        }
    }

    private ResponseEntity<EmailResponse> sendPasswordResetEmail(User user) throws MessagingException {
        String token = passwordResetTokenRepository.findByUser(user).getToken();

        final String template =
                """
                    <div
                        style="display: flex; justify-content: center; align-items: center; flex-direction: column; background-color: #ddd; padding: 30px 20px; text-align: center;">
                """
                        +
                        """
                                <h1 style="font-size: 2rem;">
                        """
                        + "Password reset" +
                        """ 
                                </h1>
                                <a href="
                        """
                        + url + token +
                        """
                                " style="padding: 8px 20px; background-color: #000; color: #fff; border-radius: 15px; text-decoration: none;">
                                 """ + "Reset password" + """
                                 </a>
                            </div>
                        """;

        if(token != null){
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Password reset");
            helper.setText(template, true);

            javaMailSender.send(message);
            return ResponseEntity.ok(new EmailResponse("Email send"));

        }
        return ResponseEntity.badRequest().body(new EmailResponse("Token is invalid."));
    }


}
