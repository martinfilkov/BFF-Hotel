package com.tinqinacademy.bff.api.operations.comment.updatecomment;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminUpdateCommentBFFOutput implements OperationOutput {
    private String id;
}
