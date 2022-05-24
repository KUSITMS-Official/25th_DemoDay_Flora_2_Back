package com.lookatthis.flora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Long id;

    @JsonIgnore
    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 100, nullable = false, unique = true)
    @JsonIgnore
    private String password;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "phone_number", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column
    private LoginType loginType;

    @Lob
    @Column(name = "profile_image")
    private Blob profileImage;

    public InputStream getProfileImageContent() throws SQLException {
        if (getProfileImage() == null) {
            return null;
        }
        return getProfileImage().getBinaryStream();
    }

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(name = "user_latitude")
    private Double userLatitude;

    @Column(name = "user_longitude")
    private Double userLongitude;

    @JsonIgnore
    @Column(name = "user_point")
    private Point userPoint;

}