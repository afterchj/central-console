package com.example.blt.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hongjian.chen
 * @date 2020/2/24 10:33
 */

@Entity
@Table(name = "t_plant_cron_detail")
public class PlantTimingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int plantId;
    @Column(unique = true, length = 32)
    private String detailName;
    private int days;//阶段天数
    private int LightSet;
    private Date startTime;
    private Date endTime;
}
