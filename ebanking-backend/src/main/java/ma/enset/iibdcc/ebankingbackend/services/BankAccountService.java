package ma.enset.iibdcc.ebankingbackend.services;

import ma.enset.iibdcc.ebankingbackend.dtos.*;
import ma.enset.iibdcc.ebankingbackend.entities.BankAccount;
import ma.enset.iibdcc.ebankingbackend.entities.CurrentAccount;
import ma.enset.iibdcc.ebankingbackend.entities.Customer;
import ma.enset.iibdcc.ebankingbackend.entities.SavingAccount;
import ma.enset.iibdcc.ebankingbackend.exeptions.BalanceNotSufficientExeption;
import ma.enset.iibdcc.ebankingbackend.exeptions.BanckAccountNoTFounExeption;
import ma.enset.iibdcc.ebankingbackend.exeptions.CustomerNotFoundExeption;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustmor(CustomerDTO customerDTO);

    CustomerDTO updateCustmor(CustomerDTO customerDTO);

    void deleteCustmor(Long customerId);

    CurrentBankAccountDTO saveCurentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundExeption;

    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundExeption;

    List<CustomerDTO> listCustomers();

    BankAccountDTO getBankAccount(String accountId) throws BanckAccountNoTFounExeption;

    void debit(String accountId, double amount, String description) throws BanckAccountNoTFounExeption, BalanceNotSufficientExeption;

    void credit(String accountId, double amount, String description) throws BanckAccountNoTFounExeption;

    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BanckAccountNoTFounExeption, BalanceNotSufficientExeption;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundExeption;

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String id, int page, int pageSize) throws BanckAccountNoTFounExeption;

    List<CustomerDTO> searchCustomers(String keyword);
}
