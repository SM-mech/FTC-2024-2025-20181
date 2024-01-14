package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Nothing", group = "CenterStage", preselectTeleOp = "Full")
public class Auto_Nothing extends LinearOpMode {

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Doing nothing");
        telemetry.update();

        resetRuntime();
        while (opModeIsActive()){
            telemetry.addData("Status", "Doing nothing for " + getRuntime() + " s");
            telemetry.update();
        }
    }

}
