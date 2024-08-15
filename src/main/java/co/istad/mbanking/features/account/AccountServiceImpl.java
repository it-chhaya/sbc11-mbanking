package co.istad.mbanking.features.account;

import co.istad.mbanking.domain.Account;
import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.features.account.dto.CreateAccountRequest;
import co.istad.mbanking.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountMapper accountMapper;

    @Override
    public void createNew(CreateAccountRequest createAccountRequest) {

        AccountType accountType = accountTypeRepository
                .findByAlias(createAccountRequest.accountTypeAlias())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account type not found"
                ));

        if (accountRepository.existsByActNo(createAccountRequest.actNo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Account NO already exists");
        }

        Account account = accountMapper.fromCreateAccountRequest(createAccountRequest);
        account.setTransferLimit(BigDecimal.valueOf(5000));
        account.setAliasName("");
        account.setAccountType(accountType);
        account.setIsHidden(false);
        account.setIsDeleted(false);

        accountRepository.save(account);
    }

}
