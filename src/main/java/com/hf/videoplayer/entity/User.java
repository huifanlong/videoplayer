package com.hf.videoplayer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private Integer id;

    private String userName;

    private String userPass;

    private String name;

    private Integer authority;

    private String courseName;

}
