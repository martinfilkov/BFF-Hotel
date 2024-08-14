package com.tinqinacademy.bff.api.operations.hotel.roombyid;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RoomByIdBFFOutput implements OperationOutput {
    private String id;
    private BigDecimal price;
    private Integer floor;
    private List<String> bedSizes;
    private String bathroomType;
    private List<LocalDate> datesOccupied;
}
