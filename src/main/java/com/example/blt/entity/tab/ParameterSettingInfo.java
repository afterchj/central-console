package com.example.blt.entity.tab;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-11-15 16:28
 **/
@Entity
@Table(name = "t_parameter_setting")
public class ParameterSettingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @Enumerated(EnumType.STRING)
    @Column()
    private String unit;
    @ColumnDefault("10")
    private Integer sceneCount;
    private String project;
    @ColumnDefault("1")
    private Integer sceneId;
    @ColumnDefault("50")
    private String x;
    @ColumnDefault("50")
    private String y;
}
