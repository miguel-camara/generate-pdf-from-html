package com.demo.pdf.facade.impl;

import com.demo.pdf.dto.UserDTO;
import com.demo.pdf.facade.PdfCreationFacade;
import com.demo.pdf.service.PdfCreationService;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Base64;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component("pdfCreationFacade")
public class DefaultPdfCreationFacade implements PdfCreationFacade {
  private final PdfCreationService pdfCreationService;

  public DefaultPdfCreationFacade(PdfCreationService pdfCreationService) {
    this.pdfCreationService = pdfCreationService;
  }

  @Override
  public byte[] createPdfFile() throws Exception {
    final Context context = new Context();
    UserDTO userDTO = new UserDTO();

    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
    String formattedDate = today.format(formatter);

    Month month = today.getMonth();
    int year = today.getYear();

    // Obtienes el nombre del mes
    String date = month.getDisplayName(TextStyle.FULL, new Locale("es", "MX"));

    date += " ".concat(String.valueOf(year));

    userDTO.setUid(1L);
    userDTO.setFirstName("Miguel");
    userDTO.setLastName("Camara");
    userDTO.setIssueDate(formattedDate);
    userDTO.setPeriod(date);

    // Conviete la imagen a base64
    byte[] fileContent = FileUtils.readFileToByteArray(new File("src/main/resources/static/images/logo.png"));
    String encodedString = Base64.getEncoder().encodeToString(fileContent);
    encodedString = "data:image/png;base64,".concat(encodedString);

    userDTO.setLogo(encodedString);

    context.setVariable("user", userDTO);
    return pdfCreationService.createPdfFile("user-template", context);
  }
}
