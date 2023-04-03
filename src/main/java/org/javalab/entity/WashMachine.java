package org.javalab.entity;


import lombok.*;


@NoArgsConstructor
@Getter
@Setter
public class WashMachine {

    private int workingTime;
    private int downtime;
    private Plate processPlate;

    public void wash(){
        this.processPlate.setWashTime(this.processPlate.getWashTime() - 1);
        this.workingTime++;
    }

}
