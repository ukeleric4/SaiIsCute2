package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.parts.Orientation;
import org.firstinspires.ftc.teamcode.parts.PIDFSlide;
import org.firstinspires.ftc.teamcode.parts.Vision;

@Config
@TeleOp
public class PIDFSlideTest extends LinearOpMode {
    private PIDController controller;

    public static double p = 0.025, i = 0, d = 0.000001;
    public static double f = 0.04;

    public static int target = 500;
    double ticks_in_degree = 145.1 / 180.0;

    private DcMotorEx motor1;
    private DcMotorEx motor2;

    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.get(DcMotorEx.class, "slide1");
        motor2 = hardwareMap.get(DcMotorEx.class, "slide2");

        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        controller = new PIDController(p, i, d);

        // Max - 1410

        while (opModeIsActive()) {
            controller.setPID(p, i, d);
            int motorPos = -motor2.getCurrentPosition();
            double pid = controller.calculate(motorPos, target);
            double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

            double power = pid + ff;

            motor1.setPower(-power);
            motor2.setPower(-power);

            telemetry.addData("position: ", motor2.getCurrentPosition());
            telemetry.addData("target: ", target);
            telemetry.update();
        }
    }
}

