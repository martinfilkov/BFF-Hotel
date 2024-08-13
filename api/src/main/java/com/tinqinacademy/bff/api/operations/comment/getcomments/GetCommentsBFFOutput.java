package com.tinqinacademy.bff.api.operations.comment.getcomments;

import com.tinqinacademy.bff.api.operations.base.OperationOutput;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsBFFOutput implements OperationOutput {
    private String id;
    private String firstName;
    private String lastName;
    private String content;
    private LocalDateTime publishDate;
    private LocalDateTime lastEditedDate;
    private UUID lastEditedBy;
}
