package com.example.blt.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hongjian.chen
 * @date 2020/2/24 10:33
 */

@Entity
@Table(name = "t_plant_timing_detail")
public class PlantTimingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int tid;
    @Column(length = 128)
    private String detailName;
    private int days;//阶段天数
    private int lightSet;
    private String startTime;
    private String endTime;
}
