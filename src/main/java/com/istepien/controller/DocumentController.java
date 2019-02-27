package com.istepien.controller;

import com.istepien.model.Document;
import com.istepien.model.Role;
import com.istepien.model.User;
import com.istepien.service.DocumentService;
import com.istepien.service.UserService;
import org.h2.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    @GetMapping("/formForAddDocument")
    public String showFormForAdd(Model theModel) {

        Document document = new Document();

        theModel.addAttribute("document", document);

        return "add-document";
    }

    @PostMapping("/saveDocument")
    public String saveDocument(@ModelAttribute("document") Document document, Principal principal) {


        User current = userService.getUserByName(principal.getName());
        document.setUser(current);
        document.setDocDateAdded(LocalDate.now());
        documentService.saveDocument(document);

        return "home";
    }

    @GetMapping("/list")
    public String listDocuments(Model model, Principal principal) {
        List<Document> documents = documentService.getAllDocuments();

        User current = userService.getUserByName(principal.getName());

        Set<Role> userRoles = current.getRoles();

        if (userRoles.size() == 1) {
            documents = documents.stream().filter(doc -> doc.getUser().getUsername().equals(principal.getName())).collect(Collectors.toList());
        }
        model.addAttribute("documentList", documents);
        return "document-list";
    }

    @GetMapping("/deleteDocument")
    public String deleteDocument(@RequestParam(name = "docId") Long docId) {

        documentService.deleteDocument(docId);

        return "redirect:list";
    }

    @GetMapping("/formForUpdateDocument")
    public String showFormForUpdateDocument(@RequestParam(name = "docId") Long docId, Model model) {

        Document doc = documentService.getDocument(docId);

        model.addAttribute("document", doc);

        return "update-document";
    }

    @PostMapping("/updateDocument")
    public String updateDocument(@ModelAttribute("document") Document document, Principal principal) {

        User current = userService.getUserByName(principal.getName());
        document.setUser(current);
        document.setDocDateAdded(documentService.getDocument(document.getDocId()).getDocDateAdded());
        document.setDocLastModified(LocalDate.now());
        documentService.saveDocument(document);

        return "home";
    }

    @GetMapping("/showDocumentPreview")
    public ModelAndView showDocumentPreview(@RequestParam(name = "docId") Long docId) {
        ModelAndView view = new ModelAndView("file-preview");
        view.addObject("docId", docId);

        return view;
    }

//    @GetMapping(value = "/showDocumentPreview", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<byte[]> getImage(@RequestParam(name = "docId") Long docId) throws IOException {
//
//        byte[] docContent = documentService.getDocument(docId).getFile();
//        final HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        return new ResponseEntity<byte[]>(docContent, headers, HttpStatus.OK);
//    }

    @GetMapping(value = "/download")
    public void getFile(@RequestParam(name = "docId") Long docId, HttpServletResponse response) {
        try {
            DefaultResourceLoader loader = new DefaultResourceLoader();
            InputStream is = new ByteArrayInputStream(documentService.getDocument(docId).getFile());
            IOUtils.copy(is, response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=Accepted.pdf");
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }

    }

    @RequestMapping(value = "/findDocumentByTitle")
    public String findDocumentByTitle(@RequestParam(value = "docTitle") String docTitle, Model model, Principal principal) {
        User current = userService.getUserByName(principal.getName());
        List<Document> documentList = documentService.getAllDocuments();

        Set<Role> userRoles = current.getRoles();

        if (userRoles.size() == 1) {
            documentList = documentList.stream()
                    .filter(document -> document.getDocTitle().equals(docTitle))
                    .filter(document -> document.getUser().getUsername().equals(principal.getName()))
                    .collect(Collectors.toList());
        } else {
            documentList = documentList.stream()
                    .filter(document -> document.getDocTitle().equals(docTitle))
                    .collect(Collectors.toList());
        }

        model.addAttribute("documentList", documentList);

        return "document-list";

    }

    @PostMapping("/saveAttachment")
    public void saveAttachment(@RequestParam MultipartFile file, Principal principal, HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setContentLength(file.getBytes().length);
        response.setHeader("Content-Disposition", "inline; filename=help.pdf");
        response.setHeader("Cache-Control", "cache, must-revalidate");
        response.setHeader("Pragma", "public");

        response.getOutputStream().write(file.getBytes());
        logger.info("attachment saved");


    }

    @RequestMapping("/sortBy")
    public String sortBy(@RequestParam(name = "value") String value, Model model, Principal principal) {
        List<Document> documentList = documentService.getAllDocuments();
        User current = userService.getUserByName(principal.getName());
        Set<Role> userRoles = current.getRoles();

        if (userRoles.size() == 1) {
            documentList = documentList.stream()
                    .filter(document -> document.getUser().getUsername().equals(principal.getName()))
                    .collect(Collectors.toList());
        }


        switch (value) {
            case "docId":
                break;

            case "docTitle":
                Collections.sort(documentList, new Comparator<Document>() {
                    @Override
                    public int compare(Document o1, Document o2) {
                        return o1.getDocTitle().compareToIgnoreCase(o2.getDocTitle());
                    }
                });
            case "docDateAdded":
                Collections.sort(documentList, new Comparator<Document>() {
                    @Override
                    public int compare(Document o1, Document o2) {
                        return o1.getDocDateAdded().compareTo(o2.getDocDateAdded());
                    }
                });
        }

        model.addAttribute(documentList);

        return "document-list";
    }

}