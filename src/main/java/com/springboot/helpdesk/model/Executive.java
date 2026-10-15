package com.springboot.helpdesk.model;

import com.springboot.helpdesk.enums.JobTitle;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Executive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String contact;
    @Enumerated(EnumType.STRING)
    private JobTitle jobTitle;

    @OneToOne
    private User user;

    @ManyToOne
    private Manager manager;
}
