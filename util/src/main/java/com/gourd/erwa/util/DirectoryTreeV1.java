package com.gourd.erwa.util;

import com.gourd.erwa.util.annotation.NotNull;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

/**
 * 目录树生成工具.
 *
 * @author wei.Li
 * @version 1
 */
public class DirectoryTreeV1 {

    /* 换行符*/
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    /* 空字符串*/
    private static final String EMPTY = "";
    /* 文件连接符*/
    private static final String VERTICAL = "│  ", INTERMEDIATE = "├──", END = "└──";
    /* 目录间距*/
    private static final String SPACING = "  ";
    /* 结果集收集*/
    private final StringBuilder r = new StringBuilder();
    /* 默认查询文件目录深度,默认为Integer.MAX_VALUE */
    private int deep = Integer.MAX_VALUE;
    /* 待生成文件目录*/
    private File generateFile;
    /* 文件筛选过滤器*/
    private FileFilter fileFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return true;
        }
    };
    /* 写出文件名及其他信息，默认只输出文件名称*/
    private AppendTo displayContent = new AppendTo(new AppendContent() {
        @Override
        public String appendContent(File file) {
            return " " + file.getName();
        }
    });

    private DirectoryTreeV1(File generateFile) {
        this.generateFile = generateFile;
    }

    private DirectoryTreeV1(DirectoryTreeV1 directoryTreeV1) {
        this.generateFile = directoryTreeV1.generateFile;
        this.fileFilter = directoryTreeV1.fileFilter;
    }

    /**
     * Create tree tree.
     *
     * @param generateFile the generate file
     * @return the tree
     */
    public static DirectoryTreeV1 createTree(File generateFile) {
        return new DirectoryTreeV1(generateFile);
    }

    /**
     * 目录读取子文件.
     *
     * @param file the file
     * @return the list
     */
    @NotNull
    List<File> fetchFiles(File file) {
        final File[] files = file.listFiles(this.fileFilter);
        return files != null ? Arrays.asList(files) : Collections.emptyList();
    }

    /**
     * 自定义文件筛选过滤器.
     *
     * @param fileFilter the file filter
     * @return the file filter
     */
    public DirectoryTreeV1 setFileFilter(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
        return this;
    }

    /**
     * 设置显示文件目录深度.
     *
     * @param deep the deep
     * @return the deep
     */
    public DirectoryTreeV1 setDeep(int deep) {
        this.deep = deep;
        return this;
    }

    /**
     * 自定义排序显示结果 tree.
     *
     * @param comparable the comparable
     * @return the tree
     */
    public DirectoryTreeV1 sort(final Comparator<File> comparable) {
        return new DirectoryTreeV1(this) {
            @Override
            List<File> fetchFiles(File file) {
                final List<File> files = super.fetchFiles(file);
                Collections.sort(files, comparable);
                return super.fetchFiles(file);
            }
        };
    }

    /**
     * 显示文件大小.
     *
     * @return the tree
     */
    public DirectoryTreeV1 showLength() {
        this.displayContent.add(new AppendContent() {
            @Override
            public String appendContent(File file) {
                return "["
                        + file.length() + "B"
                        + "]";
            }
        });
        return this;
    }

    /**
     * 显示文件修改时间.
     *
     * @return the tree
     */
    public DirectoryTreeV1 showModify() {
        this.displayContent.add(new AppendContent() {
            @Override
            public String appendContent(File file) {
                return "["
                        + new Date(file.lastModified())
                        + "]";
            }
        });
        return this;
    }

    /**
     * 显示文件权限.
     *
     * @return the tree
     */
    public DirectoryTreeV1 showPermission() {
        this.displayContent.add(new AppendContent() {
            @Override
            public String appendContent(File file) {
                return "["
                        + (file.canRead() ? "r-" : "--")
                        + (file.canWrite() ? "w-" : "--")
                        + (file.canExecute() ? "x-" : "--")
                        + "]";
            }
        });
        return this;
    }

    /**
     * 自定义添加读取 file 解析内容到输出内容.
     *
     * @param appendContent the append content
     * @return the tree
     */
    public DirectoryTreeV1 addAppendContent(AppendContent appendContent) {
        this.displayContent.add(appendContent);
        return this;
    }

    /**
     * 生成文件.
     *
     * @return 结果内容
     */
    public final String generate() {
        if (this.generateFile.exists()) {
            this.generateHandle(this.generateFile, EMPTY, 0);
        }

        return this.r.toString();
    }

    private void generateHandle(File file, String prefix, int deep) {
        final List<File> files = this.fetchFiles(file);
        if (files.isEmpty()) {
            return;
        }
        deep++;
        final int length = files.size();
        for (int i = 0; i < length; i++) {
            final File f = files.get(i);

            final boolean isLast = (i >= length - 1);
            this.r.append(prefix).append(isLast ? END : INTERMEDIATE);
            this.appendDisplayContent(f);
            this.r.append(LINE_SEPARATOR);

            if (f.isDirectory() && deep <= this.deep) {
                this.generateHandle(f, prefix + (!(length <= 1 || isLast) ? VERTICAL : EMPTY) + SPACING, deep);
            }
        }
    }

    /**
     * 处理定义文件内容
     *
     * @param f f
     */
    private void appendDisplayContent(File f) {
        final List<AppendContent> appendContents = displayContent.appendContents;
        for (AppendContent to : appendContents) {
            this.r.append(to.appendContent(f));
        }
    }

    /**
     * 读取 file 解析待内容.
     */
    public interface AppendContent {

        String appendContent(File file);
    }

    /**
     * 可累积显示 tree 中具体文件属性内容
     */
    private static class AppendTo {

        private final List<AppendContent> appendContents = new ArrayList<>();

        AppendTo(AppendContent appendTo) {
            if (appendTo != null) {
                this.appendContents.add(appendTo);
            }
        }

        void add(AppendContent to) {
            if (to != null) {
                this.appendContents.add(0, to);
            }
        }
    }
}

/**
 * The type Main.
 */
class MainTest {

    public static void main(String[] args) {
        final String generate = DirectoryTreeV1.createTree(new File("/lw/workfile/intellij_work/MyNote/concurrent"))
                .setDeep(20)
                .setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return !pathname.isHidden();
                    }
                })
                .showLength()
                .showModify()
                .showPermission()
                .addAppendContent(new DirectoryTreeV1.AppendContent() {
                    @Override
                    public String appendContent(File file) {
                        return "[" + file.getPath() + "]";
                    }
                })
                .generate();
        System.out.println(generate);
    }

}
