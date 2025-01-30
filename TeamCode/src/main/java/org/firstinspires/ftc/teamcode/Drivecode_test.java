package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp
@SuppressWarnings("FieldCanBeLocal")
public class Drivecode_test extends OpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor linearSlide;
    private Servo servoLeft;
    private Servo servoRight;

    private PIDController controller;
    public static double p = .04;
    public static double i = .3;
    public static double d = .00001;
    public static double f = .07;
    public static double target = 0;

    int x = 0; // servo movement
    int y = 0; // was button already pressed?
    int s = 0;
    int w = 0;
    int o = 0;
    int q = 0;

    private final double ticks_in_degree = 288 / 360.0;

    private DcMotorEx armRaise;

    @Override
    public void init() {
        initHardware();
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

    }

    @Override
    public void loop() {
        teleOpControls();
        controller.setPID(p, i, d);
        int arm_pos = armRaise.getCurrentPosition();
        double pid = controller.calculate(arm_pos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;
        double power = pid + ff;
        armRaise.setPower(power*.3);

        telemetry.addData("frontRight", "Encoder: %2d, Power: %.2f", frontRight.getCurrentPosition(), frontRight.getPower());
        telemetry.addData("frontLeft", "Encoder: %2d, Power: %.2f", frontLeft.getCurrentPosition(), frontLeft.getPower());
        telemetry.addData("backRight", "Encoder: %2d, Power: %.2f", backRight.getCurrentPosition(), backRight.getPower());
        telemetry.addData("backLeft", "Encoder: %2d, Power: %.2f", backLeft.getCurrentPosition(), backLeft.getPower());
        telemetry.addData("linearSlide", "Encoder: %2d, Power: %.2f", linearSlide.getCurrentPosition(), linearSlide.getPower());
        telemetry.addData("arm_pos ", arm_pos);
        telemetry.addData("target ", target);
        telemetry.update();


    }
    public void teleOpControls() {
        double vertical = -gamepad1.left_stick_y;
        double horizontal = -gamepad1.left_stick_x;
        double pivot = -gamepad1.right_stick_x;

        frontRight.setPower((vertical + horizontal + pivot));
        backRight.setPower((vertical - horizontal + pivot));
        frontLeft.setPower((vertical - horizontal - pivot));
        backLeft.setPower((vertical + horizontal - pivot));

        if (gamepad1.dpad_up && w == 0) {
            switch (o) {
                case 0:
                    linearSlide.setTargetPosition(-1600);
                    linearSlide.setPower(1);
                    linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    o = 1;
                    w = 1;
                    s = 1;
                    break;
                case 1:
                    linearSlide.setTargetPosition(-2090);
                    linearSlide.setPower(1);
                    linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    o = 0;
                    w = 1;
                    s = 1;
                    break;
                default:
                    break;
            }
        } else if (!gamepad1.dpad_up) {
            w = 0;
            if (linearSlide.getCurrentPosition() == linearSlide.getTargetPosition() && s == 1) {
                linearSlide.setPower(.1);
            }
        }
        if (gamepad1.dpad_down){
            linearSlide.setTargetPosition(0);
            linearSlide.setPower(0);
            linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            s = 0;
            o = 0;
        } else if (linearSlide.getCurrentPosition() == 0 && s == 0) {
            linearSlide.setPower(0);
        } else if (linearSlide.getCurrentPosition() <= linearSlide.getTargetPosition() && s == 1) {
            linearSlide.setPower(.1);
        }
        if (linearSlide.getCurrentPosition() >= 10){
            linearSlide.setTargetPosition(0);
            linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlide.setPower(.1);
        }
        if (gamepad1.right_bumper) {
            target = -64;
        } else if (gamepad1.left_bumper) {
            target = 0;

        }
        if (gamepad1.a && y == 0) {
            switch (x) {
                case 0:
                    servoLeft.setPosition(.89);
                    servoRight.setPosition(.11);
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
        if (gamepad1.x && q == 0) {
            q = 1;
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontLeft.setTargetPosition(-700);
            frontRight.setTargetPosition(-700);
            backRight.setTargetPosition(-700);
            backLeft.setTargetPosition(-700);
            while (frontRight.getCurrentPosition() >= frontRight.getTargetPosition() && backLeft.getCurrentPosition() >= backLeft.getTargetPosition() && backRight.getCurrentPosition() >= backRight.getTargetPosition() && frontLeft.getCurrentPosition() >= frontLeft.getTargetPosition()) {
                frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backRight.setPower(.3);
                frontLeft.setPower(.3);
                frontRight.setPower(.3);
                backLeft.setPower(.3);
            }

        }
        else if (q == 1 && gamepad1.right_stick_x <= -.8 || gamepad1.right_stick_x >= .8 || gamepad1.left_stick_y <= -.8 || gamepad1.left_stick_y >= .8 || gamepad1.left_stick_x <= -.8 || gamepad1.left_stick_x >=.8) {
            q = 0;
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
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
        armRaise = hardwareMap.get(DcMotorEx.class, "armRaise");
        armRaise.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        //linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
    public void setArmPosition(int target1) {
        target = target1;
    }

}
