package co.istad.mbanking.features.account;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.features.account.dto.AccountTypeResponse;
import co.istad.mbanking.mapper.AccountTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    @Override
    public List<AccountTypeResponse> findAll() {

        List<AccountType> accountTypes = accountTypeRepository.findAll();

        return accountTypeMapper.toAccountTypeResponseList(accountTypes);
    }
}
