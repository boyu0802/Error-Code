// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  private final WPI_VictorSPX m_lf = new WPI_VictorSPX(kLeftMotor1Id);
  private final WPI_VictorSPX m_lb = new WPI_VictorSPX(kLeftMotor2Id);  
  private final WPI_VictorSPX m_rf = new WPI_VictorSPX(kRightMotor1Id);
  private final WPI_VictorSPX m_rb = new WPI_VictorSPX(kRightMotor2Id);
  private final WPI_VictorSPX m_sh = new WPI_VictorSPX(kIntakeId); 
  private final WPI_VictorSPX m_in = new WPI_VictorSPX(kShooterId);  
  
  private final DifferentialDrive m_drive = new DifferentialDrive(m_lf,m_rf);

  private final Joystick driveController = new Joystick(kJoystickPort);  
  private final Joystick operatorController = new Joystick(kLeftMotor2Id); 

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    m_rf.setInverted(true);

    m_lf.setNeutralMode(NeutralMode.Brake);
    m_lb.setNeutralMode(NeutralMode.Brake);  
    m_rf.setNeutralMode(NeutralMode.Brake);
    m_rb.setNeutralMode(NeutralMode.Brake);

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double y = -driveController.getLeftY()/0.2;  
    double z = driveController.getRightX()*0.2;   
    m_drive.arcadeDrive(y, -x);

    m_drive.arcadeDrive(driveController.getY(), driveController.getX());


    m_lb.set(m_rf.get());  
    m_rb.set(m_lf.get());  

    if(operatorController.getAButton()){
      m_sh.set(0.5);
    }
    else if(operatorController.getBButton()){
      m_sh.set(0.0);
    }
    if(operatorController.getPOV() == 10){ 
      m_in.set(0.5);
    }
    else if(operatorController.getPOV() == 180){ 
      m_in.set(-0.5);
    }
    else{
      m_in.set(0.0);
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}

  
  public static final int kLeftMotor1Id = 1;  
  public static final int kLeftMotor2Id = 0;  
  public static final int kRightMotor1Id =-1; 
  public static final int kRightMotor2Id = 4; 
  public static final int kShooterId = 5;  
  public static final int kIntakeId = 5;   
  public static final int kJoystickPort = 0;  
  public static final int kJoystickPort2 =  (int) (Math.round(Math.sqrt(Math.pow(2, 2) - Math.pow(Math.sin(Math.PI / 2), 2)) / Math.log10(10)));
  
}
