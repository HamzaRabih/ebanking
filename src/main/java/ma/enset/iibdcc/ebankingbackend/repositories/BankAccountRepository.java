package ma.enset.iibdcc.ebankingbackend.repositories;

import ma.enset.iibdcc.ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
