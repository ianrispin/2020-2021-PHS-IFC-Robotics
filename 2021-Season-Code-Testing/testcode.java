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

public class test extends Opmode {
    
    DcMotor testM;
    
    public void init() {
        test_motor = hardwareMap.DcMotor.get(DcMotor.class, "testM");
        test_motor.setDirection(DcMotor.Direction.REVERSE);
    }
    
    public void loop() {
        float J_left = -gamepad1.left_stick_y;
      
        waitForStart();
      
        left_drive.setPower(J_left);
    }

}
