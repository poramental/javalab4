package org.javalab.entity;


import lombok.*;
import lombok.experimental.Accessors;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Plate implements Cloneable {
    private Plate next;
    private Plate prev;
    private int processingTime;
    private int washTime;
    private String color;

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

}
