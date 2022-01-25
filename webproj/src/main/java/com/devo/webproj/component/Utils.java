package com.devo.webproj.component;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class Utils {
    private static BigDecimal defaultDecimal = new BigDecimal("0.00");
    private static BigDecimal defaultDecimalError = new BigDecimal("-1.00");
    private static BigDecimal persentageMultiple = new BigDecimal("100");

    public static  BigDecimal getDefaultDecimal(){
        return defaultDecimal;
    }
    public static  BigDecimal getDefaultDecimalError(){
        return defaultDecimalError;
    }
    public static  BigDecimal getPersentageMultiple(){
        return persentageMultiple;
    }


    public static boolean isImage(String fileName) {
        List<String> imageExtensions = new ArrayList<>();
        imageExtensions.add("png");
        imageExtensions.add("jpg");
        imageExtensions.add("gif");
        imageExtensions.add("bmp");
        imageExtensions.add("jpeg");
        imageExtensions.add("svg");

        return imageExtensions.contains(getFileExtName(fileName).toLowerCase());
    }

    public static String getIssuePriorityCode(int priority) {
        if (priority == 10) return "TOP";
        if (priority == 20) return "HIGH";
        if (priority == 30) return "MIDDLE";
        return "LOW";
    }

    public static String getRandomString(int len) {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int idx = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            idx = (int) (charSet.length * Math.random());
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }

    public static List<String> getStringList(String separatedString, String separator) {
        return new ArrayList(Arrays.asList(separatedString.split(separator)));
    }

    public static List<Long> getLongList(String separatedString, String separator) {

        List<Long> longs = new ArrayList<>();
        if(separatedString.isBlank()) return longs;
        for (String string : getStringList(separatedString, separator)) {
            longs.add(Long.parseLong(string));
        }
        return longs;
    }

    public static String getJsonString(List<String> strings) {
        int idx = 0;
        String jsonString = "";
        for (String value : strings) {
            if (idx == 0) jsonString += getNameValueString(idx,value);
            else jsonString += "," + getNameValueString(idx,value);
            ++idx;
        }

        return "{" + jsonString + "}";
    }

    public static String getNameValueString(int name, String value)
    {
        return "\""+name+"\":\""+value+"\"";
    }

    public static String getBaseFolderName(String basePathName) {
        return basePathName.substring(basePathName.lastIndexOf("/") + 1);
    }

    public static String getFileNameWithOutExt(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf(".")).replace(" ", "_");
    }

    public static String getFileExtName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String getFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf("/") + 1);
    }

    public static String getRequestMappingValue(String requestURI) {
        return requestURI.substring(requestURI.lastIndexOf("/") + 1);
    }

    @NotNull
    public static String getSaveFileNameDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(new Date());
    }

    public static String getSaveBasePath(String winPathUpload, String linuxPathUpload, String macPathUpload) {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("win")) return winPathUpload;
        if (osName.toLowerCase().contains("mac")) return macPathUpload;
        return linuxPathUpload;
    }

    public static String getDownloadBasePath(String winPathUpload, String linuxPathUpload, String macPathUpload) {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("win")) return winPathUpload.substring(0, winPathUpload.lastIndexOf("/"));
        if (osName.toLowerCase().contains("mac")) return macPathUpload.substring(0, macPathUpload.lastIndexOf("/"));
        return linuxPathUpload.substring(0, linuxPathUpload.lastIndexOf("/"));
    }

    public static String getPhysicalFilePath(String winPathUpload, String linuxPathUpload, String macPathUpload, String filePathAtDB){
        return getSaveBasePath(winPathUpload, linuxPathUpload, macPathUpload) + filePathAtDB.replace("/file_upload", "");
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static LocalDateTime parseLocalDateTimeEnd(String date) {
        if (date.isBlank()) return null;
        return LocalDateTime.parse(date.replace(".","-") + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static LocalDateTime parseLocalDateTimeStart(String date) {
        if (date.isBlank()) return null;
        return LocalDateTime.parse(date.replace(".","-") + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static BigDecimal getFileSizeMegaByteUnit(BigDecimal fileSize){
        BigDecimal denominator = new BigDecimal("1000000");
        return fileSize.divide(denominator, 2, RoundingMode.HALF_EVEN);
    }

    public static String todayString()
    {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String todayPlusDayString(long day)
    {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String todayMinusDayString(long day)
    {
        return LocalDate.now().minusDays(day).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String isEmpty(String value, String defaultValue)
    {
        if(StringUtils.isEmpty(value)) return defaultValue;
        return value;
    }

    public static BigDecimal isEmpty(BigDecimal value)
    {
        if(value == null) return defaultDecimal;
        return value;
    }

    public static int toPercentage(BigDecimal value)
    {
        if(value == null) return 0;
        return value.multiply(persentageMultiple).intValue();
    }

    public static String convertFirstCharacterToUpperCase(String text)
    {
        if(text.isBlank()) return text;
        return (text.charAt(0)+"").toUpperCase(Locale.ROOT) + text.substring(1);
    }
}
