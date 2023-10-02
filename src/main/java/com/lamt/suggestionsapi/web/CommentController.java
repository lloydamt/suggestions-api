package com.lamt.suggestionsapi.web;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.service.interfaces.CommentService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComments() {
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable UUID commentId, Authentication authentication) {
        var userId = UUID.fromString(authentication.getName());
        commentService.deleteComment(commentId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
