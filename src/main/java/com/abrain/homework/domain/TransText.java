package com.abrain.homework.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "transText")
public class TransText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;
    private String id;
    private String phone;
    private String name;
    private String addr;
    private String price;
    private String companyId;
    private String companyNum;
}
