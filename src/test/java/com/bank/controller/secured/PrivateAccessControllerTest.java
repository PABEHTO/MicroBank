package com.bank.controller.secured;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.entity.UserRole;
import com.bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrivateAccessControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private PrivateAccessController controller;

    @Test
    void getMyPage() {
        //Arrange
        String actual = "private/personal-page";
        Model model = Mockito.mock(Model.class);
        User user = new User("Test", "test@mail.com", "test", UserRole.USER);
        Account account = Mockito.mock(Account.class);
        user.setAccount(account);
        when(userService.getTheUser()).thenReturn(user);

        //Act
        String result = controller.getMyPage(model);

        //Assert
        assertEquals(actual, result);
        verify(model).addAttribute("myName", user.getUsername());
        verify(model).addAttribute("myAccount", user.getAccount());
    }
}