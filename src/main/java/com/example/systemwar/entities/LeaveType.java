package com.example.systemwar.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_types")
public class LeaveType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // e.g., "Annual", "Medical"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
