package com.tinqinacademy.bff.api.operations.hotel.getroomids;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetRoomIdsBFFOutput implements OperationOutput {
    List<String> ids;
}
