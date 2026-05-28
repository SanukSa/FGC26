package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.ExpansionMech;

@TeleOp(name = "Slide Preset TeleOp", group = "TeleOp")
public class ExpandPreset extends LinearOpMode {

    private ExpansionMech slide = new ExpansionMech();

    // ---------------------------------------------------------------
    //  TUNE THIS — how many full output shaft rotations to the
    //  target position. Change this number before each run.
    // ---------------------------------------------------------------
    private static final double PRESET_REVOLUTIONS = 2.28;

    @Override
    public void runOpMode() {

        slide.init(hardwareMap);

        telemetry.addData("Status", "Initialized. Slide at home.");
        telemetry.addData("Preset Rotations", PRESET_REVOLUTIONS);
        telemetry.addData("Preset Ticks", PRESET_REVOLUTIONS * ExpansionMech.TICKS_PER_REV);
        telemetry.addData("Controls", "");
        telemetry.addData("  X", "Move to preset position");
        telemetry.addData("  B", "Return home");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // X — move to preset position
            if (gamepad1.x) {
                slide.moveToRevolutions(PRESET_REVOLUTIONS, ExpansionMech.MAX_VELOCITY);
            }

            // B — return to home (fully retracted)
            if (gamepad1.b) {
                slide.moveToRevolutions(0, ExpansionMech.MAX_VELOCITY);
            }

            // Telemetry
            telemetry.addData("--- Linear Slide ---", "");
            telemetry.addData("Preset Revolutions", PRESET_REVOLUTIONS);
            telemetry.addData("Preset Ticks", PRESET_REVOLUTIONS * ExpansionMech.TICKS_PER_REV);
            telemetry.addData("Left  Position (ticks)", slide.getLeftPosition());
            telemetry.addData("Right Position (ticks)", slide.getRightPosition());
            telemetry.addData("Slide Moving", slide.isMoving());
            telemetry.addData("", "");
            telemetry.addData("X", "Extend to preset");
            telemetry.addData("B", "Return home");
            telemetry.update();
        }

        slide.stop();
    }
}