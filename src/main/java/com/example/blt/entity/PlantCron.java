package com.example.blt.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hongjian.chen
 * @date 2020/2/24 10:33
 */

@Entity
@Table(name = "t_plant_cron")
public class PlantCron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, length = 32)
    private String cronName;
    private String cron;
    private String meshId;
    private String command;
    private int itemSet;//周期是否启用
    private int sceneId;
    private Date createTime;
    private Date updateTime;
}
