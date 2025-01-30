package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "RightAutoRed")
@SuppressWarnings("InnerClassMayBeStatic")
public class RightAutoRed extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(13.4, -62.7, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        DcMotor linearSlide = hardwareMap.get(DcMotor.class, "linearSlide");
        Servo servoLeft = hardwareMap.get(Servo.class, "servoLeft");
        Servo servoRight = hardwareMap.get(Servo.class, "servoRight");
        DcMotorEx armRaise = hardwareMap.get(DcMotorEx.class, "armRaise");
        armRaise.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRaise.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armRaise.setTargetPositionTolerance(3);
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        servoLeft.setDirection(Servo.Direction.REVERSE);
        servoRight.setDirection(Servo.Direction.REVERSE);

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(initialPose)
                        .stopAndAdd(new ArmPivot(0,armRaise,.5,true))
                        .stopAndAdd(new ServoClose(servoLeft, servoRight, false))
                        .stopAndAdd(new LinearSlide(-1600, linearSlide, .5, false))
                        .strafeToConstantHeading(new Vector2d(0,-41))
                        .stopAndAdd(new ArmPivot(-82, armRaise, 1, false))
                        .waitSeconds(1.5)
                        .stopAndAdd(new ServoOpen(servoLeft, servoRight, .4, .6, true))
                        .waitSeconds(.5)
                        .strafeToConstantHeading(new Vector2d(0, -50))
                        .stopAndAdd(new ArmPivot(-30,armRaise,.5,false))
                        //.stopAndAdd(new ArmPivot(-72, armRaise, 1, true))
                        .stopAndAdd(new LinearSlide(0, linearSlide,.5, false))
                        .strafeToConstantHeading(new Vector2d(10,-50))
                        .splineToConstantHeading(new Vector2d(48.7,-5.4),90)
                        .strafeToConstantHeading(new Vector2d(60, -5.4))
                        .strafeTo(new Vector2d(65,-58))
                        .lineToYSplineHeading(-35,Math.toRadians(270))
                        .strafeToConstantHeading(new Vector2d(65,-34.5))
                        .stopAndAdd(new ArmPivot(-72, armRaise, .3, false))
                        .waitSeconds(2.5)
                        .stopAndAdd(new ServoClose(servoLeft, servoRight, true))
                        .waitSeconds(.5)
                        .stopAndAdd(new ArmPivot(0,armRaise,1,false))
                        .stopAndAdd(new LinearSlide(-1600, linearSlide, .6, false))
                        .strafeToSplineHeading(new Vector2d(10, -42.25),Math.toRadians(90))
                        .waitSeconds(.5)
                        .stopAndAdd(new ArmPivot(-82, armRaise, .3, false))
                        .waitSeconds(2)
                        .stopAndAdd(new ServoOpen(servoLeft, servoRight, .5, .5,true))
                        .waitSeconds(2)
                        .stopAndAdd(new ArmPivot(5,armRaise,1,false))
                        .stopAndAdd(new LinearSlide(0, linearSlide,.5, false))
                        .strafeToConstantHeading(new Vector2d(65, -60))
                        .waitSeconds(10)
                        .splineToConstantHeading(new Vector2d(65, -50), Math.toRadians(270))
                        .stopAndAdd(new ArmPivot(-72, armRaise, .3, true))
                        .waitSeconds(1)
                        .lineToY(-45)
                        .stopAndAdd(new ServoClose(servoLeft, servoRight, true))
                        .stopAndAdd(new ArmPivot(0,armRaise,.5,false))
                        .stopAndAdd(new LinearSlide(-1600, linearSlide, .6, false))
                        .splineToConstantHeading(new Vector2d(0, -39.46),Math.toRadians(90))
                        .stopAndAdd(new ArmPivot(-72, armRaise, .3, true))
                        .waitSeconds(.5)
                        .stopAndAdd(new ServoOpen(servoLeft, servoRight, .5, .5,true))
                        .waitSeconds(2)
                        .strafeToConstantHeading(new Vector2d(0, -50))
                        .stopAndAdd(new ArmPivot(0,armRaise,.5,false))
                        .stopAndAdd(new LinearSlide(0, linearSlide,.5, false))
                        .waitSeconds(1.75)
                        .strafeToConstantHeading(new Vector2d(65,-50))
                        .build());
    }
    public class LinearSlide implements Action {
        DcMotor linearSlide;
        int setPosition;
        int setUp = 0;
        double power;
        boolean Wait;

        public LinearSlide (int setPosition, DcMotor linearSlide, double power, boolean Wait) {
            this.setPosition = setPosition;
            this.linearSlide = linearSlide;
            this.power = power;
            this.Wait = Wait;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (setUp == 0) {
                linearSlide.setTargetPosition(setPosition);
                linearSlide.setPower(power);
                linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                setUp = 1;
            }
            if (Wait) {
                return linearSlide.getCurrentPosition() != linearSlide.getTargetPosition();
            }
            else {
                return false;
            }

        }
    }
    public class ArmPivot implements Action {
        DcMotor ArmRaise;
        int setPosition;
        int setUp = 0;
        double power;
        boolean Wait;

        public ArmPivot (int setPosition, DcMotor ArmRaise, double power, boolean Wait) {
            this.setPosition = setPosition;
            this.ArmRaise = ArmRaise;
            this.power = power;
            this.Wait = Wait;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (setUp == 0) {
                ArmRaise.setTargetPosition(setPosition);
                ArmRaise.setPower(power);
                ArmRaise.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                setUp = 1;
            }
            if (Wait) {
                return ArmRaise.getCurrentPosition() != ArmRaise.getTargetPosition();
            }
            else {
                return false;
            }
        }
    }
    public class ServoOpen implements Action {
        Servo servoLeft;
        Servo servoRight;
        double position;
        double position2;
        int setUp = 0;
        boolean Wait;

        public ServoOpen (Servo servoLeft, Servo servoRight, double position, double position2, boolean Wait) {
            this.servoLeft = servoLeft;
            this.servoRight = servoRight;
            this.position = position;
            this.position2 = position2;
            this.Wait = Wait;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (setUp == 0) {
                servoLeft.setPosition(position);
                servoRight.setPosition(position2);
                setUp = 1;
            }
            if (Wait) {
                return servoLeft.getPosition() != position;
            }
            else {
                return false;
            }
        }
    }
    public class ServoClose implements Action {
        Servo servoLeft;
        Servo servoRight;
        int setUp = 0;
        boolean Wait;

        public ServoClose (Servo sL1, Servo sR1, boolean Wait) {
            this.servoLeft = sL1;
            this.servoRight = sR1;
            this.Wait = Wait;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (setUp == 0) {
                servoLeft.setPosition(.89);
                servoRight.setPosition(.11);
                setUp = 1;
            }
            if (Wait) {
                return servoLeft.getPosition() != .89;
            }
            else {
                return false;
            }

        }
        }
    /*public class ServoAction implements Action {
        Servo servo;
        double position;

        public ServoAction(Servo s, double w) {
            this.servo = s;
            this.position = w;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            servo.setPosition(position);
            return false;
        }
    }*/
}



