package com.reservation.backend.concert.domain;

import com.reservation.backend.common.audit.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "v1_concert_categories")
@Entity
public class ConcertCategory extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_category_id")
    private Long id;

    @Column(name = "genre", nullable = false, unique = true)
    private String genre; // BALLAD, IDOL, HIPHOP

    @Column(name = "name", nullable = false)
    private String name; // 발라드, 아이돌, 힙합

    @Column(name ="active", nullable = false)
    private boolean active;

    private ConcertCategory( String genre, String name) {
        this.genre = genre;
        this.name = name;
        this.active = true;
    }

    public static ConcertCategory create( String genre, String name) {
        return new ConcertCategory(genre, name);
    }


}
