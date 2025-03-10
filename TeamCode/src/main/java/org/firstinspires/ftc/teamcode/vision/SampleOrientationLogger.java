package org.firstinspires.ftc.teamcode.vision;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.concurrent.TimeUnit;

@Config
@TeleOp(name="Sample Orientation Logger Calibrator", group="Calibration")
public class SampleOrientationLogger extends LinearOpMode {

    private final Size CAMERA_RESOLUTION = new Size(640, 480);

    public static double minusNinetyPosition = 0.2;
    public static double plusNinetyPosition = 0.83;

    private SampleOrientationProcessorYellow processor;

    private Servo wrist;
    private WebcamName cameraName;

    @Override
    public void runOpMode() throws InterruptedException {
        wrist = hardwareMap.get(ServoImplEx.class, "orientation");
        cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        VisionPortal visionPortal = buildVisionPortal(cameraName);

        waitForStart();
        while ((opModeInInit() || opModeIsActive()) && visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING);
        sleep(100);
        updateExposure(visionPortal, getCorrectedExposure(processor.getAverageBrightness()));

        while (opModeIsActive()) {
            double sampleAngleProportion = (processor.getSampleAngle() - -Math.PI/2) / Math.PI;
            double servoAngle = minusNinetyPosition + sampleAngleProportion*(plusNinetyPosition - minusNinetyPosition);
            wrist.setPosition(servoAngle);
        }
    }

    public VisionPortal buildVisionPortal(WebcamName cameraName) {
        processor = new SampleOrientationProcessorYellow(telemetry);

        VisionPortal visionPortal = new VisionPortal.Builder()
                .setCamera(cameraName)
                .setCameraResolution(CAMERA_RESOLUTION)
                .setAutoStopLiveView(false)
                .addProcessors(processor)  // ADD PROCESSORS HERE
                .build();

        visionPortal.setProcessorEnabled(processor, true);  // let processors run asynchronously using camera data

        return visionPortal;
    }

    private void updateExposure(VisionPortal visionPortal, long ms) {
        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        exposureControl.setMode(ExposureControl.Mode.Manual);
        exposureControl.setExposure(ms, TimeUnit.MILLISECONDS);  // exposure may have to be adjusted during competitions
    }

    private long getCorrectedExposure(double averageBrightness) {
        if (averageBrightness < 50) return 50;
        else if (averageBrightness < 80) return 27;
        else if (averageBrightness < 100) return 15;
        else if (averageBrightness < 140) return 14;
        else return 5;
    }

}