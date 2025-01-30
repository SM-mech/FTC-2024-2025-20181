package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/*Ports ControlHub
Port0 = backRight
Port1 = frontRight
Port2 = backLeft
Port3 = frontLeft

Ports ExpansionHub
Port0 = LinearSlide
*/
@Disabled
@TeleOp(name="DONOTUSEON20181_DriveCodeForOtherRobot", group="Secondary")
@SuppressWarnings("FieldCanBeLocal")
public class DONOTUSEON20181_DriveCodeForOtherRobot extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor linearSlide;
    private Servo servoLeft;
    private Servo servoRight;
    int x = 0; // servo movement
    int y = 0; // was button already pressed?
    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();

        while(!isStarted()) {
            motorTelemetry();
        }
        waitForStart();
        while(opModeIsActive()) {
            teleOpControls();
            motorTelemetry();
        }
    }

    public void teleOpControls() {
        double vertical = -gamepad1.left_stick_x;
        double horizontal = -gamepad1.left_stick_y;
        double pivot = gamepad1.right_stick_x;

        frontRight.setPower((vertical + horizontal - pivot));
        backRight.setPower((vertical - horizontal + pivot));
        frontLeft.setPower((vertical - horizontal - pivot));
        backLeft.setPower((vertical + horizontal + pivot));

        if (gamepad1.dpad_up) {
            linearSlide.setPower(-1);
        }
        else if (gamepad1.dpad_down) {
            linearSlide.setPower(1);
        }
        else {
            linearSlide.setPower(0);
        }

        if (gamepad1.a && y == 0) {
            switch (x) {
                case 0:
                    servoLeft.setPosition(.1);
                    servoRight.setPosition(.9);
                    x = 1;
                    y = 1;
                    break;
                case 1:
                    servoLeft.setPosition(0.5);
                    servoRight.setPosition(0.5);
                    x = 0;
                    y = 1;
                    break;
                default:
                    break;
            }
        } else if (!gamepad1.a) {
            y = 0;
        }
        //failsafe
    }
    public void motorTelemetry() {
        telemetry.addData("frontRight", "Encoder: %2d, Power: %.2f", frontRight.getCurrentPosition(), frontRight.getPower());
        telemetry.addData("frontLeft", "Encoder: %2d, Power: %.2f", frontLeft.getCurrentPosition(), frontLeft.getPower());
        telemetry.addData("backRight", "Encoder: %2d, Power: %.2f", backRight.getCurrentPosition(), backRight.getPower());
        telemetry.addData("backLeft", "Encoder: %2d, Power: %.2f", backLeft.getCurrentPosition(), backLeft.getPower());
        telemetry.addData("linearSlide", "Encoder: %2d, Power: %.2f", linearSlide.getCurrentPosition(), linearSlide.getPower());
        telemetry.update();
    }
    public void initHardware() {
        initFrontRight();
        initFrontLeft();
        initBackRight();
        initBackLeft();
        initLinearSlide();
        initServoLeft();
        initServoRight();
    }
    public void initServoLeft() {
        servoLeft = hardwareMap.get(Servo.class, "servoLeft");
        servoLeft.setDirection(Servo.Direction.REVERSE);
        servoLeft.setPosition(.5);
    }
    public void initServoRight() {
        servoRight = hardwareMap.get(Servo.class, "servoRight");
        servoRight.setDirection(Servo.Direction.REVERSE);
        servoRight.setPosition(.5);
    }
    public void initLinearSlide(){
        linearSlide = hardwareMap.get(DcMotor.class, "linearSlide");
        linearSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void initFrontRight() {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void initFrontLeft() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void initBackLeft() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void initBackRight() {
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        //backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


}

