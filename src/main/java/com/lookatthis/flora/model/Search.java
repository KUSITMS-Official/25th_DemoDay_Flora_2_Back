package com.lookatthis.flora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class Search {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "search_item_id")
    private Long id;

    @Column(name = "search_word")
    private String searchWord;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
