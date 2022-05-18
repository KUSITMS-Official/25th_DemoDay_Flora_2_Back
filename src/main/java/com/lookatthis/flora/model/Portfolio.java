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
public class Portfolio extends Timestamped {

    // ID가 자동으로 생성 및 증가.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "portfolio_id")
    private Long id;

    @Column(name = "portfolio_name", nullable = false)
    private String portfolioName;

    @Column(name = "portfolio_description")
    @Size(max = 5000)
    private String portfolioDescription;

    @Column(name = "portfolio_price")
    private int price;

    @Column(name = "discount")
    private int discount = 0;

    @Column(name = "discount_price")
    private int discountPrice = 0;

    @Column(name = "portfolio_review_count")
    private int reviewCount = 0;

    @Column(name = "portfolio_review_sum")
    private float portfolioReviewSum;

    @Column(name = "portfolio_review_score")
    private float portfolioReviewScore;

    @Column(name = "clip_count")
    private int clipCount = 0;

    @Lob
    @Column(name = "portfolio_image")
    private Blob portfolioImage;

    public InputStream getPortfolioImageContent() throws SQLException {
        if (getPortfolioImage() == null) {
            return null;
        }
        return getPortfolioImage().getBinaryStream();
    }

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "flower_shop_id", referencedColumnName = "flower_shop_id")
    private FlowerShop flowerShop;

    @ManyToOne
    @JoinColumn(name = "flower_id", referencedColumnName = "flower_id")
    private Flower flower;

    public void setPortfolioReview(float score){
        this.portfolioReviewSum += score;
        this.reviewCount += 1;
        this.portfolioReviewScore = (portfolioReviewSum/reviewCount);
    }

    public void deleteReview(float score) {
        this.reviewCount -= 1;
        this.portfolioReviewSum -= score;

        if(reviewCount != 0) {
            this.portfolioReviewScore = (portfolioReviewSum / reviewCount);
        }
        else {
            this.portfolioReviewScore = 0.00f;
        }
    }

}
