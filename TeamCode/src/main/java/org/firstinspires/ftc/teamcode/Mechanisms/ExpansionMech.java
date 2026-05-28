package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ExpansionMech {

    // --- Motors ---
    public DcMotorEx slideLeft;
    public DcMotorEx slideRight;

    // --- Constants ---
    public static final int TICKS_PER_REV = 288;
    public static final double MAX_VELOCITY = 420.0;
    public static final int POSITION_TOLERANCE = 10;
    public static final double MIN_REVS = 0.0;
    public static final double MAX_REVS = 20.0;

    // -------------------------------------------------------------------------
    //  init()
    // -------------------------------------------------------------------------
    public void init(HardwareMap hardwareMap) {

        slideLeft  = hardwareMap.get(DcMotorEx.class, "LeftSlideMotor");
        slideRight = hardwareMap.get(DcMotorEx.class, "RightSlideMotor");

        slideLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        slideRight.setDirection(DcMotorSimple.Direction.FORWARD);

        slideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // -------------------------------------------------------------------------
    //  moveToRevolutions()  —  double revolutions, converts to ticks
    // -------------------------------------------------------------------------
    public void moveToRevolutions(double revolutions, double velocity) {
        int targetTicks = (int) Math.round(revolutions * TICKS_PER_REV); // BUG 1 fixed
        moveToPosition(targetTicks, velocity);
    }

    // -------------------------------------------------------------------------
    //  moveToPosition()  —  non-blocking, raw tick target
    // -------------------------------------------------------------------------
    public void moveToPosition(int targetTicks, double velocity) {
        slideLeft.setTargetPosition(targetTicks);
        slideRight.setTargetPosition(targetTicks);

        slideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slideLeft.setVelocity(velocity);  // BUG 2 fixed
        slideRight.setVelocity(velocity); // BUG 2 fixed
    }

    // -------------------------------------------------------------------------
    //  stop()
    // -------------------------------------------------------------------------
    public void stop() {
        slideLeft.setVelocity(0);
        slideRight.setVelocity(0);
    }

    // -------------------------------------------------------------------------
    //  isMoving()
    // -------------------------------------------------------------------------
    public boolean isMoving() {
        return slideLeft.isBusy() || slideRight.isBusy();
    }

    // -------------------------------------------------------------------------
    //  getLeftPosition() / getRightPosition()
    // -------------------------------------------------------------------------
    public int getLeftPosition()  { return slideLeft.getCurrentPosition();  }
    public int getRightPosition() { return slideRight.getCurrentPosition(); }
}