package com.tinqinacademy.bff.api.operations.hotel.createroom;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreateRoomBFFOutput implements OperationOutput {
    private String id;
}
