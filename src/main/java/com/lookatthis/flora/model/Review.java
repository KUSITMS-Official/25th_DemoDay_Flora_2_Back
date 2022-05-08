package com.lookatthis.flora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "review_image", nullable = false)
    private Blob reviewImage;

    public InputStream getReviewImageContent() throws SQLException {
        if (getReviewImage() == null) {
            return null;
        }
        return getReviewImage().getBinaryStream();
    }

    @Column(name = "review_score", nullable = false)
    private BigDecimal score;

    @Column(name = "review_content", nullable = false)
    private BigDecimal content;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private FlowerShop flowerShop;
}
