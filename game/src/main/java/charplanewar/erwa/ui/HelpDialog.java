/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package charplanewar.erwa.ui;

import charplanewar.erwa.config.Config;
import charplanewar.erwa.util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HelpDialog extends JFrame {

    private static final long serialVersionUID = 1L;

    public HelpDialog() {
        this.initComponent();
    }

    private void initComponent() {
        JTextPane helpContentTextPane = new JTextPane();
        helpContentTextPane.setEditable(false);
        helpContentTextPane.setContentType("text/html;charset=utf-8");
        try {
            helpContentTextPane.setText(FileUtil.readFileToString(Config.HELP_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(helpContentTextPane);
        scrollPane.setAutoscrolls(true);

        Container c = this.getContentPane();
        c.add(scrollPane, BorderLayout.CENTER);

        this.setTitle("Help");
        this.setIconImage(new ImageIcon(Config.LOGO_IMG).getImage());
        this.setSize(Config.HELP_DIALOG_WIDTH, Config.HELP_DIALOG_HEIGHT);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

}