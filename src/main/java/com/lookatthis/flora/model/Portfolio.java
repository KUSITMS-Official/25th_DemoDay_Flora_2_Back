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
import java.util.ArrayList;
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

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Lob
    @Column(name = "portfolio_image", nullable = false)
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

    @OneToMany
    private List<Flower> flowers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private FlowerShop flowerShop;



}
