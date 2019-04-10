package com.geyl.util;


import java.io.IOException;
import java.io.InputStream;

public class FileTypeUtils {


    /**
     * 根据文件流数据获取文件类型
     * @param in
     * @return
     * @throws IOException
     */
    public static String getFileType(InputStream in){
        try {
            String fileHead = getFileHeader(in);
            if (fileHead != null && fileHead.length() > 0) {
                fileHead = fileHead.toUpperCase();
                FileTypeEnum[] fileTypeEnums = FileTypeEnum.values();
                for (FileTypeEnum type : fileTypeEnums) {
                    if (fileHead.startsWith(type.getValue())) {
                        return type.toString();
                    }
                }
            }
        }catch (IOException e){
            return null;
        }
        return null;
    }


    /**
     * 此处因为避免影响到处理的原文件流,因此读取之后不进行关闭流
     * @param in
     * @return
     * @throws IOException
     */

    private static String getFileHeader(InputStream in) throws IOException {
        byte[] b = new byte[28];

        try {
            in.read(b, 0, 28);
        } catch (Exception e){
            return null;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return bytesToHex(b);
    }

    /** 将字节数组转换成16进制字符串 */
    public static String bytesToHex(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static enum FileTypeEnum {
        /** JPEG */
        jpeg("FFD8FF"),
        /** PNG */
        png("89504E47"),
        /** GIF */
        gif("47494638"),
        /** TIFF */
        tiff("49492A00"),
        /** Windows bitmap */
        bmp("424D"),
        /** CAD */
        dwg("41433130"),
        /** Adobe photoshop */
        psd("38425053"),
        /** Rich Text Format */
        rtf("7B5C727466"),
        /** XML */
        xml("3C3F786D6C"),
        /** HTML */
        html("68746D6C3E"),
        /** Outlook Express */
        dbx("CFAD12FEC5FD746F "),
        /** Outlook */
        pst("2142444E"),
        /** doc;xls;dot;ppt;xla;ppa;pps;pot;msi;sdw;db */
        ole2("0xD0CF11E0A1B11AE1"),
        /** Microsoft Word/Excel */
        xls_doc("D0CF11E0"),
        /** Microsoft Access */
        mdb("5374616E64617264204A"),
        /** Word Perfect */
        wpb("FF575043"),
        /** Postscript */
        eps_ps("252150532D41646F6265"),
        /** Adobe Acrobat */
        pdf("255044462D312E"),
        /** Windows Password */
        pwl("E3828596"),
        /** ZIP Archive */
        zip("504B0304"),
        /** ARAR Archive */
        rar("52617221"),
        /** WAVE */
        wav("57415645"),
        /** AVI */
        avi("41564920"),
        /** Real Audio */
        ram("2E7261FD"),
        /** Real Media */
        rm("2E524D46"),
        /** Quicktime */
        mov("6D6F6F76"),
        /** Windows Media */
        asf("3026B2758E66CF11"),
        /** MIDI */
        mid("4D546864");

        private String value = "";

        private FileTypeEnum(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
}
