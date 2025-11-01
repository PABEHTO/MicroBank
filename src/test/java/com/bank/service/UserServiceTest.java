package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.entity.UserRole;
import com.bank.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  AccountService accountService;
    @InjectMocks
    public UserService userService;

    @Test
    public void deleteUserTest() {
        //Arrangement
        User user = new User("VLAD", "PIXEL@mail.ru", "89ikijk", UserRole.USER);
        user.setId(1000);

        //Act
        userService.deleteUser(user);

        //Assert
        verify(userRepository, times(1)).delete(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void saveUserTest() {
        //Arrangement
        User user = new User("VLAD", "PIXEL@mail.ru", "89ikijk", UserRole.USER);
        Account account = new Account();
        when(accountService.createAccount()).thenReturn(account);

        //Act
        assertDoesNotThrow(() -> userService.saveUser(user));

        //Assert
        verify(accountService).createAccount();
        verify(userRepository).save(user);
        assertEquals(account, user.getAccount());
    }

    @Test
    public void getUser_shouldReturnCurrentUser() {
        //Arrangement
        User user1 = new User("Vlad", "pixel@mail.ru", "89ikoik", UserRole.USER);
        String email = user1.getEmail();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(email);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user1));

        //Act
        User result = userService.getTheUser();

        //Assert
        assertEquals(user1, result);
        verify(userRepository, times(1)).findByEmail(email);

        SecurityContextHolder.clearContext();
    }
}
