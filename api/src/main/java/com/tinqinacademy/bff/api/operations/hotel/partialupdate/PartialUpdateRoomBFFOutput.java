package com.tinqinacademy.bff.api.operations.hotel.partialupdate;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PartialUpdateRoomBFFOutput implements OperationOutput {
    private String id;
}
