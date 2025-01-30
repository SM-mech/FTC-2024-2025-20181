package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@TeleOp
@Config
public class SlideRaiseTest extends OpMode {
    DcMotor linearSlide;
    DcMotorEx armRaise;
    public static double setPosition = 0;
    @Override
    public void init() {
        linearSlide = hardwareMap.get(DcMotor.class, "linearSlide");
        armRaise = hardwareMap.get(DcMotorEx.class, "armRaise");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }
    @Override
    public void loop() {
        linearSlide.setTargetPosition((int)setPosition);
        armRaise.setTargetPosition(0);
        armRaise.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRaise.setPower(1);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlide.setPower(1);
        telemetry.addData("Position", linearSlide.getCurrentPosition());
        telemetry.update();
    }
}
