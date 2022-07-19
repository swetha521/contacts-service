package com.test.contact.controller;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
public class ContactRequestDTO {
    @NotNull(message = "firstName cannot be null")
    private String firstName;
    @NotNull(message = "lastName cannot be null")
    private String lastName;
    @NotEmpty (message = "phoneNumbers cannot be empty")
    private List<String> phoneNumbers;
    @NotNull(message = "address cannot be null")
    private String address;

    @Override
    public String toString() {
        return "ContactRequestDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", address='" + address + '\'' +
                '}';
    }
}
