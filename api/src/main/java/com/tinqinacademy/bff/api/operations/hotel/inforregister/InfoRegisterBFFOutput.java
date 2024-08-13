package com.tinqinacademy.bff.api.operations.hotel.inforregister;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class InfoRegisterBFFOutput implements OperationOutput {
    private LocalDate startDate;
    private LocalDate endDate;
    private String firstName;
    private String lastName;
    private String phone;
    private String idCardNumber;
    private String idCardValidity;
    private String idCardIssueAuthority;
    private String idCardIssueDate;
}
