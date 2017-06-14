package com.zrdz.diji.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * log日志统计保存
 * create by lx
 * time:2017.5.3
 */

public class LogcatHelper {

    private static LogcatHelper INSTANCE = null;
    private LogDumper mLogDumper = null;
    private int mPId; // 应用进程号
    private boolean isOtherDate;
    private Context context;
    private String tag = "LogcatHelper";

    /**
     * 初始化目录
     */
    public void init(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static LogcatHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LogcatHelper(context);
        }
        return INSTANCE;
    }

    private LogcatHelper(Context context) {
        this.context = context;
        mPId = android.os.Process.myPid();
    }

    public void start() {
        String dirPath = "";
        if (ifSDCardExit()) {
            dirPath = Environment.getExternalStorageDirectory().getPath() + "/";
        } else {
            dirPath = Environment.getRootDirectory().getPath() + "/";
        }
        if (mLogDumper == null) {

            mLogDumper = new LogDumper(dirPath, String.valueOf(mPId), Level.D); // 默认打印Debug级别日志信息
        }
        mLogDumper.start();
        isOtherDate = false;
    }

    public void stop() {
        if (mLogDumper != null) {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }

    public boolean isAlive() {
        if (mLogDumper != null && mLogDumper.getState() != Thread.State.TERMINATED) {
            return true;
        } else {
            if (isOtherDate) {
                return true;
            }
        }
        mLogDumper = null;
        return false;
    }

    /**
     * 日志收集线程
     */
    private class LogDumper extends Thread {

        private Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        String cmds = null;
        private String mPID;
        private String cur_date;
        private FileOutputStream out = null;

        public LogDumper(String dirPath, String pid, String level) {
            if (dirPath == null || "".equals(dirPath)) {
                dirPath = Environment.getRootDirectory().getPath() + "/" + context.getPackageName() + "/";
            }
            mPID = pid;
            cur_date = getFileName();
            init(dirPath);
            checkOutTimeAndDelectLogFile(dirPath, cur_date);
            String dir = dirPath + "zhongrun" + "/";
            Log.i(tag, dir);
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }

            try {
                out = new FileOutputStream(new File(dir, cur_date + ".log"), true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            /**
             * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s v:Verbose d:Debug i:Info
             * w:Warn e:Error f:Fatal s:Silent
             *
             * 根据项目调试需要在此调整日志输出级别,示例： cmds = "logcat *:e *:w | grep \"(" + mPID
             * + ")\""; //打印Error和Warn级别日志信息 cmds = "logcat  | grep \"(" + mPID
             * + ")\""; //打印所有日志信息 cmds = "logcat -s way"; //打印标签过滤信息
             * */
            cmds = "logcat " + level + " | grep \"(" + mPID + ")\"";
        }

        public void stopLogs() {
            mRunning = false;
        }

        @Override
        public void run() {
            try {
                logcatProc = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()), 1024);
                String line = null;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
                    if (out != null && line.contains(mPID)) {
                        out.write((getDateEN() + "  " + line + "\n").getBytes());
                    }

                    if (cur_date.compareTo(getFileName()) != 0) {
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null) {
                    try {
                        mReader.close();
                        mReader = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out = null;
                }
                if (mRunning) {
                    isOtherDate = true;
                    LogcatHelper.getInstance(context).stop();
                    LogcatHelper.getInstance(context).start();
                }
            }
        }

        private String getFileName() {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String date = format.format(new Date(System.currentTimeMillis()));
            return date;
        }

        private String getDateEN() {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date1 = format1.format(new Date(System.currentTimeMillis()));
            return date1;
        }

        private void checkOutTimeAndDelectLogFile(String dirPath, String curDate) {
            try {
                File pathFile = new File(dirPath);
                File[] files = pathFile.listFiles();
                String destDate = findLastMD(curDate);
                if (files == null || files.length == 0) {
                    return;
                }
                for (File file : files) {
                    if (file.getName().compareTo(destDate.substring(0, 6)) < 0) {
                        file.delete();
                    } else if (file.getName().compareTo(destDate.substring(0, 6)) == 0) {
                        File[] logFiles = file.listFiles();
                        for (File logFile : logFiles) {
                            if (destDate.compareTo(logFile.getName().replace(".log", "")) >= 0) {
                                logFile.delete();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String findLastMD(String curDate) {
            int y = Integer.valueOf(curDate.substring(0, 4));
            int m = Integer.valueOf(curDate.substring(4, 6));
            int d = Integer.valueOf(curDate.substring(6, 8));

            if (m == 1) {
                y = y - 1;
                m = 12;
            } else {
                m = m - 1;
            }
            String year = String.valueOf(y);
            String mounth = String.valueOf(m);
            mounth = mounth.length() == 1 ? ("0" + mounth) : mounth;
            String day = String.valueOf(d);
            day = day.length() == 1 ? ("0" + day) : day;

            return year + mounth + day;
        }
    }

    /**
     * log记录级别
     */
    private static class Level {
        public static String V = "*:v";
        public static String D = "*:d";
        public static String I = "*:i";
        public static String W = "*:w";
        public static String E = "*:e";
        public static String F = "*:f";
        public static String S = "*:s";
    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    public boolean ifSDCardExit() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}