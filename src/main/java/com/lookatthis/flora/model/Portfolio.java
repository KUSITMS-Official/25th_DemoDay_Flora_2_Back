package com.lookatthis.flora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class Portfolio extends Timestamped {
    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "portfolio_id")
    private Long id;

    @Column(name = "portfolio_name", nullable = false)
    private String portfolioName;

    @Lob
    @Column(name = "portfolio_image")
    private Blob portfolioImage;

    public InputStream getPortfolioImageContent() throws SQLException {
        if (getPortfolioImage() == null) {
            return null;
        }
        return getPortfolioImage().getBinaryStream();
    }

    @Column(name = "portfolio_description")
    @Size(max = 5000)
    private String portfolioDescription;

    @Column(name = "portfolio_price")
    private int price;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Color color;

    @Column(name = "clip_count")
    private int clipCount = 0;

    @ManyToOne
    @JoinColumn(name = "flower_shop_id", referencedColumnName = "flower_shop_id")
    private FlowerShop flowerShop;

}
