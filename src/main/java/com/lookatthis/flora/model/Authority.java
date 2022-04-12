package com.lookatthis.flora.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "authority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority extends Timestamped {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}
