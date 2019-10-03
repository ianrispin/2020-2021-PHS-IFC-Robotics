package org.firstinspires.ftc.teamcode;

import java.util.Timer;
import java.util.TimerTask;

public class MovementTimer extends TimerTask {

    public boolean finished = false;

    public void run(){
        finished = true;
    }

}
