package com.example.blt.entity.tab;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-11-15 16:46
 **/
@Entity
@Table(name = "t_egroup")
public class EGroupInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer groupId;
    private String gname;
    private Integer mid;
    private Integer pid;
    @ColumnDefault("0")
    private Integer status;
    @ColumnDefault("1")
    private Integer sequence;
}
