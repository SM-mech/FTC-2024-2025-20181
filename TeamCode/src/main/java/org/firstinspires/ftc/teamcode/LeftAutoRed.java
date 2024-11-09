package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="LeftAutoRed", group="Primary")
@SuppressWarnings("FieldCanBeLocal")
public class LeftAutoRed extends LinearOpMode {
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
            autoControls();
            motorTelemetry();
        }
    }

    public void autoControls() {
        forward(1000);
        sleep(2000);
        resetEncoders();
        turn(135);
        sleep(2000);
        resetEncoders();
        forward2(800);
        sleep(2000);
        resetEncoders();
        raiseSlide(-2300);
        sleep(4000);
        forward(250);
        sleep(1500);
        resetEncoders();
        servoOpen();
        sleep(2000);
        forward(-2000);
        sleep(2000);
        resetEncoders();
        lowerSlide(0);
        turn(-45);
        sleep(2000);
        resetEncoders();
        Sideways(-1500);
        sleep(2000);
        resetEncoders();
        forward(-400);
        sleep(14000);
        resetEncoders();








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
    }
    public void initServoRight() {
        servoRight = hardwareMap.get(Servo.class, "servoRight");
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
    public void servoOpen() {
        servoLeft.setPosition(.5);
        servoRight.setPosition(.5);
    }
    public void servoClose() {
        servoLeft.setPosition(0.97);
        servoRight.setPosition(0.03);
    }
    public void forward(double ticks) {
        frontLeft.setTargetPosition((int) (ticks));
        frontRight.setTargetPosition((int) (ticks));
        backLeft.setTargetPosition((int) (ticks));
        backRight.setTargetPosition((int) (ticks));
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(.5);
        frontLeft.setPower(.5);
        backRight.setPower(.5);
        backLeft.setPower(.5);
    }
    public void forward2(double ticks) {
        frontLeft.setTargetPosition((int) (ticks));
        frontRight.setTargetPosition((int) (ticks));
        backLeft.setTargetPosition((int) (ticks));
        backRight.setTargetPosition((int) (ticks));
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(.5);
        frontLeft.setPower(.5);
        backRight.setPower(.5);
        backLeft.setPower(.5);
    }
    //Use +deg for right and -deg for left
    public void turn(double degrees) {
        frontLeft.setTargetPosition((int)(10.889 * degrees));
        frontRight.setTargetPosition((int)(-10.889 * degrees));
        backLeft.setTargetPosition((int)(10.889 * degrees));
        backRight.setTargetPosition((int)(-10.889 * degrees));
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(.5);
        frontLeft.setPower(.5);
        backRight.setPower(.5);
        backLeft.setPower(.5);
    }
    public void Sideways(double ticks) /*Pos for right neg for left */ {
        frontLeft.setTargetPosition((int)(ticks));
        frontRight.setTargetPosition(-(int)(ticks));
        backLeft.setTargetPosition(-(int)(ticks));
        backRight.setTargetPosition((int)(ticks));
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(.5);
        frontLeft.setPower(.5);
        backRight.setPower(.5);
        backLeft.setPower(.5);
    }
    public void resetEncoders () {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void raiseSlide (double Ticks) {
        linearSlide.setTargetPosition((int)Ticks);
        linearSlide.setPower(.3);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void lowerSlide (double Ticks) {
        linearSlide.setTargetPosition((int)Ticks);
        linearSlide.setPower(.3);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


}