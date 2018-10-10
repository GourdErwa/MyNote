/**
 * 1.javaMain1程序 调用 shell {@link com.groud.erwa.shell.exit.AnalogInvokeShell}
 * <p>
 * 2.shell 内容部执行 javaMain2程序
 * <p>
 * 3.javaMain1程序 获取 javaMain2程序 执行结果（正常、异常捕获、非正常退出）
 * <p>
 * shell-invalid.sh 不可用写法，无法获取 java 程序返回值
 * shell-valid-1.sh 可用写法
 * shell-valid-2.sh 可用写法
 *
 * @author wei.Li by 2018/10/10
 */
package com.groud.erwa.shell.exit;
