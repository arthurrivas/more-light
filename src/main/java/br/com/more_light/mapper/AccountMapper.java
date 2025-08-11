package br.com.more_light.mapper;

import br.com.more_light.domain.Account;
import br.com.more_light.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "person", source = "person")
    Account accountDtoToAccount(AccountDTO accountDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "person", source = "person")
    AccountDTO accountToAccountDTO(Account account);
}
