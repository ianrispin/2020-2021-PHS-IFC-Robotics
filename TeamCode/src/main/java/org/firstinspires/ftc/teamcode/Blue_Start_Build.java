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

import java.util.Timer;

import java.lang.Math.*;


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

@TeleOp(name="Blue: start Build", group="Linear Opmode")
//@Disabled
public class Blue_Start_Build extends LinearOpMode {

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

    int relativeLayoutId;
    View relativeLayout;


    MediaPlayer mediaPlayer;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        motorFrontRight = hardwareMap.dcMotor.get("motor front right");
        motorFrontLeft = hardwareMap.dcMotor.get("motor front left");
        motorBackLeft = hardwareMap.dcMotor.get("motor back left");
        motorBackRight = hardwareMap.dcMotor.get("motor back right");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        mediaPlayer = MediaPlayer.create(hardwareMap.appContext, R.raw.hesapirate);
//        mediaPlayer.start();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        /*leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");*/

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
//        leftDrive.setDirection(DcMotor.Direction.FORWARD);
//        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
//        while(runtime.time() < 30){
//
//        }
        mediaPlayer.start();
//        not this
//        driveForTime(0,1,0,.1);
//
//        driveForTime(-1, 0, 0, 0.75);
//        driveForTime(0, 1, 0, 4);
//        driveForTime(-1, 0, 0, 2.5);
//
//        driveForTime(0, 1, 0, 1);
//        driveForTime(1, 0, 0, 1.5);
//        driveForTime(0, 1, 0, 1);
//        driveForTime(-1, 0, 0, 1.5);
//        driveForTime(0, 1, 0, 1);
//        driveForTime(1, 0, 0, 1.5);
//        driveForTime(0, 1, 0, 1);
//        driveForTime(-1, 0, 0, 1.5);
//        driveForTime(0, 1, 0, 1);
//        driveForTime(1, 0, 0, 1.5);
//        driveForTime(0, 1, 0, 1);
//        driveForTime(-1, 0, 0, 1.5);
//        driveForTime(0, 1, 0, 1);
//        driveForTime(1, 0, 0, 1.5);
//        driveForTime(0, 1, 0, 1);
//
//        driveForTime(1, 0, 0, 0.5);
//        driveForTime(0, -1, 0, 4);
        driveForTime(0, 1, 0, 0.1);
        while(!(getColor()[0] > 210 && getColor()[0] < 255)){
            driveWithInput((float)0.5,0,0);
        }
        driveWithInput(0, 0, 0);
//        driveForTime(0, 1, 0, .5);
//        driveForTime(0,1,0,.1);
////
////        driveForTime(-1, 0, 0, 0.75);
////        driveForTime(0, 1, 0, 4);
////        driveForTime(-1, 0, 0, 2.5);
////
////        driveForTime(0, 1, 0, 1);
////        driveForTime(1, 0, 0, 1.5);
////        driveForTime(0, 1, 0, 1);
////        driveForTime(-1, 0, 0, 1.5);
////        driveForTime(0, 1, 0, 1);
////        driveForTime(1, 0, 0, 1.5);
////        driveForTime(0, 1, 0, 1);
////        driveForTime(-1, 0, 0, 1.5);
////        driveForTime(0, 1, 0, 1);
////        driveForTime(1, 0, 0, 1.5);
////        driveForTime(0, 1, 0, 1);
////        driveForTime(-1, 0, 0, 1.5);
////        driveForTime(0, 1, 0, 1);
////        driveForTime(1, 0, 0, 1.5);
////        driveForTime(0, 1, 0, 1);
////
////        driveForTime(1, 0, 0, 0.5);
////        driveForTime(0, -1, 0, 4);
////        while(!(getColor()[0] > 210 && getColor()[0] < 255)){
////            driveWithInput(1,0,0);
////        }
        //driveWithInput(0, 0, 0);
        while(opModeIsActive()){}
        mediaPlayer.stop();
//        not this
//        IF IN BUILD ZONE: move to middle of mat;   drive close to end (guess right now, will have sensors later);   move forward a bit to push mat;   move back (to other end of foundation);
//        move forward a bit; move back (to other end of foundation); move forward a bit; repeat a few times




        

        // run until the end of the match (driver presses STOP)
//        while (opModeIsActive()) {
//
//            // Setup a variable for each drive wheel to save power level for telemetry
//            double leftPower;
//            double rightPower;
//
//            // Choose to drive using either Tank Mode, or POV Mode
//            // Comment out the method that's not used.  The default below is POV.
//
//            // POV Mode uses left stick to go forward, and right stick to turn.
//            // - This uses basic math to combine motions and is easier to drive straight.
//            double drive = -gamepad1.left_stick_y;
//            double turn  =  gamepad1.right_stick_x;
//            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
//            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;
//
//            // Tank Mode uses one stick to control each wheel.
//            // - This requires no math, but it is hard to drive forward slowly and keep straight.
//            // leftPower  = -gamepad1.left_stick_y ;
//            // rightPower = -gamepad1.right_stick_y ;
//
//            // Send calculated power to wheels
//            leftDrive.setPower(leftPower);
//            rightDrive.setPower(rightPower);
//
//            // Show the elapsed game time and wheel power.
//            telemetry.addData("Status", "Run Time: " + runtime.toString());
//            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
//            telemetry.update();
//        }
    }
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
        while(!t.finished && opModeIsActive()) {
            driveWithInput(directionX,directionY,rotation);

        }
        driveWithInput(0, 0, 0);


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
    public void driveADistance(float distance, int x, int y) {
        float setpos = (float) (2 * Math.PI);//once found radius, multiply 2*pi*radius
        float FrontLeft = -y - x;
        float FrontRight = y - x;
        float BackRight = y + x;
        float BackLeft = -y + x;
//        make if statements to set the position of the motors
        motorFrontLeft.setTargetPosition((int) (distance/setpos));
        motorFrontRight.setTargetPosition((int) (distance/setpos));
        motorBackLeft.setTargetPosition((int) (distance/setpos));
        motorBackRight.setTargetPosition((int) (distance/setpos));
        if (motorFrontLeft.isBusy()) {
            motorFrontLeft.setPower(FrontLeft);
        }
        else {
            motorFrontLeft.setPower(0);
        }

        if (motorFrontRight.isBusy()) {
            motorFrontRight.setPower(FrontRight);
        }
        else {
            motorFrontRight.setPower(0);
        }

        if (motorBackLeft.isBusy()) {
            motorBackLeft.setPower(BackLeft);
        }
        else {
            motorBackLeft.setPower(0);
        }

        if (motorBackRight.isBusy()) {
            motorBackRight.setPower(BackRight);
        }
        else {
            motorBackRight.setPower(0);
        }
    }
}
//    public void driveWithInput(float directionX,float directionY,float rotation){//direction refers to values that would be seen on a gamepad.
//        float FrontLeft = -directionY - directionX - rotation;
//        float FrontRight = directionY - directionX - rotation;
//        float BackRight = directionY + directionX - rotation;
//        float BackLeft = -directionY + directionX - rotation;


// clip the right/left values so that the values never exceed +/- 1
//        FrontRight = Range.clip(FrontRight, -1, 1);
//        FrontLeft = Range.clip(FrontLeft, -1, 1);
//        BackLeft = Range.clip(BackLeft, -1, 1);
//        BackRight = Range.clip(BackRight, -1, 1);

//motorFrontRight.setPower(FrontRight);
//        motorFrontLeft.setPower(FrontLeft);
//        motorBackLeft.setPower(BackLeft);
//        motorBackRight.setPower(BackRight);