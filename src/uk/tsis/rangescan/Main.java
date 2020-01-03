package uk.tsis.rangescan;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        //host port timeout

        if (args.length != 3){
            System.out.println("range port timeout (timeout in miliseconds)");
            System.out.println("Example:  20.0.10.0 22 5000");
            return;
        }

        List<Thread> threadList = new ArrayList<>();
        for (int x = 0; x < 500; x++){
            threadList.add(new Thread(new Scanner(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]))));
            threadList.get(x).start();
        }

    }

}
