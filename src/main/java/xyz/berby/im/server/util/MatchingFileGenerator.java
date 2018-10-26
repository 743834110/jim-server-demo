package xyz.berby.im.server.util;

import xyz.berby.im.server.packet.FilePacket;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/10/20.
 * @author litianfeng
 * 文件名生成器
 * Created on 2018/10/20
 */
public class MatchingFileGenerator {

    private MatchingFileGenerator() {}

    /**
     * 文件名过滤器
     */
    private static class CustomFileNameFilter implements FilenameFilter {

        private Pattern pattern;
        // 获取带有较大序号的文件序号, 默认为0
        private int biggerNumber = 0;

        public CustomFileNameFilter(String regex) {
            pattern = Pattern.compile(regex);
        }

        @Override
        public boolean accept(File dir, String name) {

            Matcher matcher = this.pattern.matcher(name);
            String numString = null;
            if (matcher.find()) {
                if ((numString = matcher.group(2)) != null) {
                    int num = Integer.parseInt(numString);
                    if (this.biggerNumber < num)
                        this.biggerNumber = num;
                }
                return true;
            }
            return false;
        }

        public int getBiggerNumber() {
            return biggerNumber;
        }
    }

    /**
     *
     * @param fileName
     * @param userDir
     * @return
     */
    public static Integer generateNewFileNameString(String fileName, File userDir) {
        if (!userDir.exists()) {
            userDir.mkdirs();
            return null;
        }

        // language=RegExp
        int dashIndex = fileName.indexOf('.');
        String regex = null;
        String prefix = null;
        String suffix = "";
        if (dashIndex == -1) {
            regex = fileName + "(\\((\\d+)\\))?$";
            prefix = fileName;
        }
        else {

            prefix = fileName.substring(0, dashIndex);
            suffix = fileName.substring(dashIndex);
            regex = prefix + "(\\((\\d+)\\))?" + suffix;
        }
        CustomFileNameFilter fileNameFilter = new CustomFileNameFilter(regex);
        String[] userFileNames = userDir.list(fileNameFilter);
        // 匹配到的文件为空，表示独立空间下该文件名不曾存在
        if (userFileNames == null || userFileNames.length == 0)
            return null;

        return fileNameFilter.getBiggerNumber();

    }

    /**
     * 根据需求产生在系统中的独立空间拥有唯一文件名的文件对象
     * 兼顾处理文件分片带来的辨别是否为新文件的问题
     * @param filePacket
     * @param uploadDir
     * @return
     */
    public static File generateNewFile(FilePacket filePacket, File uploadDir) {

        // 用户子目录不存在时，创建文件并返回
        String fileName = filePacket.getName();
        File userDir = new File(uploadDir, filePacket.getSessionId());
        // 文件系统中存在的在同名文件中最大的序号
        Integer number = generateNewFileNameString(fileName, userDir);
        int dashIndex = fileName.indexOf('.');
        // 为新文件中的分片, 因在文件系统中不存在同名文件名, 所以无需添加文件递增序号
        boolean theNewOne = filePacket.isTheNewOne();
        boolean equals = number == null;
        if (theNewOne && equals) {
            return new File(userDir, fileName);
        }
        // 为新文件中的分片, 因在文件系统中存在同名文件名, 所以需添加文件递增序号
        else if (theNewOne) {
            if (dashIndex == -1) {
                fileName = fileName + '(' + (number + 1) + ')';
            }
            else {
                fileName = fileName.substring(0, dashIndex) + '(' + (number + 1) + ')' + fileName.substring(dashIndex);
            }
            return new File(userDir, fileName);
        }
        // 为旧文件分片, 直接取得该文件对象
        else {
            return new File(userDir, fileName);
        }
    }



}
