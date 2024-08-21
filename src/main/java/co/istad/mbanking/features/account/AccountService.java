package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.AccountDetailResponse;
import co.istad.mbanking.features.account.dto.CreateAccountRequest;

public interface AccountService {

    AccountDetailResponse findByActNo(String actNo);

    void createNew(CreateAccountRequest createAccountRequest);

}
