package com.photosharingapp.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_roles", schema = "photo_sharing")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 16762343L;
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

    @ManyToOne()
    @JoinColumn(name = "userId")
    @JsonIgnore
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    @JsonIgnore
    private Role role;
}
