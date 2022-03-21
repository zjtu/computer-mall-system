package com.cy.springboot.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class District implements Serializable {
    private  Integer id;
    private String parent;
    private String code;
    private String name;
}
