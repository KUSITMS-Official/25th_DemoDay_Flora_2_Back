package com.lookatthis.flora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class Review extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "review_id")
    private Long id;

    @Lob
    @Column(name = "review_image")
    private Blob reviewImage;

    public InputStream getReviewImageContent() throws SQLException {
        if (getReviewImage() == null) {
            return null;
        }
        return getReviewImage().getBinaryStream();
    }

    @Column(name = "review_score")
    private Float score;

    @Column(name = "review_content")
    @Size(max = 5000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @OneToOne
    @JoinColumn(name = "flower_shop_id")
    private FlowerShop flowerShop;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
