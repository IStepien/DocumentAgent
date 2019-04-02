package com.istepien.controller;

import com.istepien.model.*;
import com.istepien.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/requestForUpgrade")
    public String requestForUpgrade(@RequestParam(name = "username") String username, Model model) {
        Message message = new Message();
        message.setUser(userService.getUserByName(username));
        messageService.saveMessage(message);

        return "home";
    }

    @GetMapping("/getAllUpgradeRequests")
    public String getAllUpgradeRequests(Model model) {
        List<Message> messageList = messageService.getAllMessages();
        Role user = roleService.getRoleByName("ROLE_USER");
        Set<String> toBeUpgraded = new HashSet<>();
        for (Message m : messageList) {
            if(m.getUser().getRole().getRolename().equals(user.getRolename()))
            toBeUpgraded.add(m.getUser().getUsername());
        }
        model.addAttribute("toBeUpgraded", toBeUpgraded);


        return "usersToBeUpgraded-list";
    }


    @GetMapping("/upgradeRole")
    public String upgradeRole(@RequestParam(name = "username") String username) {

        User current = userService.getUserByName(username);
        Role moderator = roleService.getRoleByName("ROLE_MODERATOR");
        current.setRole(moderator);
        userService.updateUser(current);
        return "home";
    }

    @GetMapping("/documentToBeDeleted")
    public String AddDocumentToBeDeleted(@RequestParam(name = "docId") Long docId, Model model, Principal principal) {
        Message message = new Message();

        message.setDocument(documentService.getDocument(docId));
        message.setUser(userService.getUserByName(principal.getName()));

        messageService.saveMessage(message);


        return "home";
    }

    @GetMapping("/getAllDocumentsToBeDeleted")
    public String getAllDocumentsToBeDeleted(Model model) {
        List<Message> messageList = messageService.getAllMessages();

        List<Document> toBeDeleted = new ArrayList<>();
        for ( Message message : messageList){
          toBeDeleted.add(message.getDocument());
        }

        model.addAttribute("toBeDeleted", toBeDeleted);

        return "documents-toBeDeleted";
    }
    @GetMapping("/commentsToBeDeleted")
    public String commentToBeDeleted(@RequestParam(name = "commentId")Long commentId, Principal principal){
        Message message = new Message();
        Comment current = commentService.getComment(commentId);
        message.setComment(current);
        message.setDocument(current.getDocument());
        message.setUser(userService.getUserByName(principal.getName()));

        messageService.saveMessage(message);


        return "allDocuments-list";
    }
    @GetMapping("/getAllCommentsToBeDeleted")
    public String getAllCommentsToBeDeleted(Model model) {
        List<Message> messageList = messageService.getAllMessages();

        List<Comment> toBeDeleted = new ArrayList<>();
        for ( Message message : messageList){
            toBeDeleted.add(message.getComment());
        }

        model.addAttribute("toBeDeleted", toBeDeleted);

        return "comments-toBeDeleted";
    }
    @GetMapping("/sendMessage")
    public String sendMessage(Model model){
        Message message = new Message();

        model.addAttribute(message);

        return "sendMessage";
    }
    @PostMapping("/saveMessage")
    public String saveMessage(@ModelAttribute (name = "message") Message message, Principal principal){
        message.setUser(userService.getUserByName(principal.getName()));
        messageService.saveMessage(message);
        logger.info("save-Message " + message.getUser().getUsername() + message.getMessageText());

        return "home";
    }
@GetMapping("/getAllTextMessages")
    public String getAllTextMessages(Model model){
        List<Message> messages = messageService.getAllMessages();

        List<Message> textMessages = new ArrayList<>();
        for (Message message : messages){
            if(message.getMessageText()!=null){
                textMessages.add(message);
            }
        }
        model.addAttribute("textMessages", textMessages);
        logger.info("Text messages list size"+textMessages.size());

        return "textMessages-list";
    }
    @GetMapping("/deleteMessage")
    public String deleteMessage(@RequestParam(name = "messageId") Long messageId){
        messageService.deleteMessage(messageId);

        return "home";
    }
}
