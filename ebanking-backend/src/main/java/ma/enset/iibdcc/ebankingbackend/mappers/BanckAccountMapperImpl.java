package ma.enset.iibdcc.ebankingbackend.mappers;

import lombok.AllArgsConstructor;
import ma.enset.iibdcc.ebankingbackend.dtos.AccountOperationDTO;
import ma.enset.iibdcc.ebankingbackend.dtos.CurrentBankAccountDTO;
import ma.enset.iibdcc.ebankingbackend.dtos.CustomerDTO;
import ma.enset.iibdcc.ebankingbackend.dtos.SavingBankAccountDTO;
import ma.enset.iibdcc.ebankingbackend.entities.*;
import ma.enset.iibdcc.ebankingbackend.repositories.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

//MapStruct , Jmapper (Framework)
@Service
@AllArgsConstructor
public class BanckAccountMapperImpl {

    private final CustomerRepository customerRepository;


    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public SavingBankAccountDTO fromSavingBankAccount( SavingAccount savingAccount) {
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO() ;
        BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
        savingBankAccountDTO.setCustomer(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;
    }

    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO) {
        SavingAccount savingAccount=new SavingAccount() ;
        BeanUtils.copyProperties(savingBankAccountDTO, savingAccount );
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomer()));
        return savingAccount;
    }


    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
        currentBankAccountDTO.setCustomer(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());

        return currentBankAccountDTO;
    }

    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO) {
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO, currentAccount );
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomer()));
        return currentAccount;
    }

    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;
    }

}
