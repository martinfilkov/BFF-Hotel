package com.tinqinacademy.bff.api.operations.comment.partialupdatecomment;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ContentUpdateCommentBFFOutput implements OperationOutput {
    private String id;
}
