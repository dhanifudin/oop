package id.ac.polinema.repository;

import id.ac.polinema.model.Account;
import java.util.Collection;

public interface AccountRepository {
    void save(Account account);

    Account findByNumber(String accountNumber);

    Collection<Account> findAll();
}
