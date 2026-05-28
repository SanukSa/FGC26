package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.ExpansionMech;

@TeleOp(name = "Linear Slide TeleOp", group = "TeleOp")
public class ExpandProto extends LinearOpMode {

    private ExpansionMech slide = new ExpansionMech();

    // Tracks how many rotations the driver has dialled in
    private int targetRevolutions = 0;

    // Debounce flags — prevents one press registering as many increments
    private boolean dpadLeftWasPressed  = false;
    private boolean dpadRightWasPressed = false;

    @Override
    public void runOpMode() {

        slide.init(hardwareMap);

        telemetry.addData("Status", "Initialized. Slide at home.");
        telemetry.addData("Controls", "");
        telemetry.addData("  DPAD RIGHT", "Add 1 rotation");
        telemetry.addData("  DPAD LEFT ", "Remove 1 rotation");
        telemetry.addData("  DPAD UP   ", "Extend to target");
        telemetry.addData("  DPAD DOWN ", "Retract to home");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // ------------------------------------------------------------------
            //  DPAD RIGHT — increase target rotations by 1 (with debounce)
            // ------------------------------------------------------------------
            if (gamepad1.dpad_right && !dpadRightWasPressed) {
                if (targetRevolutions < ExpansionMech.MAX_REVS) {
                    targetRevolutions++;
                }
            }
            dpadRightWasPressed = gamepad1.dpad_right;

            // ------------------------------------------------------------------
            //  DPAD LEFT — decrease target rotations by 1 (with debounce)
            // ------------------------------------------------------------------
            if (gamepad1.dpad_left && !dpadLeftWasPressed) {
                if (targetRevolutions > ExpansionMech.MIN_REVS) {
                    targetRevolutions--;
                }
            }
            dpadLeftWasPressed = gamepad1.dpad_left;

            // ------------------------------------------------------------------
            //  DPAD UP — extend to whatever rotation count is currently set
            //  DPAD DOWN — retract fully back to home (0)
            // ------------------------------------------------------------------
            if (gamepad1.dpad_up) {
                slide.moveToRevolutions(targetRevolutions, ExpansionMech.MAX_VELOCITY);

            } else if (gamepad1.dpad_down) {
                targetRevolutions = 0;
                slide.moveToRevolutions(0, ExpansionMech.MAX_VELOCITY);
            }

            // ------------------------------------------------------------------
            //  Telemetry
            // ------------------------------------------------------------------
            telemetry.addData("--- Linear Slide ---", "");
            telemetry.addData("Target Rotations",     targetRevolutions);
            telemetry.addData("Target Ticks",         targetRevolutions * ExpansionMech.TICKS_PER_REV);
            telemetry.addData("Left  Position (ticks)", slide.getLeftPosition());
            telemetry.addData("Right Position (ticks)", slide.getRightPosition());
            telemetry.addData("Slide Moving",           slide.isMoving());
            telemetry.addData("", "");
            telemetry.addData("DPAD RIGHT", "Add rotation    [" + targetRevolutions + " / " + ExpansionMech.MAX_REVS + "]");
            telemetry.addData("DPAD LEFT ", "Remove rotation [" + targetRevolutions + " / " + ExpansionMech.MAX_REVS + "]");
            telemetry.addData("DPAD UP   ", "Extend to target");
            telemetry.addData("DPAD DOWN ", "Retract to home");
            telemetry.update();
        }

        slide.stop();
    }
}