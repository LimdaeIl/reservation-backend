package com.reservation.backend.concert.domain;

import com.reservation.backend.common.audit.BaseAuditEntity;
import com.reservation.backend.concert.exception.ConcertErrorCode;
import com.reservation.backend.concert.exception.ConcertException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "v1_concerts")
@Entity
public class Concert extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Embedded
    private VenueInfo venueInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ConcertCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConcertStatus status;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<ConcertImage> images = new ArrayList<>();

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    private Concert(
            String title,
            String description,
            VenueInfo venueInfo,
            ConcertCategory category,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {
        validatePeriod(startDateTime, endDateTime);

        this.title = title;
        this.description = description;
        this.venueInfo = venueInfo;
        this.category = category;
        this.status = ConcertStatus.OPEN;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static Concert create(
            String title,
            String description,
            VenueInfo venueInfo,
            ConcertCategory category,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {
        return new Concert(title, description, venueInfo, category, startDateTime, endDateTime);
    }

    private void validatePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (!endDateTime.isAfter(startDateTime)) {
            throw new ConcertException(ConcertErrorCode.INVALID_CONCERT_PERIOD);
        }
    }

    public void addImage(String imageUrl, int sortOrder, boolean representative) {
        ConcertImage image = ConcertImage.create(imageUrl, sortOrder, representative, this);
        this.images.add(image);
    }
}
