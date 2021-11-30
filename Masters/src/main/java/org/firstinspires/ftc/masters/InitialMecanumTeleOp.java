package org.firstinspires.ftc.masters;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="freightFrenzy")
public class InitialMecanumTeleOp extends LinearOpMode {


    RobotClass robot;

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor leftFrontMotor = null;
    DcMotor rightFrontMotor = null;
    DcMotor leftRearMotor = null;
    DcMotor rightRearMotor = null;
    DcMotor intakeMotor = null;
    DcMotor linearSlideMotor = null;

    DcMotor carouselMotor = null;
    // declare motor speed variables
    double RF; double LF; double RR; double LR;
    // declare joystick position variables
    double X1; double Y1; double X2; double Y2;
    final int TOP_ENCODER_VALUE = 111111;
    final int MIDDLE_ENCODER_VALUE = 1111;
    final int BOTTOM_ENCODE_VALUE = 11;
    // operational constants
    double joyScale = 1;
    double motorMax = 0.99; // Limit motor power to this value for Andymark RUN_USING_ENCODER mode



    private enum linearSlideTargets {
        TOP,
        MIDDLE,
        BOTTOM,
        BASE
    }

    public enum linearSlidePositions {
        TOP,
        MIDDLE,
        BOTTOM,
        BASE
    }

    linearSlideTargets linearSlideTarget = linearSlideTargets.BASE;
    linearSlidePositions linearSlidePos = linearSlidePositions.BASE;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new RobotClass(hardwareMap, telemetry, this);

