package com.tinqinacademy.bff.api.operations.comment.getcomments;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsBFFOutputList implements OperationOutput {
    List<GetCommentsBFFOutput> comments;
}
