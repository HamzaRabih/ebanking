package ma.enset.iibdcc.ebankingbackend.web;

import lombok.AllArgsConstructor;
import ma.enset.iibdcc.ebankingbackend.dtos.*;
import ma.enset.iibdcc.ebankingbackend.entities.BankAccount;
import ma.enset.iibdcc.ebankingbackend.exeptions.BalanceNotSufficientExeption;
import ma.enset.iibdcc.ebankingbackend.exeptions.BanckAccountNoTFounExeption;
import ma.enset.iibdcc.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestAPI {

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


    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody  DebitDTO debitDTO) throws BanckAccountNoTFounExeption, BalanceNotSufficientExeption {
        bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }

    @PostMapping("/accounts/credit")
    public CreditDTO debit(@RequestBody  CreditDTO creditDTO) throws BanckAccountNoTFounExeption {
        bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public TransferRequestDTO debit(@RequestBody  TransferRequestDTO transferRequestDTO) throws BanckAccountNoTFounExeption, BalanceNotSufficientExeption {
        bankAccountService.transfer(transferRequestDTO.getAccountSource(),transferRequestDTO.getAccountDestination(),transferRequestDTO.getAmount());
        return transferRequestDTO;
    }

}
