package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.pathing.PathReadWriter;
import org.firstinspires.ftc.teamcode.source.Odometry;
import org.firstinspires.ftc.teamcode.source.utility;

public class drivebase {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    public Odometry odometry;
    public Line currentPath;
    public static DistanceUnit mm = DistanceUnit.MM;
    public Point currentPos = new Point(odometry.getPosition().getX(mm), odometry.getPosition().getY(mm));
    public double botHeading = odometry.getPosition().getHeading(AngleUnit.RADIANS);
    public PathReadWriter readWriter = new PathReadWriter();


    // Pathing
    public static class Point {
        public double x, y;
        public Point(double inputX, double inputY) {
            x = inputX;
            y = inputY;
        }
    }
    public static class Line {
        public Point point_1, point_2;
        public Line(Point point1_Input, Point point2_Input) {
            point_1 = point1_Input;
            point_2 = point2_Input;
        }
        public Line(double x1_Input, double y1_Input, double x2_Input, double y2_Input) {
            point_1 = new Point(x1_Input, y1_Input);
            point_2 = new Point(x2_Input, y2_Input);
        }
    }
    public static class Position {
        public double x, y, heading;
        public Position(Point point, double inputheadingradians) {
            x = point.x;
            y = point.y;
            heading = inputheadingradians;
        }
        public Position(double inputx, double inputy, double inputheading) {
            x = inputx;
            y = inputy;
            heading = inputheading;
        }

    }

    public drivebase(HardwareMap map) {
        frontLeft = map.get(DcMotorEx.class, "frontLeft");
        frontRight = map.get(DcMotorEx.class, "frontLeft");
        backLeft = map.get(DcMotorEx.class, "frontLeft");
        backRight = map.get(DcMotorEx.class, "frontLeft");
        odometry = map.get(Odometry.class, "Odometry-Computer");

        //TODO: CONFIGURE WHEEL DIRECTIONS
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    // Testing Wheel Direction use
    public void powerAll(double p) {
        frontLeft.setPower(p);
        frontRight.setPower(p);
        backLeft.setPower(p);
        backRight.setPower(p);

    }

    /// Put in Main Loop to constantly update
    public void updateCurrentPoint() {
        currentPos.x = odometry.getPosition().getX(mm);
        currentPos.y = odometry.getPosition().getY(mm);
        botHeading = odometry.getPosition().getHeading(AngleUnit.RADIANS);
    }

    /// Regular toPosition without power adjustment
    public void toPosition(double targetx, double targety, double targetheading) {
        currentPath = new Line(currentPos, new Point(targetx,targety) );

        final double Kp = 0.05; final double KpTurn = 0.03;

        double errorx = (targetx - currentPos.x); double errory = (targety - currentPos.y); double errorheading = (targetheading - botHeading);
        double driveXpower = (Kp * errorx); double driveYpower = (Kp * errory); double turnpower = (KpTurn * errorheading);

        double frontLeftP = driveYpower + driveXpower + turnpower;
        double frontRightP = driveYpower - driveXpower - turnpower;
        double backLeftP = driveYpower - driveXpower + turnpower;
        double backRightP = driveYpower + driveXpower - turnpower;

        frontLeft.setPower(frontLeftP);
        frontRight.setPower(frontRightP);
        backLeft.setPower(backLeftP);
        backRight.setPower(backRightP);
    }
    public void toPosition(Point target, double targetheading) {toPosition(target.x, target.y, targetheading);}
    public void toPosition(Position targetposition) {toPosition(targetposition.x, targetposition.y, targetposition.heading);}

    /// toPosition with power adjustment (Use with .path files)
    public void toPositionScaled(double targetx, double targety, double targetheading, double scale) {
        currentPath = new Line(currentPos, new Point(targetx,targety) );

        final double Kp = 0.05; final double KpTurn = 0.03;

        double errorx = (targetx - currentPos.x); double errory = (targety - currentPos.y); double errorheading = (targetheading - botHeading);
        double driveXpower = (Kp * errorx); double driveYpower = (Kp * errory); double turnpower = (KpTurn * errorheading);

        double frontLeftP = (driveYpower + driveXpower + turnpower) * scale;
        double frontRightP = (driveYpower - driveXpower - turnpower) * scale;
        double backLeftP = (driveYpower - driveXpower + turnpower) * scale;
        double backRightP = (driveYpower + driveXpower - turnpower) * scale;

        frontLeft.setPower(frontLeftP);
        frontRight.setPower(frontRightP);
        backLeft.setPower(backLeftP);
        backRight.setPower(backRightP);
    }
    public void toPositionScaled(Point target, double targetheading, double scale) {toPositionScaled(target.x, target.y, targetheading, scale);}
    public void toPositionScaled(Position targetposition, double scale) {toPositionScaled(targetposition.x, targetposition.y, targetposition.heading, scale);}

    ///
    public void usePathFromFile(String fileName, int index) {
        double x = readWriter.returnFullPath(fileName, index).x_coord;
        double y = readWriter.returnFullPath(fileName, index).y_coord;
        double heading = readWriter.returnFullPath(fileName, index).heading;
        double scale = readWriter.returnFullPath(fileName, index).power;
        toPositionScaled(x, y, heading, scale);
    }
    public void runFullPathSequence(String fileName, int counter) {
        int indexes = readWriter.getPathIndexQuantity(fileName);

        if (counter <= indexes) {
            double x = readWriter.returnFullPath(fileName, counter).x_coord;
            double y = readWriter.returnFullPath(fileName, counter).y_coord;
            double heading = readWriter.returnFullPath(fileName, counter).heading;
            double scale = readWriter.returnFullPath(fileName, counter).power;

            if (!utility.inRange(currentPos.x, x, 5) && !utility.inRange(currentPos.y, y, 5)) {
                toPositionScaled(x,y,heading,scale);
            }
            else {
                counter ++;
            }
        }
        else {
            return;
        }

    }
}
