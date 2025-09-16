package ma.enset.iibdcc.ebankingbackend.web;

import lombok.AllArgsConstructor;
import ma.enset.iibdcc.ebankingbackend.dtos.AccountHistoryDTO;
import ma.enset.iibdcc.ebankingbackend.dtos.AccountOperationDTO;
import ma.enset.iibdcc.ebankingbackend.dtos.BankAccountDTO;
import ma.enset.iibdcc.ebankingbackend.entities.BankAccount;
import ma.enset.iibdcc.ebankingbackend.exeptions.BanckAccountNoTFounExeption;
import ma.enset.iibdcc.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BanckAccountRestAPI {

    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) throws BanckAccountNoTFounExeption {
        return bankAccountService.getBankAccount(id);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{id}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable(name = "id") String id){
        return bankAccountService.accountHistory(id);
    }

    @GetMapping("/accounts/{id}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable(name = "id") String id,
                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name = "size",defaultValue = "5")int pageSize) throws BanckAccountNoTFounExeption {
         return bankAccountService.getAccountHistory(id,page,pageSize);
    }
}
