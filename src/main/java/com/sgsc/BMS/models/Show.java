package com.sgsc.BMS.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="Shows")
public class Show extends BaseModel{
    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Screen screen;
    private Date startTime;
    private Date endTime;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;


}
