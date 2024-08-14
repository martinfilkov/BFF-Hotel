package com.tinqinacademy.bff.rest.controllers;


import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.comment.deletecomment.DeleteCommentBFFInput;
import com.tinqinacademy.bff.api.operations.comment.deletecomment.DeleteCommentBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.deletecomment.DeleteCommentBFFOutput;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFInput;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFOutputList;
import com.tinqinacademy.bff.api.operations.comment.partialupdatecomment.ContentUpdateCommentBFFInput;
import com.tinqinacademy.bff.api.operations.comment.partialupdatecomment.ContentUpdateCommentBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.partialupdatecomment.ContentUpdateCommentBFFOutput;
import com.tinqinacademy.bff.api.operations.comment.publishcomment.PublishCommentBFFInput;
import com.tinqinacademy.bff.api.operations.comment.publishcomment.PublishCommentBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.publishcomment.PublishCommentBFFOutput;
import com.tinqinacademy.bff.api.operations.comment.updatecomment.AdminUpdateCommentBFFInput;
import com.tinqinacademy.bff.api.operations.comment.updatecomment.AdminUpdateCommentBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.updatecomment.AdminUpdateCommentBFFOutput;
import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController extends BaseController{
    private final GetCommentsBFFOperation getCommentsOperation;
    private final PublishCommentBFFOperation publishCommentOperation;
    private final ContentUpdateCommentBFFOperation contentUpdateCommentOperation;
    private final AdminUpdateCommentBFFOperation adminUpdateCommentOperation;
    private final DeleteCommentBFFOperation deleteCommentOperation;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned comments"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @GetMapping(CommentMappings.GET_COMMENTS)
    public ResponseEntity<?> getComments(@PathVariable("roomId") String id) {
        GetCommentsBFFInput input = GetCommentsBFFInput.builder()
                .roomId(id)
                .build();

        Either<Errors, GetCommentsBFFOutputList> output = getCommentsOperation.process(input);
        return handleResponse(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully published a comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PostMapping(CommentMappings.PUBLISH_COMMENT)
    public ResponseEntity<?> publishComment(
            @PathVariable("roomId") String id,
            @RequestBody PublishCommentBFFInput request) {
        PublishCommentBFFInput input = request.toBuilder()
                .roomId(id)
                .build();

        Either<Errors, PublishCommentBFFOutput> output = publishCommentOperation.process(input);

        return handleResponse(output, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PutMapping(CommentMappings.CONTENT_UPDATE_COMMENT)
    public ResponseEntity<?> contentUpdate(
            @PathVariable("commentId") String id,
            @RequestBody ContentUpdateCommentBFFInput request
    ) {
        ContentUpdateCommentBFFInput input = request.toBuilder()
                .commentId(id)
                .build();

        Either<Errors, ContentUpdateCommentBFFOutput> output = contentUpdateCommentOperation.process(input);

        return handleResponse(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PatchMapping(CommentMappings.ADMIN_UPDATE_COMMENT)
    public ResponseEntity<?> adminUpdateComment(
            @PathVariable("commentId") String id,
            @RequestBody AdminUpdateCommentBFFInput request
    ) {
        AdminUpdateCommentBFFInput input = request.toBuilder()
                .commentId(id)
                .build();

        Either<Errors, AdminUpdateCommentBFFOutput> output = adminUpdateCommentOperation.process(input);

        return handleResponse(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully deleted comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @DeleteMapping(CommentMappings.DELETE_COMMENT)
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") String id) {
        DeleteCommentBFFInput input = DeleteCommentBFFInput.builder()
                .commentId(id)
                .build();

        Either<Errors, DeleteCommentBFFOutput> output = deleteCommentOperation.process(input);

        return handleResponse(output, HttpStatus.ACCEPTED);
    }
}
