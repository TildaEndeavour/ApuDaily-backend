package com.example.ApuDaily.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Test
    void createUser_shouldReturnDto_whenValidRequest() throws InterruptedException{
        Thread.sleep(200);
    }

    @Test
    void getUserDetailsById_shouldReturnDto_whenValidId() throws InterruptedException{
        Thread.sleep(250);
    }

    @Test
    void getUserDetailsById_shouldThrowErrorWith404_whenUserNotFound() throws InterruptedException{
        Thread.sleep(260);
    }

    @Test
    void updateUser_shouldReturnUpdatedDto_whenValidFields() throws InterruptedException{
        Thread.sleep(280);
    }

    @Test
    void updateUser_shouldThrowErrorWith400_whenNotDifferentFields() throws InterruptedException{
        Thread.sleep(300);
    }

    @Test
    void updateUser_shouldThrowErrorWith404_whenUserNotFound() throws InterruptedException{
        Thread.sleep(100);
    }

    @Test
    void deleteUser_shouldReturnVoid_whenValidId() throws InterruptedException{
        Thread.sleep(150);
    }

    @Test
    void deleteUser_shouldThrowErrorWith404_whenUserNotFound() throws InterruptedException{
        Thread.sleep(150);
    }
}
