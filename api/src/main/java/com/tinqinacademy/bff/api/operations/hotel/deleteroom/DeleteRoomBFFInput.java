package com.tinqinacademy.bff.api.operations.hotel.deleteroom;

import com.tinqinacademy.bff.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeleteRoomBFFInput implements OperationInput {
    @NotBlank(message = "Id cannot be null")
    private String id;
}
