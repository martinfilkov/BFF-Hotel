package com.tinqinacademy.bff.api.operations.hotel.updateroom;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UpdateRoomBFFOutput implements OperationOutput {
    private String id;
}
