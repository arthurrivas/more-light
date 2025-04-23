package br.com.more_light.mapper;

import br.com.more_light.domain.Account;
import br.com.more_light.dto.AccountDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account accountDtoToAccount(AccountDTO accountDTO);
    AccountDTO accountToAccountDTO(Account account);
}
