package com.example.blt.entity.tab;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-11-15 17:17
 **/
@Entity
@Table(name = "f_time_point")
public class TimePointInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 32)
    private Integer tsid;
    @Column(length = 32)
    private Integer sceneId;
    @Column(length = 5)
    private String time;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(length = 1)
    private String other;
    @Column(length = 5)
    private Integer hour;
    @Column(length = 5)
    private Integer minute;
    @Column(length = 10,name = "pos_x")
    private Integer posX;
    @Column(length = 2)
    private Integer detailSenceId;
    private Integer lightStatus;
}
