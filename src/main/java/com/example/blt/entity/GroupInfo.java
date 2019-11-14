package com.example.blt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hongjian.chen on 2019/5/31.
 */

@Entity
@Table(name = "t_group")
public class GroupInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 32)
    private String gname;
    private Integer groupId;
    private String other;
    private Date createDate = new Date();
    private Date updateDate = new Date();

}
