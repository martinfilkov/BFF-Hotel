package com.tinqinacademy.bff.api.operations.hotel.inforregister;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class InfoRegisterBFFOutputList implements OperationOutput {
    List<InfoRegisterBFFOutput> visitors;
}
