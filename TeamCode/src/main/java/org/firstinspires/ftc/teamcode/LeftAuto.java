package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="AutoLeft", group="Primary")
@SuppressWarnings("FieldCanBeLocal")
public class LeftAuto extends LinearOpMode {
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
    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();

        while(!isStarted()) {
            motorTelemetry();
        }
        waitForStart();
        while(opModeIsActive()) {
            motorTelemetry();
            autoControls();

        }
    }

    public void autoControls() {
        while (frontLeft.isBusy()) {
            sleep(10);
        }
        //First block in basket code
        raiseSlide(-2185, .3);
        forward(1000, .7);
        sleep();

        resetEncoders();
        turn(-130, .7);
        sleep();

        resetEncoders();
        forward(800, .7);
        sleep();

        resetEncoders();
        Sideways(250, .7);
        sleep();

        resetEncoders();
        forward(550, .7);
        sleep();

        resetEncoders();
        servoOpen();
        sleep(1750);
        servoClose();

        //Second block in basket
        turn(130, .7);
        sleep();
        resetEncoders();

        lowerSlide(0);
        forward(300, .7);
        sleep();

        resetEncoders();
        Sideways(720, .7);
        servoOpen();
        sleep();

        resetEncoders();
        encoder(-.23, 1);
        sleep(2500);

        resetEncoders();
        forward(850, .7);
        sleep();

        resetEncoders();
        servoClose();
        sleep(1000);
        raiseSlide(-2185, .5);
        encoder(0, .8);
        turn(-140, .7);
        sleep();

        resetEncoders();
        forward(675, .7);
        sleep();

        resetEncoders();
        forward(550, .7);
        sleep();

        resetEncoders();
        servoOpen();
        sleep(750);
        servoClose();

        //Park
        turn(135, 1);
        sleep();

        resetEncoders();
        lowerSlide(0);
        //Sideways(-6900, 1);
        sleep(30000);


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
        //servoLeft.setPosition(.89);
        servoLeft.setDirection(Servo.Direction.REVERSE);
    }
    public void initServoRight() {
        servoRight = hardwareMap.get(Servo.class, "servoRight");
        //servoRight.setPosition(.11);
        servoRight.setDirection(Servo.Direction.REVERSE);
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
    public void encoder(double turnage, double power) {
        newTarget = coreHexTicks * turnage;
        armRaise.setTargetPosition((int)newTarget);
        armRaise.setPower(power);
        armRaise.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }
    public void servoOpen() {
        servoLeft.setPosition(.5);
        servoRight.setPosition(.5);
    }
    public void servoClose() {
        servoLeft.setPosition(0.89);
        servoRight.setPosition(0.11);
    }
    public void forward(double ticks, double power) {
        frontLeft.setTargetPosition((int) (ticks));
        frontRight.setTargetPosition((int) (ticks));
        backLeft.setTargetPosition((int) (ticks));
        backRight.setTargetPosition((int) (ticks));
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
    }
    //Use +deg for right and -deg for left
    public void turn(double degrees, double power) {
        frontLeft.setTargetPosition((int)(10.889 * degrees));
        frontRight.setTargetPosition((int)(-10.889 * degrees));
        backLeft.setTargetPosition((int)(10.889 * degrees));
        backRight.setTargetPosition((int)(-10.889 * degrees));
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
    }
    public void Sideways(double ticks, double power) /*Pos for right neg for left */ {
        frontLeft.setTargetPosition((int)(ticks));
        frontRight.setTargetPosition(-(int)(ticks));
        backLeft.setTargetPosition(-(int)(ticks));
        backRight.setTargetPosition((int)(ticks));
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
    }
    public void resetEncoders () { // resets wheel encoders
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void raiseSlide (double Ticks, double power) {
        linearSlide.setTargetPosition((int)Ticks);
        linearSlide.setPower(power);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void lowerSlide (double Ticks) {
        linearSlide.setTargetPosition((int)Ticks);
        linearSlide.setPower(.7);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void sleep() {
        while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy()) {
            sleep(10);
        }
    }


}