package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/*Ports ControlHub
Port0 = frontRight
Port1 = backRight
Port2 = backLeft
Port3 = frontLeft

Ports ExpansionHub
Port2 = armRaise
Port3 = LinearSlide
*/
@TeleOp(name="TeleOp v1", group="Primary")
@SuppressWarnings("FieldCanBeLocal")
public class DriveCode extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor linearSlide;
    private DcMotor armRaise;
    private Servo servoLeft;
    private Servo servoRight;
    double coreHexTicks = 288;
    double newTarget;
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
        double vertical = -gamepad1.left_stick_y;
        double horizontal = -gamepad1.left_stick_x;
        double pivot = -gamepad1.right_stick_x;

        frontRight.setPower((vertical + horizontal + pivot));
        backRight.setPower((vertical - horizontal + pivot));
        frontLeft.setPower((vertical - horizontal - pivot));
        backLeft.setPower((vertical + horizontal - pivot));

        if (gamepad1.dpad_up) {
            linearSlide.setTargetPosition(-2300);
            linearSlide.setPower(.3);
            linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else if (gamepad1.dpad_down){
            linearSlide.setTargetPosition(0);
            linearSlide.setPower(.4);
            linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(gamepad1.right_bumper) {
            encoder(.25);
        } else if (gamepad1.left_bumper) {
            encoder(0);
        }
        if (gamepad1.a && y == 0) {
            switch (x) {
                case 0:
                    telemetry.addLine("it actually works");
                    servoLeft.setPosition(0.97);
                    servoRight.setPosition(0.03);
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
    }
    public void motorTelemetry() {
        telemetry.addData("frontRight", "Encoder: %2d, Power: %.2f", frontRight.getCurrentPosition(), frontRight.getPower());
        telemetry.addData("frontLeft", "Encoder: %2d, Power: %.2f", frontLeft.getCurrentPosition(), frontLeft.getPower());
        telemetry.addData("backRight", "Encoder: %2d, Power: %.2f", backRight.getCurrentPosition(), backRight.getPower());
        telemetry.addData("backLeft", "Encoder: %2d, Power: %.2f", backLeft.getCurrentPosition(), backLeft.getPower());
        telemetry.addData("linearSlide", "Encoder: %2d, Power: %.2f", linearSlide.getCurrentPosition(), linearSlide.getPower());
        telemetry.addData("armRaise", "Encoder: %2d, Power: %.2f", armRaise.getCurrentPosition(), armRaise.getPower());
        telemetry.update();
    }
    public void initHardware() {
        initFrontRight();
        initFrontLeft();
        initBackRight();
        initBackLeft();
        initLinearSlide();
        initArmRaise();
        initServoLeft();
        initServoRight();
    }
    public void initArmRaise() {
        armRaise = hardwareMap.get(DcMotor.class, "armRaise");
        armRaise.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRaise.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armRaise.setTargetPosition(79);
        armRaise.setPower(.1);
        armRaise.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void initServoLeft() {
        servoLeft = hardwareMap.get(Servo.class, "servoLeft");
        servoLeft.setPosition(.5);
    }
    public void initServoRight() {
        servoRight = hardwareMap.get(Servo.class, "servoRight");
        servoRight.setPosition(.5);
    }
    public void initLinearSlide(){
        linearSlide = hardwareMap.get(DcMotor.class, "linearSlide");
        linearSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void initFrontRight() {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void initFrontLeft() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void initBackLeft() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void initBackRight() {
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void encoder(double turnage) {
        newTarget = coreHexTicks * turnage;
        armRaise.setTargetPosition((int)newTarget);
        armRaise.setPower(.3);
        armRaise.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }


}