        /* Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        leftFrontMotor = hardwareMap.dcMotor.get("frontLeft");
        rightFrontMotor = hardwareMap.dcMotor.get("frontRight");
        leftRearMotor = hardwareMap.dcMotor.get("backLeft");
        rightRearMotor = hardwareMap.dcMotor.get("backRight");
        carouselMotor = hardwareMap.dcMotor.get("carouselMotor");
        intakeMotor = hardwareMap.dcMotor.get("intake");
        linearSlideMotor = hardwareMap.dcMotor.get("");

        // Set the drive motor direction:
        // "Reverse" the motor that runs backwards when connected directly to the battery
        // These polarities are for the Neverest 20 motors
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set the drive motor run modes:
        // "RUN_USING_ENCODER" causes the motor to try to run at the specified fraction of full velocity
        // Note: We were not able to make this run mode work until we switched Channel A and B encoder wiring into
        // the motor controllers. (Neverest Channel A connects to MR Channel B input, and vice versa.)
//        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        leftRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        boolean carouselOn = false; //Outside of loop()
        boolean intakeOn = false;
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double leftFrontPower = y + x - rx;
            double leftRearPower = y - x - rx;
            double rightFrontPower = y - x + rx;
            double rightRearPower = y + x + rx;

            if (Math.abs(leftFrontPower) > 1 || Math.abs(leftRearPower) > 1 || Math.abs(rightFrontPower) > 1 || Math.abs(rightRearPower) > 1) {

                double max;
                max = Math.max(leftFrontPower, leftRearPower);
                max = Math.max(max, rightFrontPower);
                max = Math.max(max, rightRearPower);

                leftFrontPower /= max;
                leftRearPower /= max;
                rightFrontPower /= max;
                rightRearPower /= max;
            }

            leftFrontMotor.setPower(leftFrontPower);
            leftRearMotor.setPower(leftRearPower);
            rightFrontMotor.setPower(rightFrontPower);
            rightRearMotor.setPower(rightRearPower);

//            // Reset speed variables
//            LF = 0; RF = 0; LR = 0; RR = 0;
//
//            // Get joystick values
//            Y1 = -gamepad1.left_stick_y * joyScale; // invert so up is positive
//            X1 = gamepad1.left_stick_x * joyScale;
//            Y2 = -gamepad1.right_stick_y * joyScale; // Y2 is not used at present
//            X2 = gamepad1.right_stick_x * joyScale;
//
//            // Forward/back movement
//            LF += Y1; RF += Y1; LR += Y1; RR += Y1;
//
//            // Side to Side movement
//            LF += X1; RF -= X1; LR -= X1; RR += X1;
//
//            // Rotation movement
//            LF += X2; RF -= X2; LR += X2; RR -= X2;
//
//            // Clip motor power values to +-motorMax
//            LF = Math.max(-motorMax, Math.min(LF, motorMax));
//            RF = Math.max(-motorMax, Math.min(RF, motorMax));
//            LR = Math.max(-motorMax, Math.min(LR, motorMax));
//            RR = Math.max(-motorMax, Math.min(RR, motorMax));
//
//            // Send values to the motors
//            leftFrontMotor.setPower(LF);
//            rightFrontMotor.setPower(RF);
//            leftRearMotor.setPower(LR);
//            rightRearMotor.setPower(RR);
//
//            // Send some useful parameters to the driver station
//            telemetry.addData("LF", "%.3f", LF);
//            telemetry.addData("RF", "%.3f", RF);
//            telemetry.addData("LR", "%.3f", LR);
//            telemetry.addData("RR", "%.3f", RR);

            if(gamepad2.y && !carouselOn) {
                if(carouselMotor.getPower() != 0) carouselMotor.setPower(0);
                else carouselMotor.setPower(.6);
                carouselOn = true;
            } else if(!gamepad2.y) carouselOn = false;

            if(gamepad2.a && !intakeOn) {
                if(intakeMotor.getPower() != 0) intakeMotor.setPower(0);
                else intakeMotor.setPower(.8);
                intakeOn = true;
            } else if(!gamepad2.a) intakeOn = false;

            if (gamepad2.b) {
                intakeMotor.setPower(-.8);
                intakeOn = false;
            }

            if (gamepad2.dpad_up) {
//                Top scoring
                linearSlideTarget = linearSlideTargets.TOP;
                intakeMotor.setPower(0);
                intakeOn = false;
                linearSlideMotor.setPower(.4);
            }

            if (gamepad2.dpad_left) {
//                Middle scoring
                linearSlideTarget = linearSlideTargets.MIDDLE;
                intakeMotor.setPower(0);
                intakeOn = false;
                linearSlideMotor.setPower(.4);
            }

            if (gamepad2.dpad_down) {
//                Low scoring
                linearSlideTarget = linearSlideTargets.BOTTOM;
                intakeMotor.setPower(0);
                intakeOn = false;
                linearSlideMotor.setPower(.4);
            }

            if (gamepad2.left_trigger >= 35) {
//                Dump
                if (linearSlidePos != linearSlidePositions.BASE) {
//                    dump
                    linearSlideTarget = linearSlideTargets.BASE;
                    linearSlideMotor.setPower(-.4);
                }
            }

            if (linearSlideTarget == linearSlideTargets.TOP) {
                if (linearSlideMotor.getCurrentPosition() >= TOP_ENCODER_VALUE) {
                    linearSlideMotor.setPower(0);
                    linearSlidePos = linearSlidePositions.TOP;
                }
            } else if (linearSlideTarget == linearSlideTargets.MIDDLE) {
                if (linearSlidePos == linearSlidePositions.TOP) {
                    linearSlideMotor.setPower(-.4);
                }
                if (linearSlideMotor.getCurrentPosition() == MIDDLE_ENCODER_VALUE) {
                    linearSlideMotor.setPower(0);
                    linearSlidePos = linearSlidePositions.MIDDLE;
                }
            } else if (linearSlideTarget == linearSlideTargets.BOTTOM) {
                if (linearSlidePos == linearSlidePositions.TOP || linearSlidePos == linearSlidePositions.MIDDLE) {
                    linearSlideMotor.setPower(-.4);
                }
                if (linearSlideMotor.getCurrentPosition() == BOTTOM_ENCODE_VALUE) {
                    linearSlideMotor.setPower(0);
                    linearSlidePos = linearSlidePositions.BOTTOM;
                }
            } else if (linearSlideTarget == linearSlideTargets.BASE) {
                if (linearSlideMotor.getCurrentPosition() == 0) {
                    linearSlideMotor.setPower(0);
                    linearSlidePos = linearSlidePositions.BASE;
                    intakeOn = true;
                    intakeMotor.setPower(.8);
                }
            }

        }
    }
}