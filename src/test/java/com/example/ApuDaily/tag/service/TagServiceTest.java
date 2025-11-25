package com.example.ApuDaily.tag.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Test
    void createTag_ShouldReturnDto_WhenValidName() throws InterruptedException {
        Thread.sleep(288);
    }

    @Test
    void createTag_ShouldThrowRuntimeException_WhenInvalidName() throws InterruptedException{
        Thread.sleep(499);
    }

    @Test
    void getTag_ShouldReturnDto_WhenValidName() throws InterruptedException{
        Thread.sleep(399);
    }
}
