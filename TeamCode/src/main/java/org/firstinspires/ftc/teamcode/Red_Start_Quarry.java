/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.Timer;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Red: Start Quarry", group="Linear Opmode")
//@Disabled
public class Red_Start_Quarry extends LinearOpMode {
    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    ColorSensor sensorColor;
    ColorSensor frontSensorLeft;
    ColorSensor frontSensorRight;
    DcMotor harvester;
    DcMotor verticalLift;


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

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
//    private DcMotor leftDrive = null;
//    private DcMotor rightDrive = null;

    @Override
    public void runOpMode() {
        motorFrontRight = hardwareMap.dcMotor.get("motor front right");
        motorFrontLeft = hardwareMap.dcMotor.get("motor front left");
        motorBackLeft = hardwareMap.dcMotor.get("motor back left");
        motorBackRight = hardwareMap.dcMotor.get("motor back right");
        verticalLift = hardwareMap.dcMotor.get("verticalLift");
        verticalLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        verticalLift.setPower(0);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //newCode
        harvester = hardwareMap.dcMotor.get("harvester");
        harvester.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        harvester.setTargetPosition(0);
        harvester.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        harvester.setPower(1);
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        frontSensorRight = hardwareMap.get(ColorSensor.class, "frontSensorRight");
        frontSensorLeft = hardwareMap.get(ColorSensor.class, "frontSensorLeft");
        relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        mediaPlayer = MediaPlayer.create(hardwareMap.appContext, R.raw.hesapirate);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
//        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
//        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
//
//        // Most robots need the motor on one side to be reversed to drive forward
//        // Reverse the motor that runs backwards when connected directly to the battery
//        leftDrive.setDirection(DcMotor.Direction.FORWARD);
//        rightDrive.setDirection(DcMotor.Direction.REVERS+E);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        mediaPlayer.start();
        //Distance measured in meters use time for less than 10 cm;    20 cm x 10 cm blocks;    6 blocks
        //Drops blocks right in the build zone and moves back over to quarry


//        driveForDistance(0,1,.7);
//        while(!(getColor(frontSensorLeft)[2] < 25)){
//        driveWithInput(-(float)0.5,0,0);
//        }
        //double distance = ((runtime.time() -50)*0.35)/1000
//        driveForTime(0,0,0,100);
//        driveForDistance(-1,0,0.15);
        //driveForTime(0,0,0,100);
        //dropHarvester();
        //sleep(400);
        //distance += .15;
        //diveForDistance(1,0,distance+0.8);
        //raiseHarvester()
        //sleep(400);
        //driveForDistance(0,-1,0.15);
        //holdUnderBridge();
        //sleep(400);
        //driveForDistance(-1,0,distance+0.8+0.6);
        //raiseHarvester();
        //sleep(400);
        //driveForDistance(0,1,0.15)
        //dropHarvester();
        //sleep(400);
        //driveForDistance(1,0,distance+0.8+0.6);
        //raiseHarvester()
        //sleep(400);
//        driveForTime(0,0,-1,400);
        //holdUnderBridge();
        //sleep(400);
        //while (!((getColor(sensorColor)[0] < 20)) && (!(getColor(sensorColor)[0] > 350)) && opModeIsActive()) {
        //                driveWithInput(0, (float)0.5, 0);
        //            }


        driveForDistance(0,1,0.56);
        sleep(1500);
//        while(!(getColor(frontSensorLeft)[2] > 22 && getColor(frontSensorLeft)[2] < 40) && opModeIsActive()){
        while(!(getColor(frontSensorLeft)[0] > 80)){
        driveWithInput(-(float)0.5,0,0);
        }
        telemetry.addData("f left pwr", "front left  pwr: " + String.format("%.2f", (getColor(frontSensorLeft)[2])));
        double distance = ((runtime.time() -50)*0.35)/1000 + 0.07;
        driveForDistance(-1,0,0.07);
        driveForDistance(0,1,0.03);
        dropHarvester();
        sleep(1000);
        verticalLift.setPower(-1);
        sleep(500);
        verticalLift.setPower(0);
        sleep(500);
        driveForDistance(0, -1, 0.4);
        driveForDistance(1, 0, 1.4+distance);

        holdUnderBridge();
        sleep(1000);
        driveForDistance(-1, 0, 1.4 + distance + 0.6);
        raiseHarvester();
        sleep(1000);
        verticalLift.setPower(1);
        sleep(500);
        verticalLift.setPower(0);
        sleep(500);
        driveForDistance(0, 1, 0.4);
        dropHarvester();
        sleep(1000);
        verticalLift.setPower(-1);
        sleep(500);
        verticalLift.setPower(0);
        sleep(500);
        driveForDistance(0, -1, 0.4);
        driveForDistance(1, 0, 1.5+distance+0.6);

        holdUnderBridge();
        sleep(1000);
        driveForDistance(-1, 0, 1.5);
        raiseHarvester();
        sleep(1000);
        driveForDistance(0, 1, 0.4);
        dropHarvester();
        sleep(1000);
        driveForDistance(0, -1, 0.4);
        driveForDistance(1, 0, 1.4);

        raiseHarvester();
        sleep(1000);



//        driveForTime(0,1,0,0.6);
//        while(!(getColor(frontSensorLeft)[2] < 25)){
//        driveWithInput(-(float)0.5,0,0);
//        }
//        double distance = (0.35)*runtime.time();
//        driveForDistance(0,1,0.05);
//        driveForDistance(-1,0,0.13);
//        dropHarvester();
//        while(harvester.isBusy()){}
//        driveForDistance(0,-1,0.1);
//        driveForDistance(-1,0,distance + 0.7);
//        raiseHarvester();
//        while(harvester.isBusy()){}
//        driveForDistance(1,0, 0.7 + distance + .62);
//         driveForDistance(0,1,0.1);
//        dropHarvester();
//        while(harvester.isBusy()){}
//        driveForDistance(0,-1,0.1);
//        driveForDistance(-1,0,0.7 + distance + .8);
//        raiseHarvester();
        //move to tape
        //drive on line code
//        driveForTime(0, 1, 0, 100);
//            while (!((getColor(sensorColor)[0] < 20)) && (!(getColor(sensorColor)[0] > 350))) {
////            driveForTime(float directionX,float directionY,float rotation,double moveDuration)
//                driveWithInput(-(float)0.5, 0, 0);
//            }
//        driveWithInput(0,0,0);
            while(opModeIsActive()){}
            mediaPlayer.stop();
        }


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
    public void driveWithInput ( float directionX, float directionY, float rotation)
    {//direction refers to values that would be seen on a gamepad.
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
        getColor(frontSensorLeft);

    }
    public void driveForTime ( float directionX, float directionY, float rotation, long moveDuration){//going to optomise this to make it better for turning

        Timer whenDone = new Timer();

        MovementTimer t = new MovementTimer();
//        boolean finished = t.finished;
        whenDone.schedule(t, (long) moveDuration);
        while (!t.finished && opModeIsActive()) {
            driveWithInput(directionX, directionY, rotation);

        }
        driveWithInput(0,0,0);


//        whenDone.schedule(new TimerTask());
        getColor(frontSensorLeft);
    }

    public void driveForDistance(float powerX, float powerY, double distance){//right now, only for lateral directions
        double velocity = 0.7;
        double speed = powerX + powerY;
        double finalVelocity = Math.abs(velocity * speed);
        long FinalTime = (long)(1000*(distance/finalVelocity));
        driveForTime(powerX, powerY, 0, FinalTime+50);
        driveWithInput(0,0,0);
        getColor(frontSensorLeft);
    }
    public void dropHarvester(){
            harvester.setTargetPosition(180);
    }
    public void holdUnderBridge(){ harvester.setTargetPosition(125);}
    public void raiseHarvester(){
        harvester.setTargetPosition(0);
    }
    double scaleInput ( double dVal){
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
