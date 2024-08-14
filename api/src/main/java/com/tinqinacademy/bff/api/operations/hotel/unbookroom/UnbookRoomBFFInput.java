package com.tinqinacademy.bff.api.operations.hotel.unbookroom;

import com.tinqinacademy.bff.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UnbookRoomBFFInput implements OperationInput {
    @NotBlank(message = "Booking id cannot be null")
    private String bookingId;
}
