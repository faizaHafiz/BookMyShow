package com.sgsc.BMS.models;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment extends BaseModel{

    @Enumerated(EnumType.ORDINAL)
    private PaymentProvider paymentProvider;

    private String referenceNumber;
    private int amount;

    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;
}
