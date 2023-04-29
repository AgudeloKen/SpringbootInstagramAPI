package com.ken.socialapi.services;

import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.User;
import com.ken.socialapi.models.VerificationToken;
import com.ken.socialapi.repository.UserRepository;
import com.ken.socialapi.repository.VerificationTokenRepository;
import com.ken.socialapi.responses.EmailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EmailVerificationService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    @Value("${url.email-verify}")
    private String url;

    public EmailVerificationService(VerificationTokenRepository verificationTokenRepository, JavaMailSender javaMailSender, UserRepository userRepository){
        this.verificationTokenRepository = verificationTokenRepository;
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    private void createVerifyToken(User user){
        if(!verificationTokenRepository.existsVerificationTokenByUser(user)){
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(UUID.randomUUID().toString());
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
        }
    }


    public ResponseEntity<EmailResponse> sendVerifyEmail(Long id) throws MessagingException, UserException {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserException("User not found.");
        }
        createVerifyToken(user);
        if(user.getVerifiedAt() == null){
            return sendEmailVerify(user);
        }else{
            throw new UserException("Email is already verified.");
        }
    }


    private ResponseEntity<EmailResponse> sendEmailVerify(User user) throws MessagingException {
        String token = verificationTokenRepository.findByUser(user).getToken();

        final String template =
                """
                    <div
                        style="display: flex; justify-content: center; align-items: center; flex-direction: column; background-color: #ddd; padding: 30px 20px; text-align: center;">
                """
                        +
                        """
                                <h1 style="font-size: 2rem;">
                        """
                        + "Email address verification" +
                        """ 
                                </h1>
                                <a href="
                        """
                        + url + token +
                        """
                                " style="padding: 8px 20px; background-color: #000; color: #fff; border-radius: 15px; text-decoration: none;">
                                 """ + "VERIFY" + """
                                 </a>
                            </div>
                        """;

        if(token != null){
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Email Verification");
            helper.setText(template, true);

            javaMailSender.send(message);
            return ResponseEntity.ok(new EmailResponse("Email send"));

        }
        return ResponseEntity.badRequest().body(new EmailResponse("Token is invalid."));
    }

    public ResponseEntity<EmailResponse> verifyEmail(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken != null){
            User user = verificationToken.getUser();
            if(user.getVerifiedAt() == null){
                user.setVerifiedAt(new Date());
                verificationToken.setUser(null);
                verificationTokenRepository.delete(verificationToken);
                userRepository.save(user);
                return ResponseEntity.ok(new EmailResponse("Email successfully verified."));
            }
        }
        return ResponseEntity.badRequest().body(new EmailResponse("Token is invalid."));
    }
}
