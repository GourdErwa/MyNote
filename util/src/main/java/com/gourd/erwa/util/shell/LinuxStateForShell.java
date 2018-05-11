/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * Blog  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 */

package com.gourd.erwa.util.shell;


import com.google.common.base.MoreObjects;
import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 远程调用Linux com.gourd.erwa.shell 命令
 *
 * @author wei.Li by 14-9-2.
 */
public class LinuxStateForShell {


    private static final String CPU_MEM_SHELL = "top -b -n 1";
    private static final String FILES_SHELL = "df -hl";
    private static final String[] COMMANDS = {CPU_MEM_SHELL, FILES_SHELL};
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static Session session;

    /**
     * 连接到指定的HOST
     *
     * @return isConnect
     * @throws JSchException JSchException
     */
    private static boolean connect(String user, String passwd, String host) {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(passwd);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println("connect error !");
            return false;
        }
        return true;
    }

    /**
     * 远程连接Linux 服务器 执行相关的命令
     *
     * @param commands 执行的脚本
     * @param user     远程连接的用户名
     * @param passwd   远程连接的密码
     * @param host     远程连接的主机IP
     * @return 最终命令返回信息
     */
    private static Map<String, String> runDistanceShell(String[] commands, String user, String passwd, String host) {
        if (!connect(user, passwd, host)) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        StringBuilder stringBuffer;

        BufferedReader reader = null;
        Channel channel = null;
        try {
            for (String command : commands) {
                stringBuffer = new StringBuilder();
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);

                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);

                channel.connect();
                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                String buf;
                while ((buf = reader.readLine()) != null) {

                    //舍弃PID 进程信息
                    if (buf.contains("PID")) {
                        break;
                    }
                    stringBuffer.append(buf.trim()).append(LINE_SEPARATOR);
                }
                //每个命令存储自己返回数据-用于后续对返回数据进行处理
                map.put(command, stringBuffer.toString());
            }
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (channel != null) {
                channel.disconnect();
            }
            session.disconnect();
        }
        return map;
    }


    /**
     * 直接在本地执行 com.gourd.erwa.shell
     *
     * @param commands 执行的脚本
     * @return 执行结果信息
     */
    public static Map<String, String> runLocalShell(String[] commands) {
        Runtime runtime = Runtime.getRuntime();

        Map<String, String> map = new HashMap<>();
        StringBuilder stringBuffer;

        BufferedReader reader;
        Process process;
        for (String command : commands) {
            stringBuffer = new StringBuilder();
            try {
                process = runtime.exec(command);
                InputStream inputStream = process.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String buf;
                while ((buf = reader.readLine()) != null) {
                    //舍弃PID 进程信息
                    if (buf.contains("PID")) {
                        break;
                    }
                    stringBuffer.append(buf.trim()).append(LINE_SEPARATOR);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            //每个命令存储自己返回数据-用于后续对返回数据进行处理
            map.put(command, stringBuffer.toString());
        }
        return map;
    }


    /**
     * 处理 com.gourd.erwa.shell 返回的信息
     * <p>
     * 具体处理过程以服务器返回数据格式为准
     * 不同的Linux 版本返回信息格式不同
     *
     * @param result com.gourd.erwa.shell 返回的信息
     * @return 最终处理后的信息
     */
    private static States disposeResultMessage(Map<String, String> result) {

        States states = new States();

        StringBuilder buffer = new StringBuilder();

        for (String command : COMMANDS) {
            String commandResult = result.get(command);
            if (null == commandResult) continue;

            if (command.equals(CPU_MEM_SHELL)) {
                String[] strings = commandResult.split(LINE_SEPARATOR);
                //将返回结果按换行符分割
                for (String line : strings) {
                    line = line.toUpperCase();//转大写处理

                    //处理CPU Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
                    if (line.startsWith("CPU(S):")) {
                        String cpuStr = "CPU 用户使用占有率:";
                        try {
                            cpuStr += line.split(":")[1].split(",")[0].replace("US", "");
                        } catch (Exception e) {
                            e.printStackTrace();
                            cpuStr += "计算过程出错";
                        }
                        buffer.append(cpuStr).append(LINE_SEPARATOR);

                        states.cpuUsStates = cpuStr;//设置CPU状态

                        //处理内存 Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
                    } else if (line.startsWith("MEM")) {
                        String memStr = "内存使用情况:";
                        try {
                            Pattern pattern = Pattern.compile("(\\d.{2,3}kused})");
                            memStr += line.split(":")[1]
                                    .replace("TOTAL", "总计")
                                    .replace("USED", "已使用")
                                    .replace("FREE", "空闲")
                                    .replace("BUFFERS", "缓存");

                        } catch (Exception e) {
                            e.printStackTrace();
                            memStr += "计算过程出错";
                        }
                        buffer.append(memStr).append(LINE_SEPARATOR);

                        states.memStates = memStr;//设置内存状态
                    }
                }
            } else if (command.equals(FILES_SHELL)) {
                //处理系统磁盘状态
                String filesSystem = "系统磁盘状态:";
                try {
                    filesSystem += disposeFilesSystem(commandResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    filesSystem += "计算过程出错";
                }
                buffer.append(filesSystem).append(LINE_SEPARATOR);

                states.filesSystemStates = filesSystem;//设置磁盘状态
            }
        }

        return states;
    }

    //处理系统磁盘状态

    /**
     * Filesystem            Size  Used Avail Use% Mounted on
     * /dev/sda3             442G  327G   93G  78% /
     * tmpfs                  32G     0   32G   0% /dev/shm
     * /dev/sda1             788M   60M  689M   8% /boot
     * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
     *
     * @param commandResult 处理系统磁盘状态shell执行结果
     * @return 处理后的结果
     */
    private static String disposeFilesSystem(String commandResult) {
        String[] strings = commandResult.split(LINE_SEPARATOR);

        // final String PATTERN_TEMPLATE = "([a-zA-Z0-9%_/]*)\\s";
        int size = 0;
        int used = 0;
        for (int i = 0; i < strings.length - 1; i++) {
            if (i == 0) continue;

            int temp = 0;
            for (String s : strings[i].split("\\b")) {
                if (temp == 0) {
                    temp++;
                    continue;
                }
                if (!s.trim().isEmpty()) {
                    if (temp == 1) {
                        size += disposeUnit(s);
                        temp++;
                    } else {
                        used += disposeUnit(s);
                        temp = 0;
                    }
                }
            }
        }
        return "大小 " + size + "G , 已使用" + used + "G ,空闲" +
                (size - used) + "G";
    }

    /**
     * 处理单位转换
     * K/KB/M/T 最终转换为G 处理
     *
     * @param s 带单位的数据字符串
     * @return 以G 为单位处理后的数值
     */
    private static int disposeUnit(String s) {

        try {
            s = s.toUpperCase();
            String lastIndex = s.substring(s.length() - 1);
            String num = s.substring(0, s.length() - 1);
            int parseInt = Integer.parseInt(num);
            switch (lastIndex) {
                case "G":
                    return parseInt;
                case "T":
                    return parseInt * 1024;
                case "M":
                    return parseInt / 1024;
                case "K":
                case "KB":
                    return parseInt / (1024 * 1024);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static void main(String[] args) {
        Map<String, String> result = runDistanceShell(COMMANDS, "root", "123", "192.168.1.233");

        //实体类
        final States states = disposeResultMessage(result);

        System.out.println(states);
        //runLocalShell(COMMANDS);
    }

}


/**
 * 封装实体类
 */
class States {

    String cpuUsStates;
    String memTotal;
    String memUsed;
    String memFree;
    String memStates;
    String filessystemTotal;
    String filesSystemStates;
    String filesSystem_Free;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cpuUsStates", cpuUsStates)
                .add("memTotal", memTotal)
                .add("memUsed", memUsed)
                .add("memFree", memFree)
                .add("memStates", memStates)
                .add("filessystemTotal", filessystemTotal)
                .add("filesSystemStates", filesSystemStates)
                .add("filesSystem_Free", filesSystem_Free)
                .toString();
    }
}

/**
 * 最终处理结果模板为
 * =============================================================================
 * CPU 用户使用占有率: 10.9%
 * 内存使用情况:  66100704k 总计, 64378928k 已使用,  1721776k 空闲,    91316k 缓存
 * 系统磁盘状态:大小 474G , 已使用327G ,空闲147G
 * =============================================================================
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 * <p>
 * 返回原始数据
 * <p>
 * ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 * top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 * Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 * Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 * Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 * Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached
 * <p>
 * PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 * 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java
 * <p>
 * <p>
 * Filesystem            Size  Used Avail Use% Mounted on
 * /dev/sda3             442G  327G   93G  78% /
 * tmpfs                  32G     0   32G   0% /dev/shm
 * /dev/sda1             788M   60M  689M   8% /boot
 * /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 * <p>
 * 字段说明
 * <p>
 * 统计信息区
 * 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 * ：
 * 01:06:48           当前时间
 * up 1:22            系统运行时间，格式为时:分
 * 1 user             当前登录用户数
 * load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 * 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。
 * <p>
 * 第二、三行为进程和CPU的信息。
 * 当有多个CPU时，这些内容可能会超过两行。内容如下：
 * Tasks: 29 total    进程总数
 * 1 running          正在运行的进程数
 * 28 sleeping        睡眠的进程数
 * 0 stopped          停止的进程数
 * 0 zombie           僵尸进程数
 * Cpu(s): 0.3% us    用户空间占用CPU百分比
 * 1.0% sy            内核空间占用CPU百分比
 * 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 * 98.7% id           空闲CPU百分比
 * 0.0% wa            等待输入输出的CPU时间百分比
 * 0.0% hi            CPU服务于硬中断所耗费的时间总额
 * 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time
 * <p>
 * 最后两行为内存信息。内容如下：
 * Mem: 191272k total 物理内存总量
 * 173656k used       使用的物理内存总量
 * 17616k free        空闲内存总量
 * 22052k buffers     用作内核缓存的内存量
 * Swap: 192772k total 交换区总量
 * 0k used            使用的交换区总量
 * 192772k free       空闲交换区总量
 * 123988k cached     缓冲的交换区总量。
 * 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 * 该数值即为这些内容已存在于内存中的交换区的大小。
 * 相应的内存再次被换出时可不必再对交换区写入。
 * <p>
 * <p>
 * <p>
 * 进程信息区
 * 统计信息区域的下方显示了各个进程的详细信息。
 * 序号 列名 含义
 * a PID      进程id
 * b PPID     父进程id
 * c RUSER Real user name
 * d UID      进程所有者的用户id
 * e USER     进程所有者的用户名
 * f GROUP    进程所有者的组名
 * g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 * h PR       优先级
 * i NI nice值。负值表示高优先级，正值表示低优先级
 * j P        最后使用的CPU，仅在多CPU环境下有意义
 * k %CPU     上次更新到现在的CPU时间占用百分比
 * l TIME     进程使用的CPU时间总计，单位秒
 * m TIME+    进程使用的CPU时间总计，单位1/100秒
 * n %MEM     进程使用的物理内存百分比
 * o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 * p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 * q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 * r CODE     可执行代码占用的物理内存大小，单位kb
 * s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 * t SHR      共享内存大小，单位kb
 * u nFLT     页面错误次数
 * v nDRT     最后一次写入到现在，被修改过的页面数。
 * w S        进程状态。
 * D=不可中断的睡眠状态
 * R=运行
 * S=睡眠
 * T=跟踪/停止
 * Z=僵尸进程
 * x COMMAND 命令名/命令行
 * y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 */


/**
 * 返回原始数据
 *
 ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
 top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
 Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
 Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
 Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
 Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached

 PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
 13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java


 Filesystem            Size  Used Avail Use% Mounted on
 /dev/sda3             442G  327G   93G  78% /
 tmpfs                  32G     0   32G   0% /dev/shm
 /dev/sda1             788M   60M  689M   8% /boot
 /dev/md0              1.9T  483G  1.4T  26% /ezsonar
 */

/**
 * 字段说明
 *
 * 统计信息区
 前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
 ：
 01:06:48           当前时间
 up 1:22            系统运行时间，格式为时:分
 1 user             当前登录用户数
 load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
 三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。

 第二、三行为进程和CPU的信息。
 当有多个CPU时，这些内容可能会超过两行。内容如下：
 Tasks: 29 total    进程总数
 1 running          正在运行的进程数
 28 sleeping        睡眠的进程数
 0 stopped          停止的进程数
 0 zombie           僵尸进程数
 Cpu(s): 0.3% us    用户空间占用CPU百分比
 1.0% sy            内核空间占用CPU百分比
 0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
 98.7% id           空闲CPU百分比
 0.0% wa            等待输入输出的CPU时间百分比
 0.0% hi            CPU服务于硬中断所耗费的时间总额
 0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time

 最后两行为内存信息。内容如下：
 Mem: 191272k total 物理内存总量
 173656k used       使用的物理内存总量
 17616k free        空闲内存总量
 22052k buffers     用作内核缓存的内存量
 Swap: 192772k total 交换区总量
 0k used            使用的交换区总量
 192772k free       空闲交换区总量
 123988k cached     缓冲的交换区总量。
 内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
 该数值即为这些内容已存在于内存中的交换区的大小。
 相应的内存再次被换出时可不必再对交换区写入。



 进程信息区
 统计信息区域的下方显示了各个进程的详细信息。
 序号 列名 含义
 a PID      进程id
 b PPID     父进程id
 c RUSER Real user name
 d UID      进程所有者的用户id
 e USER     进程所有者的用户名
 f GROUP    进程所有者的组名
 g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
 h PR       优先级
 i NI nice值。负值表示高优先级，正值表示低优先级
 j P        最后使用的CPU，仅在多CPU环境下有意义
 k %CPU     上次更新到现在的CPU时间占用百分比
 l TIME     进程使用的CPU时间总计，单位秒
 m TIME+    进程使用的CPU时间总计，单位1/100秒
 n %MEM     进程使用的物理内存百分比
 o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
 p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
 q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
 r CODE     可执行代码占用的物理内存大小，单位kb
 s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
 t SHR      共享内存大小，单位kb
 u nFLT     页面错误次数
 v nDRT     最后一次写入到现在，被修改过的页面数。
 w S        进程状态。
 D=不可中断的睡眠状态
 R=运行
 S=睡眠
 T=跟踪/停止
 Z=僵尸进程
 x COMMAND 命令名/命令行
 y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
 */
