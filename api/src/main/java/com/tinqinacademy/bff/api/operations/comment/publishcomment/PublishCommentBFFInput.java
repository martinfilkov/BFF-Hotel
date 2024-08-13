package com.tinqinacademy.bff.api.operations.comment.publishcomment;

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
public class PublishCommentBFFInput implements OperationInput {
    @NotBlank(message = "roomId cannot be blank")
    @JsonIgnore
    private String roomId;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotBlank(message = "User id cannot be blank")
    private String userId;
}
