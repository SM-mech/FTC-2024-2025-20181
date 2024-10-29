package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/*Ports
Port0 = frontRight
Port1 = backRight
Port2 = backLeft
Port3 = frontLeft
The commit and push didn't work*/
@TeleOp(name="TeleOp v1", group="Primary")
@SuppressWarnings("FieldCanBeLocal")
public class DriveCode extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor armMotor;
    private DcMotor armRaise;
    private Servo servoOne;
    private Servo servoTwo;

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
        double vertical = gamepad1.left_stick_y;
        double horizontal = -gamepad1.left_stick_x;
        double pivot = -gamepad1.right_stick_x;

        frontRight.setPower(-(vertical - horizontal - pivot));
        backRight.setPower(-(vertical + horizontal -pivot));
        frontLeft.setPower(-(vertical + horizontal + pivot));
        backLeft.setPower(-(vertical - horizontal + pivot));

        if (gamepad1.dpad_up) {
            armMotor.setPower(-0.4);
        }
        else {
            armMotor.setPower(0);
        }
        if(gamepad1.right_bumper) {
            armRaise.setPower(.3);
        } else if (gamepad1.left_bumper) {
            armRaise.setPower(-.3);
        }
        else {
            armRaise.setPower(0);
        }

        if (gamepad1.b) {
            servoOne.setPosition(.5);
            servoTwo.setPosition(.7);
        }
        if (gamepad1.a) {
            servoOne.setPosition(.3);
            servoTwo.setPosition(.8);
        }



    }
    public void motorTelemetry() {
        telemetry.addData("frontRight", "Encoder: %2d, Power: %.2f", frontRight.getCurrentPosition(), frontRight.getPower());
        telemetry.addData("frontLeft", "Encoder: %2d, Power: %.2f", frontLeft.getCurrentPosition(), frontLeft.getPower());
        telemetry.addData("backRight", "Encoder: %2d, Power: %.2f", backRight.getCurrentPosition(), backRight.getPower());
        telemetry.addData("backLeft", "Encoder: %2d, Power: %.2f", backLeft.getCurrentPosition(), backLeft.getPower());
        telemetry.addData("armMotor", "Encoder: %2d, Power: %.2f", armMotor.getCurrentPosition(), armMotor.getPower());
        telemetry.addData("armRaise", "Encoder: %2d, Power: %.2f", armRaise.getCurrentPosition(), armRaise.getPower());
        telemetry.update();
    }
    public void initHardware() {
        initFrontRight();
        initFrontLeft();
        initBackRight();
        initBackLeft();
        initArmMotor();
        initArmRaise();
        initServoOne();
        initServoTwo();
    }
    public void initArmRaise() {
        armRaise = hardwareMap.get(DcMotor.class, "armRaise");
        armRaise.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRaise.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void initServoOne() {
        servoOne = hardwareMap.get(Servo.class, "servoOne");
        servoOne.setPosition(-1);
    }
    public void initServoTwo() {
        servoTwo = hardwareMap.get(Servo.class, "servoTwo");
        servoOne.setPosition(1);
    }
    public void initArmMotor(){
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


}

