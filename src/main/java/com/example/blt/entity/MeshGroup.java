package com.example.blt.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hongjian.chen on 2019/5/31.
 */

@Entity
@Table(name = "t_mesh_group")
public class MeshGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer gid;
    @Column(unique = true)
    private Integer mid;
    private Integer masterId;
}
