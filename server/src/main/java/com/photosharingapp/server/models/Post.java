package com.photosharingapp.server.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts", schema = "photo_sharing")
public class Post {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "caption", nullable = false)
    private String caption;
    @Column(name = "location")
    private String location;
    @Column(name = "likesCount")
    private Integer likesCount;
    @Builder.Default
    @Column(name="createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt = new Date();
    @Builder.Default
    @Column(name="updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt = new Date();
    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @ManyToOne(targetEntity = AppUser.class)
    @JoinColumn()
    private AppUser user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "post")
    private List<Comment> comments;
}
