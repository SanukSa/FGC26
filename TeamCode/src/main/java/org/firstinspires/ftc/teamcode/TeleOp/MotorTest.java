package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Slide Raw Power", group = "TeleOp")
public class MotorTest extends LinearOpMode {

    DcMotor slideLeft;
    DcMotor slideRight;

    @Override
    public void runOpMode() {

        slideLeft  = hardwareMap.get(DcMotor.class, "LeftSlideMotor");
        slideRight = hardwareMap.get(DcMotor.class, "RightSlideMotor");

        slideLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        slideRight.setDirection(DcMotorSimple.Direction.FORWARD);

        // No encoders at all
        slideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        slideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Ready");
        telemetry.addData("Controls", "DPAD UP = Extend  |  DPAD DOWN = Retract  |  Release = Stop");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.dpad_up) {
                // Extend — full power forward
                slideLeft.setPower(1.0);
                slideRight.setPower(1.0);

            } else if (gamepad1.dpad_down) {
                // Retract — full power backward
                slideLeft.setPower(-1.0);
                slideRight.setPower(-1.0);

            } else {
                // Release button — stop and brake
                slideLeft.setPower(0);
                slideRight.setPower(0);
            }

            telemetry.addData("Left  Power", slideLeft.getPower());
            telemetry.addData("Right Power", slideRight.getPower());
            telemetry.update();
        }

        slideLeft.setPower(0);
        slideRight.setPower(0);
    }
}