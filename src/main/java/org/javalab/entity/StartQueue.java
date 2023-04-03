package org.javalab.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;
import java.util.Optional;


@Getter
@NoArgsConstructor
public class StartQueue {
    private Plate first;
    private int size = 0;
    public StartQueue(Plate first){
        this.first = first;
    }

    public void push(Plate plate){
        if(this.first == null){
            this.first = plate;
            this.size = size + 1;

        }
        this.first.setPrev(plate);
        plate.setNext(this.first);
        this.size = size + 1;


    }
    public Optional<Plate> pop() throws CloneNotSupportedException{
        if(this.first == null){

            return Optional.empty();
        }
        Plate resultPlate = (Plate) this.first.clone();
        this.first.setPrev(null);
        this.first = resultPlate.getPrev();
        resultPlate.setPrev(null);
        this.size = size  - 1;
        return Optional.of(resultPlate);

    }
    public int getSize(){
        return this.size - 1;
    }

    public boolean isEmpty(){
        return this.first == null;
    }

}
