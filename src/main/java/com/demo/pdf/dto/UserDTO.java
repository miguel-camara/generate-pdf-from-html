package com.demo.pdf.dto;

import lombok.Data;

@Data
public class UserDTO {
  private String firstName;
  private String lastName;
  private String logo;
  private Long uid;
  private String issueDate;
  private String period;
}
