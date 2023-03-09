package com.photosharingapp.server.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments", schema = "photo_sharing")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1955323L;
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
    @Column(name="content")
    private String content;

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
    @Column(name="isActive")
    private boolean isActive;
    @Column(name="isDeleted")
    private boolean isDeleted;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userId")
    private AppUser user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

}
