package co.istad.mbanking.features.auth;

import co.istad.mbanking.domain.EmailVerification;
import co.istad.mbanking.domain.Role;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.auth.dto.RegisterRequest;
import co.istad.mbanking.features.auth.dto.VerifyRequest;
import co.istad.mbanking.features.user.UserRepository;
import co.istad.mbanking.mapper.UserMapper;
import co.istad.mbanking.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String adminMail;


    @Override
    public void verify(VerifyRequest verifyRequest) {

        User user = userRepository
                .findByEmail(verifyRequest.email())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Email doesn't exist"
                ));

        EmailVerification emailVerification = emailVerificationRepository
                .findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Verification failed"
                ));

        if (!emailVerification.getVerificationCode().equals(verifyRequest.verificationCode())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Verification code is invalid");
        }

        if (LocalTime.now().isAfter(emailVerification.getExpiryTime())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Verification code is expired");
        }

        user.setIsVerified(true);
        user.setIsDeleted(false);
        userRepository.save(user);
    }


    @Override
    public void register(RegisterRequest registerRequest) throws MessagingException {

        // Validate national Card ID
        if (userRepository.isNationalCardIdExisted(registerRequest.nationalCardId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "National card ID already exists");
        }

        // Validate phone number
        if (userRepository.existsByPhoneNumber(registerRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Phone number already exists");
        }

        // Validate email
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Email already exists");
        }

        // Validate password and confirmed password
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Passwords do not match");
        }

        // Transfer data from DTO to Domain Model
        User user = userMapper.fromRegisterRequest(registerRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setIsAccountNonLocked(true);
        user.setIsAccountNonExpired(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsVerified(false);
        user.setIsBlocked(false);
        user.setIsDeleted(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setProfileImage("user-avatar.png");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(1).orElseThrow());
        roles.add(roleRepository.findById(2).orElseThrow());
        user.setRoles(roles);

        user = userRepository.save(user);

        // Send email for verification

        // Step 1. Prepare email verification data
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setVerificationCode(RandomUtil.random6Digits());
        emailVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        emailVerification.setUser(user);

        emailVerificationRepository.save(emailVerification);

        // Step 2. Prepare to send mail

        String myHtml = String.format("""
                <h1>MBanking - Email Verification</h1>
                <hr/>
                %s
                """, emailVerification.getVerificationCode());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Email Verification - MBanking");
        helper.setTo(user.getEmail());
        helper.setFrom(adminMail);
        helper.setText(myHtml, true);

        javaMailSender.send(mimeMessage);
    }

}
