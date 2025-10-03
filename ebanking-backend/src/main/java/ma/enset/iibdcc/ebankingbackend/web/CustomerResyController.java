package ma.enset.iibdcc.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.iibdcc.ebankingbackend.dtos.CustomerDTO;
import ma.enset.iibdcc.ebankingbackend.exeptions.CustomerNotFoundExeption;
import ma.enset.iibdcc.ebankingbackend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerResyController {

    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> customerList() {
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword) {
        return bankAccountService.searchCustomers("%"+keyword+"%");
    }

    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundExeption {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customer){
        return bankAccountService.saveCustmor(customer);
    }

    @PutMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customer) {
        customer.setId(id);
        return bankAccountService.updateCustmor(customer);
    }

    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustmor(id);
    }

}
