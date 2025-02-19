package org.firstinspires.ftc.archive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "W3DeAuto", group = "DeLorean")
@Disabled
public class W3DeAuto extends LinearOpMode {
    // Declares Motor Variables
    private DcMotor dcBackLeft;
    private DcMotor dcBackRight;
    private DcMotor dcFrontLeft;
    private DcMotor dcFrontRight;
    private DcMotor dcTuckRight;
    private DcMotor dcTuckLeft;
    private DcMotor tetHookLift;

    // Declare servos
    // private Servo svFlipper;
    private Servo svClaim;

    // Declare color sensor(s) here
    // private ColorSensor csMain;

    // Used to specify direction for strafing, turning, or later arch screw intake
    public enum direction {
        LEFT, RIGHT
    }

    // Used to skip future scannings
    // private boolean sensedGold;

    // Number of ticks per rotation for drive motors
    private final int REV_TICK_COUNT = 560;

    public void runOpMode() {
        // Map variables to robot hardware (via config profile on phone)
        dcBackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        dcBackRight = hardwareMap.get(DcMotor.class, "BackRight");
        dcFrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        dcFrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        dcTuckLeft = hardwareMap.get(DcMotor.class, "WheelTuckLeft");
        //dcTuckRight = hardwareMap.get(DcMotor.class, "WheelTuckRight");
        //dcTuckRight = hardwareMap.get(DcMotor.class, "WheelTuckRight");
        tetHookLift = hardwareMap.get(DcMotor.class, "HookLift");

        // Servos
        // svFlipper = hardwareMap.get(Servo.class, "Flipper");
        svClaim = hardwareMap.get(Servo.class, "svClaim");

        // Sensors
        // csMain = hardwareMap.get(ColorSensor.class, "ColorSensor");

        // Reverse motors on one side so all rotate in same direction
        dcBackLeft.setDirection(DcMotor.Direction.REVERSE);
        dcFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        //Sets up speeds for different actions
        double motorSpeed = 0.7;
        double turnSpeed = 0.8;
        double tuckSpeed = 1;
        // double strafeSpeed = 1; Impractical; we only use it once

        waitForStart();

        // Get off hook
        liftWheels(500, tuckSpeed);
        sleep(500);
        tetHookLift.setPower(1);
        sleep(9000);
        tetHookLift.setPower(0);

        // turnDegrees(180, 0.4, direction.RIGHT);

        // Drive to claim site
        // driveInches(72, motorSpeed);

        // Turn and drop claim piece
        // turnDegrees(90, turnSpeed, direction.RIGHT);
        // svClaim.setPosition(0.7);

        // Turn, approach crater (maybe?)

        //region Old Code
        /*
        turnDegrees(15, turnSpeed, direction.RIGHT);

        driveInches(39.5, motorSpeed);

        if(isItYellow()) {
            hitGold();
            sensedGold = true;
        }
        turnDegrees(110, turnSpeed, direction.LEFT);
        driveInches(14.5, motorSpeed);
        if (isItYellow() && !sensedGold) {
            hitGold();
            sensedGold = true;
        }
        driveInches(14.5, motorSpeed);
        if(!sensedGold) {
            hitGold();
        }

        // Go to claim site
        turnDegrees(20, turnSpeed, direction.RIGHT);
        driveInches(30, motorSpeed);

        // Drop claim piece
        svClaim.setPosition(0.8);
        svClaim.setPosition(0);

        // Park on crater
        turnDegrees(110, turnSpeed, direction.RIGHT);
        driveInches(60, motorSpeed);

        //endregion

        //region Pseudocode
        /* Pseudocode
         * 1. Land
         * 2. Turn and approach right mineral of first set
         * START IF/ELSEIF/ELSE STATEMENT
         * 3. Scan it & intake if gold
         *    a. If it is, intake via Archimedes screw & skip 4 + 5
         *   4. Turn and move above next (center) mineral & scan
         *     a. Intake if yellow & skip 5
         *   5. Approach last mineral & intake if other two weren't yellow
         * END
         * 6. Turn toward and approach claim site
         * 7. Drop claim piece (and gold if picked up)
         * 8. Turn towards crater and drive
         * 9. Park on (NOT IN) crater
         */
        //endregion
    }

    //region We don't have a flipper
    /*
    private void hitGold() {
        svFlipper.setPosition(0.7);
        svFlipper.setPosition(0.4);
    }
    */
    //endregion

    private void strafeRightSideOut(long milliseconds, double speed){
        dcBackRight.setPower(speed);
        dcFrontRight.setPower(-speed);

        sleep(milliseconds);

        //Brake all motors
        dcBackRight.setPower(0);
        dcFrontRight.setPower(0);

    }

