package com.example.blt.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hongjian.chen on 2019/5/31.
 */

@Entity
@Table(name = "t_master_group")
public class MasterGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer childId;
    private Integer hid;
}
