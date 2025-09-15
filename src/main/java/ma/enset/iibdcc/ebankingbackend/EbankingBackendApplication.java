package ma.enset.iibdcc.ebankingbackend;

import ma.enset.iibdcc.ebankingbackend.dtos.BankAccountDTO;
import ma.enset.iibdcc.ebankingbackend.dtos.CurrentBankAccountDTO;
import ma.enset.iibdcc.ebankingbackend.dtos.CustomerDTO;
import ma.enset.iibdcc.ebankingbackend.dtos.SavingBankAccountDTO;
import ma.enset.iibdcc.ebankingbackend.entities.*;
import ma.enset.iibdcc.ebankingbackend.enums.AccountStatus;
import ma.enset.iibdcc.ebankingbackend.enums.OperationType;
import ma.enset.iibdcc.ebankingbackend.exeptions.BalanceNotSufficientExeption;
import ma.enset.iibdcc.ebankingbackend.exeptions.BanckAccountNoTFounExeption;
import ma.enset.iibdcc.ebankingbackend.exeptions.CustomerNotFoundExeption;
import ma.enset.iibdcc.ebankingbackend.repositories.AccountOperationRepository;
import ma.enset.iibdcc.ebankingbackend.repositories.BankAccountRepository;
import ma.enset.iibdcc.ebankingbackend.repositories.CustomerRepository;
import ma.enset.iibdcc.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    //Test couche service
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            Stream.of("Souhaib","Hamza","Mohammed")
                    .forEach(name -> {
                        CustomerDTO customer = new CustomerDTO();
                        customer.setName(name);
                        customer.setEmail(name + "@gmail.com");
                        System.out.println(customer);
                        bankAccountService.saveCustmor(customer);
                    });

            bankAccountService.listCustomers().forEach(
                    customer -> {
                        try {
                            bankAccountService.saveCurentBankAccount(Math.random()+90000,2000, customer.getId());
                            bankAccountService.saveSavingBankAccount(Math.random()+30000,5.5, customer.getId());

                        } catch (CustomerNotFoundExeption e) {
                            e.printStackTrace();
                        }
                    });
            List<BankAccountDTO> bankAccountList=bankAccountService.bankAccountList();
            for (BankAccountDTO account : bankAccountList) {
                for (int i = 0; i <100 ; i++) {
                    String accountId ;
                    if (account instanceof SavingBankAccountDTO) {
                        accountId=((SavingBankAccountDTO) account).getId();
                    }else {
                        accountId=((CurrentBankAccountDTO) account).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*12000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }
            }
        };
    }


    //@Bean
    CommandLineRunner start  (AccountOperationRepository repository,
                              BankAccountRepository bankAccountRepository,
                              CustomerRepository customerRepository,
                              AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Souhaib","Hamza","Mohammed")
                    .forEach(name -> {
                        Customer customer = new Customer();
                        customer.setName(name);
                        customer.setEmail(name + "@gmail.com");
                        customerRepository.save(customer);
                    });
            customerRepository.findAll().forEach(
                    customer -> {
                        CurrentAccount currentAccount = new CurrentAccount();
                        currentAccount.setId(UUID.randomUUID().toString());
                        currentAccount.setCreatedAt(new Date());
                        currentAccount.setBalance(Math.random() * 70200);
                        currentAccount.setStatus(AccountStatus.CREATED);
                        currentAccount.setCustomer(customer);
                        currentAccount.setOverDraft(70200);
                        bankAccountRepository.save(currentAccount);

                        SavingAccount savingAccount = new SavingAccount();
                        savingAccount.setId(UUID.randomUUID().toString());
                        savingAccount.setCreatedAt(new Date());
                        savingAccount.setBalance(Math.random() * 70200);
                        savingAccount.setStatus(AccountStatus.CREATED);
                        savingAccount.setCustomer(customer);
                        savingAccount.setInterestRate(5.2);
                        bankAccountRepository.save(savingAccount);

                    }
            );

            bankAccountRepository.findAll().forEach(
                    bankAccount -> {
                        for (int i = 0; i <10 ; i++) {
                            AccountOperation accountOperation = new AccountOperation();
                            accountOperation.setOprationDate(new Date());
                            accountOperation.setAmount(Math.random() * 12500);
                            accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                            accountOperation.setBankAccount(bankAccount);
                            accountOperationRepository.save(accountOperation);
                        }

                    }
            );
        };
    }
}
