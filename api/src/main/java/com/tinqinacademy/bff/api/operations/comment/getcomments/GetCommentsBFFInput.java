package com.tinqinacademy.bff.api.operations.comment.getcomments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsBFFInput implements OperationInput {
    @NotBlank(message = "roomId cannot be blank")
    @JsonIgnore
    private String roomId;
}
