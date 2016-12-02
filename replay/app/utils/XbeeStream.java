package utils;

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;
import java.lang.String;
import java.util.Scanner;

/**
 * Created by brycerich on 11/16/16.*/


public class XbeeStream {
    public Manager manager;
    private static XbeeStream instance;
    public boolean docsReady = false;
//    public static void go() throws Exception {
//    }


    private XbeeStream(){}

    public synchronized static XbeeStream getInstance(){
        if(instance == null){
            instance = new XbeeStream();
            try {
                instance.setManager();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return instance;
    }
    private synchronized void setManager() throws Exception {
        manager = new Manager("localhost", "8983", "demo1");
        manager.getAllDocuments();
        docsReady = true;
    }

//    public Manager getManager() {
//        if (threadNumber == 0) {
//            threadNumber = Thread.currentThread().getId();
//        }
//
//        if (manager == null && Thread.currentThread().getId() == threadNumber) {
//            System.out.println("I dont learn " + count);
//            count++;
//            manager = new Manager("localhost", "8983", "test");
//            System.out.println("My thread num: " + Thread.currentThread().getId());
//            return manager;
//        }
//        while(manager == null) {
//        }
//        return manager;
//    }
}


