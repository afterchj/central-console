package com.example.blt.entity;


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
    private String meshId;
    @Column(length = 32)
    private String meshName;
    private String other;
    private Date log_date = new Date();

}
