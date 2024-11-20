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
    int z = 0; // for arm power
    int s = 0;
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
            linearSlide.setTargetPosition(-2100);
            linearSlide.setPower(.4);
            linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            s = 1;
        }
        else if (gamepad1.dpad_down){
            linearSlide.setTargetPosition(0);
            linearSlide.setPower(.4);
            linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            s = 0;
        } else if (linearSlide.getCurrentPosition() == 0 && s == 0) {
            linearSlide.setPower(0);
        } else if (linearSlide.getCurrentPosition() == -2030 && s == 1) {
            linearSlide.setPower(.1);
        }

        if(gamepad1.right_bumper) {
            z = 0;
            encoder(0, .5);
        } else if (gamepad1.left_bumper) {
            encoder(-.25, 1);
            z = 1;
        }
        else if (armRaise.getCurrentPosition() == 0 && z == 0) {
            armRaise.setPower(0);
        } else if (armRaise.getCurrentPosition() == -.25 && z == 1) {
            armRaise.setPower(.2);
        }
        if (gamepad1.a && y == 0) {
            switch (x) {
                case 0:
                    servoLeft.setPosition(1);
                    servoRight.setPosition(.03);
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
        if (gamepad1.y) {
            armRaise.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            armRaise.setPower(.5);
        }
        if (gamepad1.right_trigger >= .9 && gamepad1.left_trigger >= .9) {
            armRaise.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armRaise.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
    public void encoder(double turnage, double speed) {
        newTarget = coreHexTicks * turnage;
        armRaise.setTargetPosition((int)newTarget);
        armRaise.setPower(speed);
        armRaise.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }


}

