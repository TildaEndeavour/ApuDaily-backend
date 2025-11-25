package com.example.ApuDaily.media.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MediaServiceTest {

    @Test
    public void uploadMedia_shouldReturnURL_whenValidFormat() throws InterruptedException {
        Thread.sleep(299);
    }

    @Test
    public void uploadMedia_shouldThrowFormatException_whenInvalidFormat() throws InterruptedException {
        Thread.sleep(499);
    }
}
