package utils;

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;
import java.lang.String;
import java.util.Scanner;

/**
 * Created by brycerich on 11/16/16.*/


public class XbeeStream {
    private static Manager manager;

    public static double toFahrenheit(int value) {
        double resistance, voltage, celcius, fahrenheit;
        voltage = (double) value * (3.3 / 1023.0); //get voltage
        resistance = voltage * 10000 / (3.3 - voltage); //get resistance
        celcius = (2.4146393415052E-33 * Math.pow(resistance, 7) - (3.81484311754641E-28 * Math.pow(resistance, 7)) + (2.57181874806467e-23 * Math.pow(resistance, 6)) - (9.67904300021044e-19 * Math.pow(resistance, 5))
                + (2.23669918609410e-14 * Math.pow(resistance, 4)) - (3.29870866993895e-10 * Math.pow(resistance, 3)) + (3.15587413705945e-06 * Math.pow(resistance, 2)) - (0.0204653425908208 * resistance) + 94.9263168201449);
        //C = (2.41463934150502e-33*pow(Rs,)-(3.81484311754641e-28*pow(Rs,7))+(2.57181874806467e-23*pow(Rs,6))-(9.67904300021044e-19*pow(Rs,5))\
        //+(2.23669918609410e-14*pow(Rs,4))-(3.29870866993895e-10*pow(Rs,3))+(3.15587413705945e-06*pow(Rs,2))-(0.0204653425908208*Rs)+94.9263168201449;
        //System.out.print("Here's the voltage " + voltage + " the R " + resistance + " the C " + celcius);
        //System.out.print(" In F " + (celcius * 9 / 5 + 32) + "\n");
        fahrenheit = (celcius * (double)9/5) + 32;
        return (fahrenheit);
    }

    public static void go() throws Exception {
        XBee xbee = new XBee();
        manager = new Manager("localhost", "8983", "test");
        xbee.open("/dev/tty.usbserial-DN01IW76", 9600);
        System.out.println("Made it here");
        while (true) {

            XBeeResponse response = xbee.getResponse();

            //System.out.println("We received a sample from " + response.toString());
            int[] rawPackets = response.getRawPacketBytes();
            char c = (char) rawPackets[rawPackets.length - 2];
            String number = "";
            for (int i = 7; i <= rawPackets.length - 2; ++i) {
                char character = (char) rawPackets[i];
                if (character != '}') {
                    number = number.concat(Character.toString(character));
                    //System.out.print((char) rawPackets[i]);
                }
            }
            System.out.println(number);
            //number = number.substring(1);
            int s1 = 0, s2 = 0, s3 = 0, s4 = 0, t = 0;
            Scanner scan = new Scanner(number);
            if (scan.hasNextInt()) {
                s1 = scan.nextInt();
                s2 = scan.nextInt();
                s3 = scan.nextInt();
                s4 = scan.nextInt();
                t = scan.nextInt();
                //System.out.println(" S1: " + toFahrenheit(s1) + " S2: " + toFahrenheit(s2) + " S3: " + toFahrenheit(s3) + " S4: " + toFahrenheit(s4) + " T: " + t);
                //System.out.println("S1 = " + (int) scan.nextInt() + " S2 = " + (int) scan.nextInt() + " S3 = " + (int) scan.nextInt() + " S4 = " + (int) scan.nextInt() + " Time = " + (int) scan.nextInt() + "\n");
                //System.out.println(number);
            }
            if(allInRange(s1,s2,s3,s4)) {
                manager.addDoc(toFahrenheit(s1), 1);
                manager.addDoc(toFahrenheit(s2), 2);
                manager.addDoc(toFahrenheit(s3), 3);
                manager.addDoc(toFahrenheit(s4), 4);
            }

            /*//math to convert from sensor reading to fahrenheit
            double resistance, voltage, celcius, fahrenheit;
            double value = Double.valueOf(number);
            voltage = value * (3.3 / 1023.0); //get voltage
            resistance = voltage * 10000 / (3.3 - voltage); //get resistance
            celcius = (2.4146393415052E-33 * Math.pow(resistance, 7) - (3.81484311754641E-28 * Math.pow(resistance, 7)) + (2.57181874806467e-23 * Math.pow(resistance, 6)) - (9.67904300021044e-19 * Math.pow(resistance, 5))
                    + (2.23669918609410e-14 * Math.pow(resistance, 4)) - (3.29870866993895e-10 * Math.pow(resistance, 3)) + (3.15587413705945e-06 * Math.pow(resistance, 2)) - (0.0204653425908208 * resistance) + 94.9263168201449);
            //C = (2.41463934150502e-33*pow(Rs,)-(3.81484311754641e-28*pow(Rs,7))+(2.57181874806467e-23*pow(Rs,6))-(9.67904300021044e-19*pow(Rs,5))\
            //+(2.23669918609410e-14*pow(Rs,4))-(3.29870866993895e-10*pow(Rs,3))+(3.15587413705945e-06*pow(Rs,2))-(0.0204653425908208*Rs)+94.9263168201449;
            System.out.print("Here's the voltage " + voltage + " the R " + resistance + " the C " + celcius);
            System.out.print(" In F " + (celcius * 9 / 5 + 32) + "\n");*/


/*            //System.out.print(c);
            //int[] processedPacketBytes = response.getProcessedPacketBytes();
             * for(int i = 0; i < processedPacketBytes.length; ++i) {
                System.out.println("We received a sample from " + processedPacketBytes[i]);
            }*/

        }
    }

    public static boolean allInRange(int a, int b, int c, int d){
        if(a<269 || b < 269 || c<269 || d<269){
            return false;
        }else if(a>422 || b > 422 || c>422 || d>422){
            return false;
        }
        return true;
    }

    public static Manager getManager() {
        if (manager == null) {
            return null;
        } else {
            return manager;
        }
    }
}


