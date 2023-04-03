package org.javalab.entity;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Stack {
    private Plate[] stackArr;
    private int maxSize;
    private int top = 0;

    public Stack(int m){
        maxSize = m;
        top = -1;
        stackArr = new Plate[m];
    }

    public void Push(Plate plate){
        stackArr[++top] = plate;
    }

    public Optional<Plate> Pop(){
        return Optional.ofNullable(stackArr[top--]);
    }
    public boolean isEmpty(){

        return top == -1;
    }

}
