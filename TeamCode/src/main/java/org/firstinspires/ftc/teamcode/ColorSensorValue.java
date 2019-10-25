package org.firstinspires.ftc.teamcode;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ConceptHolonomicDrive;

import java.util.Locale;

public class ColorSensorValue extends ConceptHolonomicDrive {
    ColorSensor sensorColor;

//    DistanceSensor sensorDistance;
//    int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
//    final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues[] = {0F, 0F, 0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;

    // sometimes it helps to multiply the raw RGB values with a scale factor
    // to amplify/attentuate the measured values.
    final double SCALE_FACTOR = 255;
    public void init(){
//        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");




        // get a reference to the distance sensor that shares the same name.
//        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");


    }


    public float[] getColor() {
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        // get a reference to the color sensor.


        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);

//        relativeLayout.post(new Runnable() {
//            public void run() {
//                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
//            }
//        });
        return hsvValues;
    }

    public static void main(String[] args) {

    }
}