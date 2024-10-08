package com.tinqinacademy.bff.api.operations.hotel.getroomids;

import com.tinqinacademy.bff.api.operations.base.OperationInput;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetRoomIdsBFFInput implements OperationInput {
    @FutureOrPresent(message = "Date cannot be in the past")
    private LocalDate startDate;

    @FutureOrPresent(message = "Date cannot be in the past")
    private LocalDate endDate;

    private Optional<String> bedSize;

    private Optional<String> bathroomType;
}
