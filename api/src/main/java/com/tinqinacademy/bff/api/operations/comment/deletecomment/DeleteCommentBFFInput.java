package com.tinqinacademy.bff.api.operations.comment.deletecomment;

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
public class DeleteCommentBFFInput implements OperationInput {
    @NotBlank(message = "comment cannot be blank")
    @JsonIgnore
    private String commentId;
}
