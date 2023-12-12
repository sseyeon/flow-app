package com.flow.app.service;

import com.flow.app.model.Extension;
import com.flow.app.repository.ExtensionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExtensionServiceImpl implements ExtensionService{
    @Autowired
    private ExtensionRepository extensionRepository;


    @Override
    public List<Extension> getAllExtensions() {
        return extensionRepository.findAll();
    }

    @Override
    public Extension getExtension(String extension) {
        Optional<Extension> existingExtension = extensionRepository.findByExtension(extension);
        return existingExtension.orElse(null);
    }

    @Override
    public List<Extension> getExtensionsByFixedStatus(Boolean isFixed) {
        if (isFixed) {
            return extensionRepository.findByIsFixedTrue();
        } else {
            return extensionRepository.findByIsFixedFalse();
        }
    }

    @Override
    public Extension addExtension(String extension) {
        Optional<Extension> existingExtension = extensionRepository.findByExtension(extension);

        if (existingExtension.isPresent()) {
            // 이미 있는 확장자인 경우
            if (existingExtension.get().getIsFixed()) {
                // 고정 확장자인 경우
                throw new RuntimeException("고정 확장자로 등록되어 있는 확장자입니다.");
            } else {
                // 이미 삭제된 확장자인 경우
                existingExtension.get().setStatus("ACTIVE");
                extensionRepository.save(existingExtension.get());
            }
        } else {
            // 신규 등록인 경우
            Extension newExtension = new Extension();
            newExtension.setExtension(extension);
            newExtension.setIsFixed(false);
            newExtension.setStatus("ACTIVE");
            newExtension.setAddedDate(LocalDateTime.now());

            extensionRepository.save(newExtension);
        }
        // 추가된 로직: IsFixed가 false이고, 활성 상태인 확장자가 200개인지 체크
        long countActiveExtensions = extensionRepository.countByIsFixedAndStatus(false, "ACTIVE");
        if (countActiveExtensions > 200) {
            // 예외 처리 또는 다른 로직 수행
            throw new RuntimeException("활성 상태인 IsFixed가 false인 확장자가 200개 이상입니다.");
        }
        return null;
    }

    @Override
    public void updateExtensionStatus(String extension, String newStatus) {
        Optional<Extension> existingExtension = extensionRepository.findByExtension(extension);

        if (existingExtension.isPresent()) {
            existingExtension.get().setStatus(newStatus);
            extensionRepository.save(existingExtension.get());
        } else {
            throw new EntityNotFoundException("해당 확장자를 찾을 수 없습니다.");
        }
    }
}
