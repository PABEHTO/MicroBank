package com.bank.controller.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PublicHomeControllerTest {

    @Test
    void getHomePage() {
        //Arrange
        String expected = "public/home-page";

        //Act
        String actual = new PublicHomeController().getHomePage();

        //Assert
        assertEquals(expected, actual);
    }
}