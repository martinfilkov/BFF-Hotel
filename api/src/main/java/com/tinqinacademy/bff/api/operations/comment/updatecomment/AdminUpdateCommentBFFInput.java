package com.tinqinacademy.bff.api.operations.comment.updatecomment;

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
public class AdminUpdateCommentBFFInput implements OperationInput {
    @NotBlank(message = "commentId cannot be blank")
    @JsonIgnore
    private String commentId;
    private String roomNumber;
    private String firstName;
    private String lastName;
    private String content;
}
