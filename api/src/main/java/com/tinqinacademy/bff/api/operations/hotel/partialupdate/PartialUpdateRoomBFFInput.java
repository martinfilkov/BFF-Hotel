package com.tinqinacademy.bff.api.operations.hotel.partialupdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class PartialUpdateRoomBFFInput implements OperationInput {
    @NotBlank(message = "roomId cannot be null")
    @JsonIgnore
    private String roomId;

    private List<String> bedSizes;

    private String bathRoomType;

    private String roomNumber;

    @PositiveOrZero(message = "Price must be positive or 0")
    private BigDecimal price;
}
