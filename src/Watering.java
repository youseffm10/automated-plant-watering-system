import org.firmata4j.I2CDevice;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Watering {
    static class pins {
        public static int D7 = 7;
        public static int A2  = 16;
    }
    private static String log ="";
    private static String log1 ="";
    private static String log4 ="";
    private static final int moistureThreshold = 300;
    public static void process() throws IOException, InterruptedException {
        String myPort = "COM3";
        IODevice myDevice = new FirmataDevice(myPort);
        myDevice.start();
        myDevice.ensureInitializationIsDone();

        I2CDevice i2cObject = myDevice.getI2CDevice((byte) 0x3C);
        SSD1306 myOLED = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);

        myOLED.init();
        try {
            myDevice.start();
            myOLED.getCanvas().setTextsize(2);
            myOLED.getCanvas().drawString(4, 10, "Arduino");
            myOLED.display();
            Thread.sleep(1000);
            myOLED.getCanvas().drawString(4, 10, "Connection");
            myOLED.display();
            Thread.sleep(1000);
            myOLED.getCanvas().drawString(4, 10, "Successful");
            myOLED.display();
            Thread.sleep(1000);
            myOLED.getCanvas().clear();
            myOLED.display();
            Thread.sleep(1000);
            myOLED.getCanvas().setCursor(90, 0);

            myDevice.ensureInitializationIsDone();
        } catch (Exception ex) {
            System.out.println("Couldn't connect to board.");
        }

        myOLED.getCanvas().setTextsize(2);
        myOLED.getCanvas().drawString(4, 10, "Enter");
        myOLED.display();
        Thread.sleep(1000);
        myOLED.getCanvas().drawString(4, 10, "Number of");
        myOLED.display();
        Thread.sleep(1000);
        myOLED.getCanvas().clear();
        myOLED.display();
        Thread.sleep(1000);
        myOLED.getCanvas().drawString(4, 10, "days");
        myOLED.display();
        Thread.sleep(1000);
        myOLED.getCanvas().drawString(4, 10, "to run");
        myOLED.display();
        Thread.sleep(1000);
        myOLED.display();
        myOLED.getCanvas().drawString(4, 10, "program");
        myOLED.display();
        Thread.sleep(1000);
        myOLED.getCanvas().clear();
        myOLED.display();
        Thread.sleep(1000);
         System.out.println("Enter number of days to run program:");
        Scanner scanner = new Scanner(System.in);
         int days = scanner.nextInt();

//number of days
        for (int j = 0; j < days; j++) {

            // a for loop to run for 24 times a day
            for (int i = 0; i < 24; i++) {
                SimpleDateFormat dateFormat;
                Date date;
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Thread.sleep(1000);
                myOLED.getCanvas().drawString(4, 10, "Starting");
                myOLED.display();
                Thread.sleep(1000);
                myOLED.getCanvas().drawString(4, 10, "Water");
                myOLED.display();
                Thread.sleep(1000);
                myOLED.getCanvas().drawString(4, 10, "Process.");
                myOLED.display();
                Thread.sleep(2000);
                myOLED.getCanvas().clear();
                myOLED.display();
                Thread.sleep(1000);

                var waterPump = myDevice.getPin(pins.D7);
                waterPump.setMode(Pin.Mode.OUTPUT);
                var moistureSensor = myDevice.getPin(pins.A2);
                moistureSensor.setMode(Pin.Mode.ANALOG);

                date = new Date();
                log1 = log1 + dateFormat.format(date);
                myOLED.getCanvas().drawString(1, 1, log1);
                myOLED.display();
                Thread.sleep(2000);
                myOLED.getCanvas().clear();
                myOLED.display();
                Thread.sleep(1000);
                myOLED.getCanvas().drawString(1, 1, "Moisture  level is   " + moistureSensor.getValue() + " \n");
                myOLED.display();
                Thread.sleep(2000);
                myOLED.getCanvas().clear();
                myOLED.display();
                Thread.sleep(1000);
                int counter = 0;
                while (moistureSensor.getValue() > moistureThreshold) {
                    if (counter == 0) {

                        myOLED.getCanvas().drawString(1, 1, "Moisture  level is    low \n");
                        myOLED.display();
                        Thread.sleep(2000);
                        myOLED.getCanvas().clear();
                        myOLED.display();
                        Thread.sleep(1000);
                        myOLED.getCanvas().drawString(1, 1, "watering   for    5 seconds \n");
                        myOLED.display();
                        Thread.sleep(2000);
                        myOLED.getCanvas().clear();
                        myOLED.display();
                        Thread.sleep(1000);
                        myOLED.getCanvas().setCursor(90, 0);
                    } else {
                        Thread.sleep(1000);
                        myOLED.getCanvas().drawString(1, 1, " Moisture level is  still low \n");
                        myOLED.display();
                        Thread.sleep(2000);
                        myOLED.getCanvas().clear();
                        myOLED.display();
                        Thread.sleep(1000);
                        myOLED.getCanvas().drawString(1, 1, "watering  for 5     seconds. \n");
                        myOLED.display();
                        Thread.sleep(2000);
                        myOLED.getCanvas().clear();
                        myOLED.display();
                        Thread.sleep(1000);
                        myOLED.getCanvas().setCursor(90, 0);
                        waterPump.setValue(1);
                        Thread.sleep(5000);
                        waterPump.setValue(0);
                        Thread.sleep(5000);
                    }
                    waterPump.setValue(1);
                    Thread.sleep(5000);
                    waterPump.setValue(0);
                    Thread.sleep(5000);
                    counter++;
                }
                date = new Date();
                log4 = log4 + dateFormat.format(date);
                myOLED.getCanvas().drawString(1, 1, log4);
                myOLED.display();
                Thread.sleep(3000);
                myOLED.getCanvas().clear();
                myOLED.display();
                Thread.sleep(1000);
                myOLED.getCanvas().drawString(1, 1, "Plant is  wet after");
                myOLED.display();
                Thread.sleep(2000);
                myOLED.getCanvas().clear();
                myOLED.display();
                myOLED.getCanvas().drawString(1, 1, +(counter * 2) + " seconds  of        watering\n");
                myOLED.display();
                Thread.sleep(2000);
                myOLED.getCanvas().clear();
                myOLED.getCanvas().clear();
                myOLED.display();
                Thread.sleep(1000);
                myOLED.getCanvas().setCursor(90, 0);
                Thread.sleep(3600000); // this makes it check every 1 hour
            }
        }
        myDevice.stop();
        myOLED.getCanvas().drawString(1, 1, "Arduino Board Stopped.");
        myOLED.display();
        Thread.sleep(2000);
        myOLED.getCanvas().drawString(1, 1, "End of Watering Process");
        myOLED.display();
        Thread.sleep(2000);
        myOLED.getCanvas().clear();
        myOLED.display();
        Thread.sleep(1000);
        myOLED.getCanvas().setCursor(90, 0);
        System.out.println("Arduino Board Stopped. End of Watering Process.");
    }
    public static String processLog() {
        return log;
    }
}
