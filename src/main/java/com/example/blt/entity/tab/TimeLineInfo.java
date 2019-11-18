package com.example.blt.entity.tab;


import javax.persistence.*;
import java.util.Date;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-11-15 17:17
 **/
@Entity
@Table(name = "f_time_line",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"tid","mesh_id"},name = "tidAndMeshId")})//多个字段索引
public class TimeLineInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String tname;
    @Column(nullable = false,length = 10)
    private Integer tid;
    @Column(nullable = false,length = 32,name = "mesh_id")
    private String meshId;
    @Column(length = 1)
    private String repetition;
    @Column(name = "dayObj")
    private String dayObj;
    @Column(length = 50)
    private String week;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(length = 1)
    private String other;
    @Column(length = 2)
    private Integer ischoose;
    private String itemDesc;
    @Column(length = 2)
    private Integer itemSet;
    @Column(length = 2)
    private Integer itemTag;
}
