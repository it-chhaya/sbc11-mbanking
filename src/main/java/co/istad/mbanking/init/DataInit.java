package co.istad.mbanking.init;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.domain.CardType;
import co.istad.mbanking.features.account.AccountTypeRepository;
import co.istad.mbanking.features.card.CardTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final CardTypeRepository cardTypeRepository;
    private final AccountTypeRepository accountTypeRepository;

    @PostConstruct
    void init() {
        initCardTypeData();
        initAccountTypeData();

        // Select data
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        accountTypes.forEach(System.out::println);

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
