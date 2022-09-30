package com.hf.videoplayer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class MultipleIntelligence implements Serializable {
    private Integer id;

    private String uid;

    private Integer linguistIntelli;

    private Integer logicMathIntelli;

    private Integer spatialIntelli;

    private Integer bodilyKinesIntelli;

    private Integer musicIntelli;

    private Integer interpersonalIntelli; //人际

    private Integer selfIntelli; //自我

    private Integer naturalIntelli;
}