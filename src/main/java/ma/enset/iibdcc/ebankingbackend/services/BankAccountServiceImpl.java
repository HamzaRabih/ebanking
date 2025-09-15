package ma.enset.iibdcc.ebankingbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.iibdcc.ebankingbackend.dtos.*;
import ma.enset.iibdcc.ebankingbackend.entities.*;
import ma.enset.iibdcc.ebankingbackend.enums.OperationType;
import ma.enset.iibdcc.ebankingbackend.exeptions.BalanceNotSufficientExeption;
import ma.enset.iibdcc.ebankingbackend.exeptions.BanckAccountNoTFounExeption;
import ma.enset.iibdcc.ebankingbackend.exeptions.CustomerNotFoundExeption;
import ma.enset.iibdcc.ebankingbackend.mappers.BanckAccountMapperImpl;
import ma.enset.iibdcc.ebankingbackend.repositories.AccountOperationRepository;
import ma.enset.iibdcc.ebankingbackend.repositories.BankAccountRepository;
import ma.enset.iibdcc.ebankingbackend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
//log4j & slf4j :log Frameworks
@Slf4j//pour ajouter ce ligne par Lommbok   //Logger log = LoggerFactory.getLogger(this.getClass().getName());
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BanckAccountMapperImpl dtoMapper;

    //Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public CustomerDTO saveCustmor(CustomerDTO customerDTO) {
        log.info("Saving a new customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustmor(CustomerDTO customerDTO) {
        log.info("Saving a new customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustmor(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public CurrentBankAccountDTO saveCurentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundExeption {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundExeption("Customer not found");

        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedbankAccount=bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedbankAccount);
    }


    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundExeption {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundExeption("Customer not found");

        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedbankAccount=bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedbankAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customerList=customerRepository.findAll();
        List<CustomerDTO> customerDTOList=customerList.stream()
                .map(customer -> dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        return customerDTOList;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BanckAccountNoTFounExeption {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BanckAccountNoTFounExeption("BankAccount not found"));
        if (bankAccount instanceof SavingAccount){
            SavingAccount savingAccount=(SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);
        }else {
            CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BanckAccountNoTFounExeption, BalanceNotSufficientExeption {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BanckAccountNoTFounExeption("BankAccount not found"));
        if (bankAccount.getBalance() < amount)
            throw new BalanceNotSufficientExeption("Balance not sufficient");

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setOprationDate(new Date());
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BanckAccountNoTFounExeption {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BanckAccountNoTFounExeption("BankAccount not found"));

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setOprationDate(new Date());
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BanckAccountNoTFounExeption, BalanceNotSufficientExeption {
        debit(accountIdSource,amount,"Transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from"+accountIdSource);
    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccountList=bankAccountRepository.findAll();

        List<BankAccountDTO> bankAccountDTOS= bankAccountList.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount){
                SavingAccount savingAccount=(SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            }else {
                CurrentAccount currentAccount=(CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundExeption {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundExeption("Customer not found"));
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId);
        return  accountOperations.stream().map(
                op->dtoMapper.fromAccountOperation(op)
        ).collect(Collectors.toList());

    }

    @Override
    public AccountHistoryDTO getAccountHistory(String id, int page, int pageSize) throws BanckAccountNoTFounExeption {
        BankAccount bankAccount=bankAccountRepository.findById(id).orElse(null);
        if (bankAccount == null) throw new BanckAccountNoTFounExeption("Account not found");
        Page<AccountOperation> accountOperations=accountOperationRepository.findByBankAccountId(id, PageRequest.of(page, pageSize));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS= accountOperations.getContent().stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(pageSize);
        accountHistoryDTO.setTotalePages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }


}
