/**
 *
 * Created by Maddie, FTC Team 4962, The Rockettes
 * version 1.0 Aug 11, 2016
 *
 */

package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import java.util.Timer;
import java.util.TimerTask;



/*
	Holonomic concepts from:
	http://www.vexforum.com/index.php/12370-holonomic-drives-2-0-a-video-tutorial-by-cody/0
   Robot wheel mapping:
          X FRONT X
        X           X
      X  FL       FR  X
              X
             XXX
              X
      X  BL       BR  X
        X           X
          X       X
*/
@TeleOp(name = "Concept: HolonomicDrivetrain", group = "Concept")
//@Disabled
public class ConceptHolonomicDrive extends OpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    ColorSensor sensorColor;
// hsvValues is an array that will hold the hue, saturation, and value information.
float hsvValues[] = {0F, 0F, 0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;

    // sometimes it helps to multiply the raw RGB values with a scale factor
    // to amplify/attentuate the measured values.
    final double SCALE_FACTOR = 255;

    int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
    final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
    /**
     * Constructor
     */
    public ConceptHolonomicDrive() {

    }

    @Override
    public void init() {


        /*
         * Use the hardwareMap to get the dc motors and servos by name. Note
         * that the names of the devices must match the names used when you
         * configured your robot and created the configuration file.
         */


        motorFrontRight = hardwareMap.dcMotor.get("motor front right");
        motorFrontLeft = hardwareMap.dcMotor.get("motor front left");
        motorBackLeft = hardwareMap.dcMotor.get("motor back left");
        motorBackRight = hardwareMap.dcMotor.get("motor back right");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
//        ourColorSensor = new ColorSensorValue();
        //These work without reversing (Tetrix motors).
        //AndyMark motors may be opposite, in which case uncomment these lines:
        //motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        //motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        //motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        //motorBackRight.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop() {


        // left stick controls direction
        // right stick X controls rotation
        float foundColor = 0;

        float gamepad1LeftY = -gamepad1.left_stick_y;
        float gamepad1LeftX = gamepad1.left_stick_x;
        float gamepad1RightX = gamepad1.right_stick_x;
        if(gamepad1.a){
            driveWithInput(0,-1,0);
        }else if(gamepad1.y){
            driveWithInput(0,1,0);
        }else if(gamepad1.x){
            driveWithInput(-1,0,0);
        }else if(gamepad1.b){
            driveWithInput(1,0,0);
        }else {
            driveWithInput(gamepad1LeftX, gamepad1LeftY, gamepad1RightX);
        }
        // holonomic formulas
        if(gamepad1.right_bumper){
            foundColor = getColor()[0];
        }
//        if (gamepad1LeftY !== 0) {
//            getColor();
//        }
        telemetry.addData("Found Hue", "found " + String.format("%.2f", foundColor));



    }

    @Override
    public void stop() {

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    public float[] getColor() {
//        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        // get a reference to the color sensor.


        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });
        return hsvValues;
    }
    public void driveWithInput(float directionX,float directionY,float rotation){//direction refers to values that would be seen on a gamepad.
        float FrontLeft = -directionY - directionX - rotation;
        float FrontRight = directionY - directionX - rotation;
        float BackRight = directionY + directionX - rotation;
        float BackLeft = -directionY + directionX - rotation;

        // clip the right/left values so that the values never exceed +/- 1
//        FrontRight = Range.clip(FrontRight, -1, 1);
//        FrontLeft = Range.clip(FrontLeft, -1, 1);
//        BackLeft = Range.clip(BackLeft, -1, 1);
//        BackRight = Range.clip(BackRight, -1, 1);

        // write the values to the motors
        motorFrontRight.setPower(FrontRight);
        motorFrontLeft.setPower(FrontLeft);
        motorBackLeft.setPower(BackLeft);
        motorBackRight.setPower(BackRight);


        /*
         * Telemetry for debugging
         */
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Joy XL YL XR", String.format("%.2f", directionX) + " " +
                String.format("%.2f", directionY) + " " + String.format("%.2f", directionX));
        telemetry.addData("f left pwr", "front left  pwr: " + String.format("%.2f", FrontLeft));
        telemetry.addData("f right pwr", "front right pwr: " + String.format("%.2f", FrontRight));
        telemetry.addData("b right pwr", "back right pwr: " + String.format("%.2f", BackRight));
        telemetry.addData("b left pwr", "back left pwr: " + String.format("%.2f", BackLeft));

    }
    public void driveForTime(float directionX,float directionY,float rotation,double moveDuration){//going to optomise this to make it better for turning

        Timer whenDone = new Timer();

        MovementTimer t = new MovementTimer();
//        boolean finished = t.finished;
        whenDone.schedule(t,(long)moveDuration * 1000);
        while(!t.finished) {
            driveWithInput(directionX,directionY,rotation);

        }


//        whenDone.schedule(new TimerTask());
    }
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}

