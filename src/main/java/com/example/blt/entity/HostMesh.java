package com.example.blt.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hongjian.chen on 2019/5/31.
 */

@Entity
@Table(name = "t_host_mesh")
public class HostMesh implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private Integer hid;
    private Integer mid;
}
