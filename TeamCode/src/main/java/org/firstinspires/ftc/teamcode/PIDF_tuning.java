package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.arcrobotics.ftclib.controller.PIDController;

@Config
@TeleOp
public class PIDF_tuning extends OpMode {
    private PIDController controller;
    public static double p = .04;
    public static double i = .3;
    public static double d = .003;
    public static double f = .07;
    public static double target = 0;

    private final double ticks_in_degree = 288 / 360.0;

    private DcMotorEx armRaise;

    @Override
    public void init() {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        armRaise = hardwareMap.get(DcMotorEx.class, "armRaise");
        controller.setPID(p, i, d);
        int arm_pos = armRaise.getCurrentPosition();
        double pid = controller.calculate(arm_pos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;
        double power = pid + ff;
        armRaise.setPower(power);

    }

    @Override
    public void loop() {
        controller.setPID(p, i, d);
        int arm_pos = armRaise.getCurrentPosition();
        double pid = controller.calculate(arm_pos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;
        double power = pid + ff;
        armRaise.setPower(power);

        telemetry.addData("arm_pos ", arm_pos);
        telemetry.addData("target ", target);
        telemetry.update();
    }
    public void setArmPosition(int target) {
        controller.setPID(p, i, d);
        int arm_pos = armRaise.getCurrentPosition();
        double pid = controller.calculate(arm_pos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;
        double power = pid + ff;
        armRaise.setPower(power);
    }

}
