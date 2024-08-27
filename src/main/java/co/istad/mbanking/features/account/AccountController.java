package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.AccountDetailResponse;
import co.istad.mbanking.features.account.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{actNo}")
    AccountDetailResponse findByActNo(@PathVariable String actNo) {
        return accountService.findByActNo(actNo);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        accountService.createNew(createAccountRequest);
    }

}
