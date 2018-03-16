package com.miraclemessages.common;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.miraclemessages.BuildConfig;

/**
 * This is to help use logging in an easier and cleaner way.
 * If its a release Build this would be a Crashlytics log else if its a debug build then it would only log to the terminal.
 * This prevents mixing the production/QA crash logs in Crashlytics with the ones caused by local developer testing in debug mode.
 * <p/>
 *
 * @author shobhit on 2017-08-06.
 */
public class Logger {
    private static final int VERBOSE = Log.VERBOSE;
    private static final int DEBUG = Log.DEBUG;
    private static final int INFO = Log.INFO;
    private static final int WARNING = Log.WARN;
    private static final int ERROR = Log.ERROR;

    public static void verbose(Class<?> currentClass, String message) {
        log(currentClass, VERBOSE, message);
    }

    public static void verbose(Class<?> currentClass, Exception exception) {
        log(currentClass, VERBOSE, exception);
    }

    public static void debug(Class<?> currentClass, String message) {
        log(currentClass, DEBUG, message);
    }

    public static void debug(Class<?> currentClass, Exception exception) {
        log(currentClass, DEBUG, exception);
    }

    public static void info(Class<?> currentClass, String message) {
        log(currentClass, INFO, message);
    }

    public static void info(Class<?> currentClass, Exception exception) {
        log(currentClass, INFO, exception);
    }

    public static void warn(Class<?> currentClass, String message) {
        log(currentClass, WARNING, message);
    }

    public static void warn(Class<?> currentClass, Exception exception) {
        log(currentClass, WARNING, exception);
    }

    public static void error(Class<?> currentClass, String message) {
        log(currentClass, ERROR, message);
    }

    public static void error(Class<?> currentClass, Exception exception) {
        log(currentClass, ERROR, exception);
    }

    private static void log(Class<?> currentClass, int logLevel, String message) {
        switch (logLevel) {
            case VERBOSE:
                if (BuildConfig.DEBUG) {
                    Log.v(currentClass.getName(), message);
                } else {
                    Crashlytics.log(Log.VERBOSE, currentClass.getName(), message);
                }
                break;
            case DEBUG:
                if (BuildConfig.DEBUG) {
                    Log.d(currentClass.getName(), message);
                } else {
                    Crashlytics.log(Log.DEBUG, currentClass.getName(), message);
                }
                break;
            case INFO:
                if (BuildConfig.DEBUG) {
                    Log.i(currentClass.getName(), message);
                } else {
                    Crashlytics.log(Log.INFO, currentClass.getName(), message);
                }
                break;
            case WARNING:
                if (BuildConfig.DEBUG) {
                    Log.w(currentClass.getName(), message);
                } else {
                    Crashlytics.log(Log.WARN, currentClass.getName(), message);
                }
                break;
            case ERROR:
                if (BuildConfig.DEBUG) {
                    Log.e(currentClass.getName(), message);
                } else {
                    Crashlytics.log(Log.ERROR, currentClass.getName(), message);
                }
                break;
            default:
                Log.d(currentClass.getName(), message);
                break;
        }
    }

    /**
     * This is to log full stack trace if exception is passed as parameter.
     * Converting it to string looses the full trace.
     *
     * @param currentClass Class
     * @param logLevel     LogLevel
     * @param exception    Exception
     */
    private static void log(Class<?> currentClass, int logLevel, Exception exception) {
        switch (logLevel) {
            case VERBOSE:
                if (BuildConfig.DEBUG) {
                    Log.v(currentClass.getName(), "", exception);
                } else {
                    Crashlytics.logException(exception);
                }
                break;
            case DEBUG:
                if (BuildConfig.DEBUG) {
                    Log.d(currentClass.getName(), "", exception);
                } else {
                    Crashlytics.logException(exception);
                }
                break;
            case INFO:
                if (BuildConfig.DEBUG) {
                    Log.i(currentClass.getName(), "", exception);
                } else {
                    Crashlytics.logException(exception);
                }
                break;
            case WARNING:
                if (BuildConfig.DEBUG) {
                    Log.w(currentClass.getName(), "", exception);
                } else {
                    Crashlytics.logException(exception);
                }
                break;
            case ERROR:
                if (BuildConfig.DEBUG) {
                    Log.e(currentClass.getName(), "", exception);
                } else {
                    Crashlytics.logException(exception);
                }
                break;
        }
    }
}