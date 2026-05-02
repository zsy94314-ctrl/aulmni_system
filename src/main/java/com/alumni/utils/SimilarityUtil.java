package com.alumni.utils;

import com.alumni.entity.Alumni;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SimilarityUtil {
    public double calculateSimilarity(Alumni a, Alumni b) {
        double nameSim = nameSimilarity(a.getName(), b.getName());
        double phoneSim = exactSimilarity(a.getPhone(), b.getPhone());
        double classSim = exactSimilarity(
                a.getClassId() != null ? a.getClassId().toString() : "",
                b.getClassId() != null ? b.getClassId().toString() : "");
        double studentSim = exactSimilarity(a.getStudentNo(), b.getStudentNo());
        return 0.3 * nameSim + 0.3 * phoneSim + 0.2 * classSim + 0.2 * studentSim;
    }

    public double nameSimilarity(String s1, String s2) {
        if (s1 == null || s2 == null) return 0;
        double editSim = levenshteinSimilarity(s1, s2);
        double pinyinSim = pinyinSimilarity(s1, s2);
        return Math.max(editSim, pinyinSim * 0.95);
    }

    public double exactSimilarity(String s1, String s2) {
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) return 0;
        return s1.equals(s2) ? 1.0 : 0;
    }

    public double levenshteinSimilarity(String s1, String s2) {
        int maxLen = Math.max(s1.length(), s2.length());
        if (maxLen == 0) return 1.0;
        int distance = StringUtils.getLevenshteinDistance(s1, s2);
        return 1.0 - (double) distance / maxLen;
    }

    public double pinyinSimilarity(String s1, String s2) {
        try {
            String p1 = toPinyin(s1);
            String p2 = toPinyin(s2);
            return levenshteinSimilarity(p1, p2);
        } catch (Exception e) {
            return 0;
        }
    }

    private String toPinyin(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            String[] arr = PinyinHelper.toHanyuPinyinStringArray(c);
            sb.append(arr != null && arr.length > 0 ? arr[0] : c);
        }
        return sb.toString();
    }
}
