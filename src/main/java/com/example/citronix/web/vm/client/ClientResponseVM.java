package com.example.citronix.web.vm.client;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientResponseVM {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
}
