package com.tinqinacademy.bff.api.operations.comment.publishcomment;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PublishCommentBFFOutput implements OperationOutput {
    private String id;
}
