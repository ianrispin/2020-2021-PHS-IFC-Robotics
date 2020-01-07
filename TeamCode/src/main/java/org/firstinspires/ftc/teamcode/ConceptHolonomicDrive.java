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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import java.util.Timer;
import java.util.TimerTask;
import android.media.MediaPlayer;
import java.lang.Math;



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
    DcMotor verticalLift;
    double liftHeight = 0;
    String harvestMode = "POS";
    double maxLiftHeight = 100000;
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
//    Servo harvester;
    DcMotor harvester;
    double  harvesterPosition = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    boolean autoHarvester = false;

// hsvValues is an array that will hold the hue, saturation, and value information.
float hsvValues[] = {0F, 0F, 0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;

    // sometimes it helps to multiply the raw RGB values with a scale factor
    // to amplify/attentuate the measured values.
    final double SCALE_FACTOR = 255;

    int relativeLayoutId;
    View relativeLayout;


    MediaPlayer mediaPlayer;
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

//        MediaPlayer mediaPlayer = MediaPlayer.create(FtcRobotControllerActivity.getContext(), R.raw.sound_file);
//        mediaPlayer.start();
        motorFrontRight = hardwareMap.dcMotor.get("motor front right");
        motorFrontLeft = hardwareMap.dcMotor.get("motor front left");
        motorBackLeft = hardwareMap.dcMotor.get("motor back left");
        motorBackRight = hardwareMap.dcMotor.get("motor back right");
//        harvester = hardwareMap.get(Servo.class, "harvester");
        harvester = hardwareMap.dcMotor.get("harvester");
        verticalLift = hardwareMap.dcMotor.get("verticalLift");
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if(harvestMode == "POS") {
            harvester.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            harvester.setTargetPosition(0);
            harvester.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            harvester.setPower(1);
        }else{
            harvester.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        mediaPlayer = MediaPlayer.create(hardwareMap.appContext, R.raw.hesapirate);
        mediaPlayer.start();

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

//        float gamepad1LeftY = -gamepad1.left_stick_y;
//        float gamepad1LeftX = gamepad1.left_stick_x;
        float gamepad1RightX = gamepad1.right_stick_x;
        float Xmove;
        float Ymove;
        if (gamepad1.left_stick_y != 0) {
            Ymove = (float) ((-gamepad1.left_stick_y) / Math.abs(gamepad1.left_stick_y) * Math.sqrt(Math.abs(gamepad1.left_stick_y)));
        } else {
            Ymove = 0;
        }
        if (gamepad1.left_stick_x != 0) {
            Xmove = (float) ((gamepad1.left_stick_x) / Math.abs(gamepad1.left_stick_x) * Math.sqrt(Math.abs(gamepad1.left_stick_x)));
        } else {
            Xmove = 0;
        }
        if (gamepad1.a) {
//            driveWithInput(0, -1, 0);
//            verticalLift.setTargetPosition(0);
        } else if (gamepad1.y) {
            driveWithInput(0, 1, 0);
        } else if (gamepad1.x) {
            driveWithInput(-1, 0, 0);
        } else if (gamepad1.b) {
//            driveWithInput(1, 0, 0);
            harvester.setTargetPosition(harvester.getCurrentPosition());
        } else {
            driveWithInput(Xmove, Ymove, gamepad1RightX);
        }
        //maxServoAngle(34 degrees)
        if(gamepad1.right_bumper){
            dropHarvester();
        }
        else if(gamepad1.left_bumper){
            raiseHarvester();
        }else{
            if(harvestMode != "POS"){
                harvester.setPower(0);
            }
        }

//        if(gamepad1.right_trigger - gamepad1.left_trigger < 0 && liftHeight >= 0 || gamepad1.right_trigger - gamepad1.left_trigger > 0 && liftHeight <= maxLiftHeight) {
            verticalLift.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
            liftHeight += gamepad1.left_trigger - gamepad1.right_trigger;

//        }else{
//            verticalLift.setPower(0);
//        }
        //controlServo.setPosition(34/180*liftHeight/maxLiftHeight)

        //code for auto-drop
        //if(lift is down() && autoDrop){
        //if(distance < xcm){
        //
        //}
//        if(gamepad1.right_bumper){
//            foundColor = getColor(sensorColor)[0];
//        }
//        if (gamepad1LeftY !== 0) {
//            getColor();
//        }
        telemetry.addData("Found Hue", "found " + String.format("%.2f", foundColor));



    }

    @Override
    public void stop() {
        mediaPlayer.stop();

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    public float[] getColor(ColorSensor sensor) {
//        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        // get a reference to the color sensor.


        Color.RGBToHSV((int) (sensor.red() * SCALE_FACTOR),
                (int) (sensor.green() * SCALE_FACTOR),
                (int) (sensor.blue() * SCALE_FACTOR),
                hsvValues);

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });
        return hsvValues;
    }
    public void driveWithInput(float directionX,float directionY,float rotation){//direction refers to values that would be seen on a gamepad.
//        float FrontLeft = (float)(-(directionY + directionX + (rotation / .75))  );//left power is reversed
//        float FrontRight = (float)(directionY - directionX - (rotation / .75));
//        float BackRight = (float)(directionY + directionX - (rotation /.75));
//        float BackLeft = (float)(-(directionY - directionX + (rotation / .75)));
        float theta = (float)Math.atan2(directionY,directionX);
        float amplitude = (float)Math.hypot(directionX,directionY);

        float FrontLeft = (float)(-Math.sin(theta+((1.0/4.0) * Math.PI)) * 1.0/.71 * amplitude - (rotation / .75));
        float BackRight = (float)((Math.sin(theta+((1.0/4.0) * Math.PI)) * 1.0/.71 * amplitude - (rotation / .75)));
        float FrontRight = (float)(Math.sin(theta-((1.0/4.0) * Math.PI)) * 1.0/.71 * amplitude - (rotation / .75));
        float BackLeft = (float)(-Math.sin(theta-((1.0/4.0) * Math.PI)) * 1.0/.71 * amplitude - (rotation / .75));

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);

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
//        telemetry.addData("f left pwr", "front left  pwr: " + String.format("%.2f", FrontLeft));
//        telemetry.addData("f right pwr", "front right pwr: " + String.format("%.2f", FrontRight));
//        telemetry.addData("b right pwr", "back right pwr: " + String.format("%.2f", BackRight));
//        telemetry.addData("b left pwr", "back left pwr: " + String.format("%.2f", BackLeft));
        telemetry.addData("Theta", "Theta: " + String.format("%.2f", theta));
//        telemetry.addData("VerticalLift Height", "Height: " + String.format("%.2f",verticalLift.getCurrentPosition()));


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
    public void driveForDistance(float powerX, float powerY, double distance){//right now, only for lateral directions
        double velocity = 0.8;
        double speed = powerX + powerY;
        double finalVelocity = velocity * speed;
        double FinalTime = distance/finalVelocity;
        driveForTime(powerX, powerY, 0, FinalTime);
        driveWithInput(0,0,0);
    }
    public void manualHarvester(float direction){
        if((direction < 0 && harvesterPosition > MIN_POS) || (direction > 0 && harvesterPosition < MAX_POS) && !autoHarvester){
            harvesterPosition += 0.1 * direction;

        }
//        harvester.setPosition(harvesterPosition);

    }
    public void dropHarvester(){

//            harvester.setPosition(0.6);
        if(harvestMode == "POS") {
            harvester.setTargetPosition(240);
        }else{
            harvester.setPower(0.8);
        }


    }
    public void raiseHarvester(){
        if(harvestMode == "POS") {
            harvester.setTargetPosition(0);
        }else{
            harvester.setPower(-0.8);
        }
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