    private void strafe(double inches, double motorSpeed, direction strafeDirection){
        //Converts degrees into ticks

        double totalDistance = (REV_TICK_COUNT / (Math.PI * 4)) * inches;

        //Resets motor encoders
        dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Check which direction to strafe
        if(strafeDirection == direction.RIGHT)
        {
            dcBackLeft.setTargetPosition((int)totalDistance);
            dcBackRight.setTargetPosition(-(int)totalDistance);
            dcFrontLeft.setTargetPosition(-(int)totalDistance);
            dcFrontRight.setTargetPosition((int)totalDistance);

            dcBackLeft.setPower(motorSpeed);
            dcBackRight.setPower(-motorSpeed);
            dcFrontLeft.setPower(-motorSpeed);
            dcFrontRight.setPower(motorSpeed);
        }
        else {
            dcBackLeft.setTargetPosition((int)totalDistance);
            dcBackRight.setTargetPosition(-(int)totalDistance);
            dcFrontLeft.setTargetPosition(-(int)totalDistance);
            dcFrontRight.setTargetPosition((int)totalDistance);

            dcBackLeft.setPower(motorSpeed);
            dcBackRight.setPower(-motorSpeed);
            dcFrontLeft.setPower(-motorSpeed);
            dcFrontRight.setPower(motorSpeed);
        }

        //Stalls until motors are done
        while (opModeIsActive() && dcBackLeft.isBusy() && dcBackRight.isBusy() && dcFrontLeft.isBusy() && dcFrontRight.isBusy()) {
            idle();
        }

        //Brake all motors
        dcBackRight.setPower(0);
        dcBackLeft.setPower(0);
        dcFrontRight.setPower(0);
        dcFrontLeft.setPower(0);

    }

    private void turnDegrees(double degrees, double turnSpeed, direction turnDirection){
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 10; // Double to 10? 180 degrees was only 90
        double ticks = (degrees * CONVERSION_FACTOR);

        //Reset encoders and make motors run to # of ticks
        dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Check which direction to turn
        if(turnDirection == direction.RIGHT)
        {
            dcBackLeft.setTargetPosition((int)ticks );
            dcBackRight.setTargetPosition(-(int)ticks);
            dcFrontLeft.setTargetPosition((int)ticks );
            dcFrontRight.setTargetPosition(-(int)ticks);

            dcBackLeft.setPower(turnSpeed);
            dcBackRight.setPower(-turnSpeed);
            dcFrontLeft.setPower(turnSpeed);
            dcFrontRight.setPower(-turnSpeed);
        }
        else {
            dcBackLeft.setTargetPosition(-(int)ticks);
            dcBackRight.setTargetPosition((int)ticks);
            dcFrontLeft.setTargetPosition(-(int)ticks);
            dcFrontRight.setTargetPosition((int)ticks);

            dcBackLeft.setPower(-turnSpeed);
            dcBackRight.setPower(turnSpeed);
            dcFrontLeft.setPower(-turnSpeed);
            dcFrontRight.setPower(turnSpeed);
        }

        //Wait until turning is done
        while (opModeIsActive() && dcBackLeft.isBusy() && dcBackRight.isBusy() && dcFrontLeft.isBusy() && dcFrontRight.isBusy()) {
            idle();
        }

        //Stop motors
        dcBackRight.setPower(0);
        dcBackLeft.setPower(0);
        dcFrontRight.setPower(0);
        dcFrontLeft.setPower(0);


    }

    // Landing code
    private void liftWheels (long milliseconds, double tuckSpeed){

        // final int HD_40_1_TICK_COUNT = 1120;
        // double totalRotations = HD_40_1_TICK_COUNT * rotations;

        // dcTuckLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // dcTuckLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // dcTuckLeft.setTargetPosition((int)totalRotations);
        dcTuckLeft.setPower(tuckSpeed);
        sleep(milliseconds);

        // while(opModeIsActive() && dcTuckLeft.isBusy()) {
        //     idle();
        // }

        // dcTuckLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // dcTuckLeft.setPower(0.2);
        // sleep(2000);
        dcTuckLeft.setPower(0);
    }

    private void driveInches(double inches, double motorSpeed){
        //Convert inches to ticks
        double totalDistance = (REV_TICK_COUNT / 12.566) * inches;

        //Reset encoders and make motors run for ticks
        dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Check whether to drive forward or backward
        if(motorSpeed < 0){
            dcBackLeft.setTargetPosition(-(int)totalDistance);
            dcBackRight.setTargetPosition(-(int)totalDistance);
            dcFrontLeft.setTargetPosition(-(int)totalDistance);
            dcFrontRight.setTargetPosition(-(int)totalDistance);
        }
        else {
            dcBackLeft.setTargetPosition((int)totalDistance);
            dcBackRight.setTargetPosition((int)totalDistance);
            dcFrontLeft.setTargetPosition((int)totalDistance);
            dcFrontRight.setTargetPosition((int)totalDistance);
        }

        //Run motors
        dcBackLeft.setPower(motorSpeed);
        dcBackRight.setPower(motorSpeed);
        dcFrontLeft.setPower(motorSpeed);
        dcFrontRight.setPower(motorSpeed);

        //Wait for moving to finish
        while (opModeIsActive() && dcBackLeft.isBusy() && dcBackRight.isBusy()) {
            idle();
        }

        //Stop motors
        dcBackRight.setPower(0);
        dcBackLeft.setPower(0);
        dcFrontLeft.setPower(0);
        dcFrontRight.setPower(0);
    }

    //Function to sense yellow that returns a boolean
    //region We don't have a color sensor
    /*private boolean isItYellow(){
        //Declare boolean isYellow and initialize it to false
        boolean isYellow = false;

        //Senses yellow
        if (csMain.red() > csMain.green() && csMain.blue() < (2 * csMain.green())  / 3) {
            isYellow = true;
        }

        //Return boolean value isYellow
        return isYellow;
    }
    */
    //endregion
}
