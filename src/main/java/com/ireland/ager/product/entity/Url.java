package com.ireland.ager.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "urlId", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Url implements Serializable {
    @Id
    @GeneratedValue
    private Long urlId;

    @ManyToOne
    @JsonIgnore
    private Product product;

    private String url;

}