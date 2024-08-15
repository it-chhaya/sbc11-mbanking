package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.CreateAccountRequest;

public interface AccountService {

    void createNew(CreateAccountRequest createAccountRequest);

}
