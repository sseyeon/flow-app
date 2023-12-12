package com.flow.app.repository;

import com.flow.app.model.Extension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExtensionRepository extends JpaRepository<Extension, Long> {
    Optional<Extension> findByExtension(String extension);

    List<Extension> findByIsFixedTrue();

    List<Extension> findByIsFixedFalse();

    long countByIsFixedAndStatus(boolean isFixed, String status);
}
