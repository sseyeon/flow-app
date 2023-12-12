package com.flow.app.model.request;

import lombok.Data;

@Data
public class ExtensionRequest {
  private String extension;
  private boolean isFixed;
  private String status;
}
