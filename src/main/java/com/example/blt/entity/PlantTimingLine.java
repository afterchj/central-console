package com.example.blt.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hongjian.chen
 * @date 2020/2/26 11:48
 */
@Entity
@Table(name = "t_plant_timing")
public class PlantTimingLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tname;
    @Column(length = 1024)
    private String itemDetail;//阶段设置详情
    private int days;//周期总天数
    private int itemSet;//周期是否启用
    private int itemCount;//阶段设置个数
    private String startDate;
    private Date createTime;
    private Date updateTime;
}
