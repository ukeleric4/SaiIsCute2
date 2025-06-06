package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class SpecimenArm {
    public LiatPanningServo panning1;
    public LiatPanningServo2 panning2;

    public SpecimenArm(HardwareMap hw) {
        panning1 = new LiatPanningServo(hw);
        panning2 = new LiatPanningServo2(hw);
    }

    public void pickUp() {
        panning1.moveDown();
        panning2.moveDown();
    }

    public void score() {
        panning1.moveUp();
        panning2.moveUp();
    }

    public void scorePush() {
        panning1.moveSpecific(1);
        panning2.moveSpecific(1);
    }

    public void moveSpecific(double pos) {
        panning1.moveSpecific(pos);
        panning2.moveSpecific(pos);
    }
}
