package com.tinqinacademy.bff.api.operations.comment.partialupdatecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class ContentUpdateCommentBFFInput implements OperationInput {
    @NotBlank(message = "commentId cannot be blank")
    @JsonIgnore
    private String commentId;

    @NotBlank(message = "Content cannot be blank")
    private String content;
}
