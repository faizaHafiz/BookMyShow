package com.sgsc.BMS.models;

import java.util.List;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Region extends BaseModel{
    private String name;
    //private List<Theatre> theatres; either this or include region
    //in theatre class
}
