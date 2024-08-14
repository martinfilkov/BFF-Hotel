package com.tinqinacademy.bff.api.operations.comment.updatecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class AdminUpdateCommentBFFInput implements OperationInput {
    @NotBlank(message = "CommentId cannot be blank")
    @JsonIgnore
    private String commentId;

    @UUID(message = "Room id must cover the UUID syntax")
    @NotBlank(message = "Room id not provided")
    private String roomId;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @JsonIgnore
    private String userId;
}
