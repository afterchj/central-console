package com.example.blt.entity;


import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hongjian.chen on 2019/5/31.
 */

@Entity
@Table(name = "t_mesh")
public class MeshInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, length = 32)
    private String meshId;
    @Column(length = 32)
    private String meshName;
    @ColumnDefault("03")
    private String flag;
    private String other;
    private Date log_date = new Date();
    @ColumnDefault("0")
    private Integer status;
    private Integer sid;//空间id
    @ColumnDefault("1")
    private Integer sequence;//序列，顺序
}
