package com.example.blt.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hongjian.chen on 2019/5/31.
 */

@Entity
@Table(name = "t_host_info")
public class HostInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, length = 32)
    private String ip;
    @Column(unique = true, length = 32)
    private String meshId;
    @ColumnDefault("1")
    private Boolean status;
    @ColumnDefault("0")
    private Boolean isMaster;
    @ColumnDefault("0")
    private Boolean isControl;
    private String other;
    private Date log_date = new Date();

}
