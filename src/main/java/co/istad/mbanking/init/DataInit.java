package co.istad.mbanking.init;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.domain.CardType;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.account.AccountTypeRepository;
import co.istad.mbanking.features.card.CardTypeRepository;
import co.istad.mbanking.features.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final CardTypeRepository cardTypeRepository;
    private final AccountTypeRepository accountTypeRepository;

    @PostConstruct
    void init() {
        initCardTypeData();
        initAccountTypeData();
        initUserData();
    }

    private void initUserData() {
        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setEmail("admin@mbanking.com");
        user.setPhoneNumber("098459947");
        user.setPassword("pwd");
        user.setPin("1234");
        user.setProfileImage("profile-admin.jpg");
        user.setNationalCardId("123456789");
        user.setGender("Male");
        user.setName("ADMIN");
        user.setDob(LocalDate.of(1999, 12, 31));
        user.setCreatedAt(LocalDateTime.now());
        user.setIsDeleted(false);
        user.setIsBlocked(false);
        user.setIsVerified(true);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);

        userRepository.save(user);
    }

    private void initAccountTypeData() {
        AccountType saving = new AccountType();
        saving.setName("Saving Account");
        saving.setAlias("saving-account");
        saving.setDescription("Saving Account");
        saving.setIsDeleted(false);
        AccountType payroll = new AccountType();
        payroll.setName("Payroll Account");
        payroll.setAlias("payroll-account");
        payroll.setDescription("Payroll Account");
        payroll.setIsDeleted(false);
        AccountType current = new AccountType();
        current.setName("Current Account");
        current.setAlias("current-account");
        current.setDescription("Current Account");
        current.setIsDeleted(true);

        accountTypeRepository.saveAll(List.of(saving, payroll, current));
    }

    private void initCardTypeData() {
        CardType visa = new CardType();
        visa.setName("Visa");
        visa.setAlias("visa");
        visa.setIsDeleted(false);
        CardType mastercard = new CardType();
        mastercard.setName("Mastercard");
        mastercard.setAlias("mastercard");
        mastercard.setIsDeleted(false);

        /*cardTypeRepository.save(visa);
        cardTypeRepository.save(mastercard);*/
        cardTypeRepository.saveAll(List.of(visa, mastercard));
    }

}
