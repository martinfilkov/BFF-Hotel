package com.tinqinacademy.bff.api.operations.hotel.roombyid;

import com.tinqinacademy.bff.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RoomByIdBFFInput implements OperationInput {
    @NotBlank(message = "Id cannot be empty")
    private String id;
}
