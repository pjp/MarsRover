package com.controlq;

/**
 * A ENUM to represent a command that can be sent to a Rover, one of
 * <p/>
 * Let Rotation<br/>
 * Right Rotation<br/>
 * Move Forward<br/>
 *
 * @author pjp
 * @version 1
 */
public enum Command {
    L { @Override
        public String toString() { return "L" ; }},  // Rotate left

    R { @Override
        public String toString() { return "R" ; }},  // Rotate right

    M { @Override
        public String toString() { return "M" ; }};  // Move an increment.
}
