package com.sgsc.BMS.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@Table(name="users")
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;
    @OneToMany
    private List<Booking> bookings;
}
