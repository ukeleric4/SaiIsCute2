package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

public class Pitching {
    public Servo pitching;

    public Pitching(HardwareMap hw) {
        pitching = hw.get(Servo.class, "pitching");
    }

    public void moveUp() {
        pitching.setPosition(0);
    }

    public void moveDown() {
        pitching.setPosition(1);
    }

    public double getPosition() { return pitching.getPosition(); }

    public void moveSpecific(double pos) {
        pitching.setPosition(pos);
    }
}
