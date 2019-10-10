package com.example.blt.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hongjian.chen
 * @date 2019/9/2 17:54
 */
@Entity
@Table(name = "t_cron")
public class CronInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true,length = 32)
    private String cronName;
    @Column(length = 32)
    private String command;
    private Integer itemSet;
    private Integer repetition;
    private String cron;
    private String meshId;
    private int sceneId;
    private Date createDate;
    private Date updateDate;
}
