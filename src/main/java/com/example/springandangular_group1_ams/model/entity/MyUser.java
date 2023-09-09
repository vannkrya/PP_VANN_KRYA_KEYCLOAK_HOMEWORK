package com.example.springandangular_group1_ams.model.entity;

import com.example.springandangular_group1_ams.model.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class MyUser {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 10
    )
    private Role role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Article> articles;
    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks;

    public MyUser(UUID userId) {
        this.id=userId;
    }

    public UserDto toDto() {
        return new UserDto(
                this.id,
                this.name,
                this.role
        );

    };
    public MyUser(String name, Role role) {
        this.name = name;
    }
}

