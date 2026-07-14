package com.demo.pdf.controller;

import com.demo.pdf.facade.PdfCreationFacade;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller
@RestController
@RequestMapping("/")
public class PdfCreationController {
  private final PdfCreationFacade pdfCreationFacade;

  public PdfCreationController(PdfCreationFacade pdfCreationFacade) {
    this.pdfCreationFacade = pdfCreationFacade;
  }

  @GetMapping("/pdf")
  public ResponseEntity<byte[]> downloadUserPDF() throws Exception {

    final byte[] pdfContent = pdfCreationFacade.createPdfFile();
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=user-info.pdf")
        .contentType(MediaType.APPLICATION_PDF)
        .body(pdfContent);
  }
}
