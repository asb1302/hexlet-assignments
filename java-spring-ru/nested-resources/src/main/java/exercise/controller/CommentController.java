package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // BEGIN
    @GetMapping(path = "/{postId}/comments")
    public Iterable<Comment> getPostComments(@PathVariable Long postId) {
        return commentRepository.findCommentsByPostId(postId);
    }

    @PatchMapping(path = "/{id}/comments/{commentId}")
    public void updatePostComment(@PathVariable long id, @PathVariable long commentId, @RequestBody Comment comment) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        comment.setPost(post);
        comment.setId(commentId);
        commentRepository.save(comment);
    }

    // END
}
