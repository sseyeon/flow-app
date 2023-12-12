package com.flow.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="flow_table")
@Getter
@Setter
@NoArgsConstructor
public class Extension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "extension", length = 20)
    private String extension;

    @Column(name = "added_date")
    private LocalDateTime addedDate;

    @Column(name = "is_fixed")
    private Boolean isFixed;

    @Column(name = "status", length = 10)
    private String status;

    public Extension(String extension, LocalDateTime addedDate, boolean isFixed, String status) {
        this.extension = extension;
        this.addedDate = addedDate;
        this.isFixed = isFixed;
        this.status = status;
    }
}
