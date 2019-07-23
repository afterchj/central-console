package com.example.blt.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hongjian.chen on 2019/6/14.
 */

@Entity
@Table(name = "t_light_info2")
public class LightInfoC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String mname;
    private String ip;
    @Column(length = 64)
    private String lmac;
    private String lname;
    private String place;
    private Integer groupId;
    private String x;
    private String y;
    private Integer status;
    private String other;
    private Date log_date=new Date();

}
