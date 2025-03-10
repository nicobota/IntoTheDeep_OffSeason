package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.subsystems.drivebase;

public class BasicAuto extends LinearOpMode {
    public drivebase base = new drivebase(hardwareMap);
    public int stageCounter = 0;
    public String pathFile = "new.path";

    public void runOpMode() {

        waitForStart();

        while(opModeIsActive()) {
            base.updateCurrentPoint();

            fullPath();

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
        // arm action or something
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
