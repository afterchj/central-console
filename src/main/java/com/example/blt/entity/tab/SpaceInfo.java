package com.example.blt.entity.tab;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-11-15 16:40
 **/
@Entity
@Table(name = "t_space")
public class SpaceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ColumnDefault("0")
    private String status;
    @ColumnDefault("1")
    private Integer sequence;
}
