package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    public Servo claw;
    public AnalogInput encoder;

    public Claw(HardwareMap hw) {
        claw = hw.get(Servo.class, "claw");
        encoder = hw.get(AnalogInput.class, "clawEncoder");
    }

    public void openClaw() {
        claw.setPosition(0);
    }

    public double getEncoderPosition() {
        return (encoder.getVoltage() / 3.3 * 360);
    }

    public void closeClaw() {
        claw.setPosition(1.0);
    }

    public double getPosition() { return claw.getPosition(); }

    public void moveSpecific(double pos) {
        claw.setPosition(pos);
    }
}
