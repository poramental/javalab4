package org.javalab;

import org.javalab.entity.Plate;
import org.javalab.entity.Stack;
import org.javalab.entity.StartQueue;
import org.javalab.entity.WashMachine;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//Смоделировать процесс мыться тарелок в столовой. На вход в программу поступает информация о тарелках:
// цвет тарелки (строковое значение), время на помывку тарелки (целое число в секундах),
// время поступления тарелки на обработку (целое число в секундах — время относительно предыдущей тарелки).
// Все тарелки поступают в конец очереди. Имеется n мойщиков тарелок (число вводится с клавиатуры).
// Далее каждая секунда обрабатывается отдельно. При обработки очередной секунды выполняются следующие действия:
//
//          У тарелки, находящейся в начале очереди, на 1 уменьшается время поступления тарелки на обработку.
//          Если у тарелки, находящейся в начале очереди, время поступления тарелки на обработку равно 0,
//              то она помещается в стек (стопку) грязных тарелок.
//          Для каждого мойщика проверяется, если у него тарелка, которую он сейчас моет. Если её нет,
//              он забирает себе верхнюю тарелку из стопки грязных тарелок (если там есть тарелки).
//          Для каждого мойщика проверяется, если у тарелки, которую он сейчас моет, время на помыку равно 0,
//              то он перекладывает её в стек (стопку) чистых тарелок, иначе время помывки этой тарелки уменьшается на 1.
//          Процесс продолжается до тех пор, пока не будут вымыты все тарелки из входной очереди.
//          В конце:
//              Вывести порядок цветов тарелок, лежащих в стопке чистых тарелок (начиная с верхней).
//              Для каждого мойщика тарелок вывести количество тарелок, которые он помыл, общее время мытья тарелок, суммарное время «простоя» мойщика.
public class Main {



    private static StartQueue fillQueue(StartQueue queue){
        do{
            try {

                Scanner in = new Scanner(System.in);
                System.out.print("Number of plates ->\t");
                int numberOfPlates = in.nextInt();
                for (int i = 0; i < numberOfPlates; i++) {
                    System.out.print("color of plate ->\n");
                    String color = "green"; // in.nextLine() why not working??
                    System.out.print("processing time ->\t");
                    int processingTime = in.nextInt();
                    System.out.print("washTime ->\t");
                    int washTime = in.nextInt();
                    queue.push(
                            new Plate()
                                    .setWashTime(washTime)
                                    .setProcessingTime(processingTime)
                                    .setColor(color)
                    );

                }
                return queue;
            }catch (InputMismatchException e){
                e.printStackTrace();
                System.out.println("something is incorrect, try again.");
            }

        }while (true);
    }

    private static boolean anyWasherHasProcessingPlate(List<WashMachine> washers){
        for(WashMachine washMachine : washers)
            if(washMachine.getProcessPlate() != null)
                return true;
        return false;
    }

    private static List<WashMachine> makeWashers(){
        do{
            try{
                Scanner in = new Scanner(System.in);
                System.out.print("Number of washers ->\t");
                int numberOfWashers = in.nextInt();
                List<WashMachine> listOfWashers = new ArrayList();
                for(int i = 0; i < numberOfWashers; i++){
                    listOfWashers.add(new WashMachine());
                }
                return listOfWashers;
            }catch (InputMismatchException e) {
                System.out.println("Number of washers is incorrect, try again.");
            }
        }while(true);
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        StartQueue queue = new StartQueue();
        fillQueue(queue);
        List<WashMachine> washers = makeWashers();
        System.out.println(queue.getSize());
        Stack stackOfDirtyPlates = new Stack(queue.getSize());
        Stack stackOfCleanPlates = new Stack(queue.getSize());


        do{
            if(queue.getFirst() != null && queue.getFirst().getProcessingTime() == 0) {
                stackOfDirtyPlates.Push(queue.pop().get());
            }else
                if(queue.getFirst() != null)
                    queue.getFirst().setProcessingTime(queue.getFirst().getProcessingTime() - 1);
            for(WashMachine washer : washers){
                if(washer.getProcessPlate() == null && !stackOfDirtyPlates.isEmpty())
                    washer.setProcessPlate(stackOfDirtyPlates.Pop().get());
                if(washer.getProcessPlate() == null & stackOfDirtyPlates.isEmpty())
                    washer.setDowntime(washer.getDowntime() + 1);
                if(washer.getProcessPlate() != null) washer.wash();
                if(washer.getProcessPlate() != null && washer.getProcessPlate().getWashTime() == 0) {
                    stackOfCleanPlates.Push(washer.getProcessPlate());
                    washer.setProcessPlate(null);
                }
           }


        }while (!queue.isEmpty() && !stackOfDirtyPlates.isEmpty() && anyWasherHasProcessingPlate(washers));



        System.out.println("\n---------------" + "Plates" + "---------------\n" );
        int j = 1;
        while(!stackOfCleanPlates.isEmpty()){
            System.out.println(stackOfCleanPlates.Pop().get().getColor() + "\tplate\t" + "#" + j++);
        }
        int i = 1;
        System.out.println("\n---------------" + "Washers" + "---------------\n" );
        for(WashMachine washer : washers){
            System.out.println(washer.getDowntime() + "\tdowntime\t" + "#" + i);
            System.out.println(washer.getWorkingTime() + "" + "\twashtime\t" + "#" + i++);

        };


    }
}