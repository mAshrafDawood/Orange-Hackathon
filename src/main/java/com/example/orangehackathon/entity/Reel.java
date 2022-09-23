package com.example.orangehackathon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reel")
public class Reel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "video", nullable = false)
    private String video;

    @Column(name = "description")
    private String description;


    @ManyToMany(mappedBy = "like", fetch = FetchType.EAGER)
    private Set<User> like;

    @ManyToOne(fetch = FetchType.EAGER)
    @Singular
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reel reel = (Reel) o;
        return id != null && Objects.equals(id, reel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}