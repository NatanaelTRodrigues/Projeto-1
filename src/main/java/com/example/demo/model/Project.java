package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks; 

    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}