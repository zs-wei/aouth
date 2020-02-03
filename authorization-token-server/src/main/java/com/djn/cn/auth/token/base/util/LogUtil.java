package com.djn.cn.auth.token.base.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 
 * <b>类 名：</b>LogUtil<br/>
 * <b>类描述：</b>日志输出用共通类<br/>
 * <b>创建人：</b>djnie<br/>
 * <b>创建时间：</b>2018-7-15 下午4:46:56<br/>
 * <b>修改人：</b>djnie<br/>
 * <b>修改时间：</b>2018-7-15 下午4:46:56<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0<br/>
 * 
 */
public class LogUtil
{

    /**
     * 输出用logger
     */
    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    /**
     * 输出Debug日志
     * 
     * @param message 信息
     */
    public static void debug(String message)
    {
        try
        {
            logger.debug(getThrowableLineString(new LogThrowableInfo()) + "  - " + message);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        }
    }

    /**
     * 输出信息日志
     * 
     * @param message 信息
     */
    public static void info(String message)
    {
        try
        {
            logger.info(getThrowableLineString(new LogThrowableInfo()) + "  - " + message);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        }
    }

    /**
     * 输出trace日志
     * 
     * @param message 信息
     */
    public static void trace(String message)
    {
        try
        {
            logger.trace(getThrowableLineString(new LogThrowableInfo()) + "  - " + message);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        }
    }

    /**
     * 输出错误日志
     * 
     * @param message 信息
     */
    public static void error(String message)
    {
        try
        {
            logger.error(getThrowableLineString(new LogThrowableInfo()) + "  - " + message);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        }
    }

    /**
     * 输出错误日志
     * 
     * @param throwable 异常
     */
    public static void error(Throwable throwable)
    {
        logger.error(throwable.getMessage());
    }

    /**
     * 取得异常信息的文本内容
     * 
     * @return 异常信息
     */
    public static String getThrowableLineString(LogThrowableInfo t)
    {
        String[] lines = t.getThrowableStrRep();
        String traceLine = "";

        if (lines != null && lines.length > 3)
        {
            traceLine = lines[3];
        }

        if (traceLine.startsWith("\tat "))
        {
            traceLine = traceLine.substring(4);
        }

        return traceLine;
    }
    public static void writeAccessLog(Logger log, HttpServletRequest request, long startTime, String... params)
    {
      String paramString = "";
      if (params.length > 0) {
        for (String pa : params) {
          paramString = paramString + "[" + pa + "] ";
        }
      }
      MDC.put("LogDivision", "access");
      log.info("URI:" + request.getRequestURI() + "; SPEND:" + (
        System.currentTimeMillis() - startTime) + "; PARAM:" + paramString + "; IP:" + request
        
        .getRemoteAddr());
      MDC.remove("LogDivision");
    }
}
