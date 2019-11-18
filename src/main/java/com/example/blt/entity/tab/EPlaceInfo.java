package com.example.blt.entity.tab;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-11-15 16:43
 **/
@Entity
@Table(name = "t_eplace")
public class EPlaceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer placeId;
    private String placeName;
    private Integer mid;
    @ColumnDefault("0")
    private Integer status;
    @ColumnDefault("1")
    private Integer sequence;
}
