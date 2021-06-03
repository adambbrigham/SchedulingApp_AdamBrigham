package Utilities;

import java.time.LocalDateTime;


/**
 * class for time files
 * @author adamb
 */
public class TimeFiles
{
    /**
     * gets the LocalDateTime of now
     * @return LocalDateTime
     */
    public static LocalDateTime getTimeNow()
    {
        return LocalDateTime.now();
    }

    /**
     * LocalDateTime of when the currentUser logs into the program
     */
    public static LocalDateTime timeOfUserLogin;

}
