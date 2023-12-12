package com.flow.app.service;

import com.flow.app.model.Extension;

import java.util.List;

public interface ExtensionService {
    List<Extension> getAllExtensions();
    Extension getExtension(String extension);
    List<Extension> getExtensionsByFixedStatus(Boolean is_fiexed);

    Extension addExtension(String extension);

    // 확장자 상태 변경 메소드 추가
    void updateExtensionStatus(String extension, String newStatus);

}
