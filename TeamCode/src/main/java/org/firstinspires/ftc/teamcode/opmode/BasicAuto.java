package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.subsystems.drivebase;

public class BasicAuto extends LinearOpMode {
    public drivebase base = new drivebase(hardwareMap);
    public GamepadEx pad = new GamepadEx(gamepad1);
    public int stageCounter = 0;
    public String pathFile = "new.path";
    public boolean runFullSequence = false;

    public void runOpMode() {

        waitForStart();

        while(opModeIsActive()) {
            base.updateCurrentPoint();

            if (runFullSequence) {
                if (pad.wasJustPressed(GamepadKeys.Button.A)) {
                    fullPath();
                }
            }
            else {
                if (pad.wasJustPressed(GamepadKeys.Button.A)) {
                    stage0();
                    stage1();
                    stage2();
                }
            }

            telemetry.addData(
                    "Current Path",
                    "(%.2f, %.2f) to (%.2f, %.2f)",
                    base.currentPath.point_1.x,
                    base.currentPath.point_1.y,
                    base.currentPath.point_2.x,
                    base.currentPath.point_2.y);
            telemetry.update();
        }

    }

    public void stage0() {
        base.usePathFromFile(pathFile, 0);
    }
    public void stage1() {
        base.usePathFromFile(pathFile, 1);
    }
    public void stage2() {
        base.usePathFromFile(pathFile, 2);
    }
    public void fullPath() {
        base.runFullPathSequence(pathFile, 0);
    }


}
