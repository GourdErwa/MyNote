package com.groud.erwa.shell.exit;


import java.io.IOException;

/**
 * 模拟调用 shell 获取返回值
 *
 * @author wei.Li by 2018/10/10
 */
public class AnalogInvokeShell {

    public static void main(String[] args) throws IOException {

        final Process process = new ProcessBuilder()
                .command("/bin/sh", "-c", "sh /lw/workfile/intellij_work/MyNote/shell/src/main/java/com/groud/erwa/shell/exit/shell-invalid.sh")
                .inheritIO()
                .start();

        int exitCode;
        try {
            exitCode = process.waitFor();
        } catch (final InterruptedException e) {
            e.printStackTrace();
            exitCode = 1;
        }

        System.out.println(AnalogInvokeShell.class.getName() + " exitCode:" + exitCode);
    }
}
