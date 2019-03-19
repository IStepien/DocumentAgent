package com.istepien.controller;

import com.istepien.model.Comment;
import com.istepien.service.CommentService;
import com.istepien.service.DocumentService;
import com.istepien.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;
    @Autowired
    DocumentService documentService;
    @Autowired
    UserService userService;

    @GetMapping("/addComment")
    public String addComment(@RequestParam(name = "docId") Long docId, Model model) {


        Comment comment = new Comment();
        comment.setDocument(documentService.getDocument(docId));
        model.addAttribute("comment", comment);

        return "add-comment";
    }

    @PostMapping("/saveComment")
    public String saveComment(@ModelAttribute(name = "comment") Comment comment, Principal principal) {
        comment.setUser(userService.getUserByName(principal.getName()));
        commentService.saveComment(comment);
        return "home";
    }

    @GetMapping("/showComments")
    public String showComments(@RequestParam(name = "docId") Long docId, Model model) {
        List<Comment> allComments = commentService.getAllComments().stream()
                .filter(comment -> comment.getDocument().getDocId().equals(docId)).collect(Collectors.toList());

        model.addAttribute("allComments", allComments);



        return "comments-list";

    }

    @GetMapping("/modify")
    public String modify(@RequestParam(name = "commentId") Long commentId, Model model) {
        Comment comment = commentService.getComment(commentId);

        model.addAttribute("comment", comment);

        return "update-comment";
    }

    @PostMapping("/updateComment")
    public String updateComment(@ModelAttribute("comment") Comment comment) {
        comment.setUser(userService.getUserByName(comment.getUser().getUsername()));
        commentService.saveComment(comment);

        return "home";
    }
    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam(name = "commentId") Long commentId){
        commentService.deleteComment(commentId);
        return "home";
    }

}
