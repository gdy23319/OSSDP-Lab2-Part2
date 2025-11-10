import java.util.*;

/**
 * 测试用例设计总体原则：
 * 1. 等价类划分原则：
 *    - 有效等价类：字符串长度大于10、包含重复DNA序列
 *    - 无效等价类：字符串长度小于10、空字符串、无重复序列
 *    - 边界值分析：长度9、10、11的字符串
 * 2. 功能覆盖原则：
 *    - 正常功能：单个重复序列、多个重复序列
 *    - 边界情况：刚好出现两次的序列、重叠序列
 *    - 异常情况：空字符串、短字符串
 * 3. 数据覆盖原则：
 *    - 覆盖所有DNA字符（A、C、G、T）
 *    - 覆盖不同重复次数（2次、多次）
 *    - 覆盖不同序列位置
 */
public class L2023110905_17_Test {
    
    private Solution17 solution;
    private int testCount = 0;
    private int passedCount = 0;
    
    public void setUp() {
        solution = new Solution17();
    }
    
    public static void main(String[] args) {
        L2023110905_17_Test test = new L2023110905_17_Test();
        test.runAllTests();
    }
    
    public void runAllTests() {
        System.out.println("开始运行 DNA 序列重复检测测试...");
        
        setUp();
        
        testShortStrings();
        testSingleRepeatedSequence();
        testMultipleRepeatedSequences();
        testNoRepeatedSequences();
        testExactlyTwoOccurrences();
        testAllDNACharacters();
        testOverlappingSequences();
        testMinimalValidLength();
        testLongString();
        testMultipleOccurrences();
        testMixedCharacterSequence();
        
        System.out.println("\n测试完成！");
        System.out.println("总计: " + testCount + " 个测试, 通过: " + passedCount + " 个, 失败: " + (testCount - passedCount) + " 个");
    }
    
    private void assertTrue(boolean condition, String message) {
        testCount++;
        if (condition) {
            passedCount++;
            System.out.println("PASS: " + message);
        } else {
            System.out.println("FAIL: " + message);
        }
    }
    
    private void assertEquals(Object expected, Object actual, String message) {
        testCount++;
        if (expected.equals(actual)) {
            passedCount++;
            System.out.println("PASS: " + message);
        } else {
            System.out.println("FAIL: " + message + " (期望: " + expected + ", 实际: " + actual + ")");
        }
    }
    /**
     * 测试目的：验证边界情况处理 - 空字符串和短字符串
     * 测试用例：
     *   - 用例1：空字符串 "" 
     *   - 用例2：长度8的字符串 "ACGTACGT"
     *   - 用例3：长度10的字符串 "ACGTACGTAC"（无重复）
     * 预期结果：所有情况都应返回空列表
     * 测试类型：边界测试、无效等价类测试
     */
    public void testShortStrings() {
        System.out.println("\n=== 测试短字符串边界情况 ===");
        List<String> result1 = solution.findRepeatedDnaSequences("");
        assertTrue(result1.isEmpty(), "空字符串应返回空列表");
        
        List<String> result2 = solution.findRepeatedDnaSequences("ACGTACGT");
        assertTrue(result2.isEmpty(), "长度8的字符串应返回空列表");
        
        List<String> result3 = solution.findRepeatedDnaSequences("ACGTACGTAC");
        assertTrue(result3.isEmpty(), "长度10无重复字符串应返回空列表");
    }
    
/**
     * 测试目的：验证基本功能 - 识别单个重复序列
     * 测试用例：
     *   - 输入："AAAAACCCCCAAAAACCCCCC"
     *   - 重复序列："AAAAACCCCC" 出现2次
     * 预期结果：返回包含 "AAAAACCCCC" 的列表
     * 测试类型：正常功能测试、有效等价类测试
     */
    public void testSingleRepeatedSequence() {
        System.out.println("\n=== 测试单个重复序列 ===");
        String s = "AAAAACCCCCAAAAACCCCCC";
        List<String> result = solution.findRepeatedDnaSequences(s);
        
        List<String> expected = Arrays.asList("AAAAACCCCC");
        assertEquals(expected, result, "应正确识别单个重复序列");
    }
    
    /**
     * 测试目的：验证多个重复序列的识别能力
     * 测试用例：
     *   - 输入："AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
     *   - 重复序列1："AAAAACCCCC" 出现2次
     *   - 重复序列2："CCCCCAAAAA" 出现2次
     * 预期结果：返回包含两个重复序列的列表
     * 测试类型：正常功能测试、多情况覆盖
     */
    public void testMultipleRepeatedSequences() {
        System.out.println("\n=== 测试多个重复序列 ===");
        String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        List<String> result = solution.findRepeatedDnaSequences(s);
        
        assertEquals(2, result.size(), "应识别出2个重复序列");
        assertTrue(result.contains("AAAAACCCCC"), "应包含AAAAACCCCC序列");
        assertTrue(result.contains("CCCCCAAAAA"), "应包含CCCCCAAAAA序列");
    }
    /**
     * 测试目的：验证无重复序列的情况
     * 测试用例：
     *   - 输入："ACGTACAAACGTACGA"（所有10字符序列都唯一）
     * 预期结果：返回空列表
     * 测试类型：正常功能测试、无效等价类测试
     */
    public void testNoRepeatedSequences() {
        System.out.println("\n=== 测试无重复序列 ===");
        String s = "ACGTACAAACGTACGA";
        List<String> result = solution.findRepeatedDnaSequences(s);
        assertTrue(result.isEmpty(), "无重复序列时应返回空列表");
    }
    
    /**
     * 测试目的：验证刚好出现两次的序列识别
     * 测试用例：
     *   - 输入："ACGTTGCAGTACGTTGCAGT"
     *   - 重复序列："ACGTTGCAGT" 刚好出现2次
     * 预期结果：返回包含 "ACGTTGCAGT" 的列表
     * 测试类型：边界测试、计数边界测试
     */
    public void testExactlyTwoOccurrences() {
        System.out.println("\n=== 测试刚好出现两次的序列 ===");
        String s = "ACGTTGCAGTACGTTGCAGT";
        List<String> result = solution.findRepeatedDnaSequences(s);
        assertTrue(result.contains("ACGTTGCAGT"), "应识别刚好出现2次的序列");
    }

    /**
     * 测试目的：验证所有DNA字符的覆盖情况
     * 测试用例：
     *   - 输入："ACGTACGTACACGTACGTAC"（包含A、C、G、T所有字符）
     *   - 重复序列："ACGTACGTAC" 出现2次
     * 预期结果：正确识别重复序列
     * 测试类型：数据覆盖测试、字符全集测试
     */
    public void testAllDNACharacters() {
        System.out.println("\n=== 测试所有DNA字符 ===");
        String s = "ACGTACGTACACGTACGTAC";
        List<String> result = solution.findRepeatedDnaSequences(s);
        List<String> expected = Arrays.asList("ACGTACGTAC");
        assertEquals(expected, result, "应正确处理所有DNA字符");
    }
    
    /**
     * 测试目的：验证重叠序列的处理
     * 测试用例：
     *   - 输入："AAAAAAAAAAAAA"（13个A，产生重叠的重复序列）
     *   - 重复序列："AAAAAAAAAA" 出现4次
     * 预期结果：正确识别重叠的重复序列
     * 测试类型：特殊情况测试、重叠序列测试
     */
    public void testOverlappingSequences() {
        System.out.println("\n=== 测试重叠序列 ===");
        String s = "AAAAAAAAAAAAA";
        List<String> result = solution.findRepeatedDnaSequences(s);
        
        assertEquals(1, result.size(), "应识别出1个重复序列");
        assertEquals("AAAAAAAAAA", result.get(0), "重复序列应为AAAAAAAAAA");
    }
    /**
     * 测试目的：验证边界长度字符串的处理
     * 测试用例：
     *   - 输入："ACGTACGTACG"（长度11，最小有效长度）
     * 预期结果：根据具体重复情况返回相应结果
     * 测试类型：边界测试、最小有效长度测试
     */
    public void testMinimalValidLength() {
        System.out.println("\n=== 测试最小有效长度 ===");
        String s = "ACGTACGTACG";
        List<String> result = solution.findRepeatedDnaSequences(s);
        assertTrue(result.isEmpty(), "长度11无重复时应返回空列表");
    }
    
    /**
     * 测试目的：验证长字符串的性能和正确性
     * 测试用例：
     *   - 输入：由重复模式构成的长字符串（1000个字符）
     * 预期结果：正确识别重复序列，不出现性能问题
     * 测试类型：性能测试、压力测试
     */
    public void testLongString() {
        System.out.println("\n=== 测试长字符串 ===");
        StringBuilder sb = new StringBuilder();
        String pattern = "ACGTACGTAC";
        for (int i = 0; i < 100; i++) {
            sb.append(pattern);
        }
        
        List<String> result = solution.findRepeatedDnaSequences(sb.toString());
        assertTrue(result.contains(pattern), "长字符串中应识别重复模式");
    }

    /**
     * 测试目的：验证序列出现三次以上时只记录一次
     * 测试用例：
     *   - 输入："TTTTTTTTTTTTT"（13个T，序列出现4次）
     * 预期结果：只返回一次该序列
     * 测试类型：重复计数测试
     */
    public void testMultipleOccurrences() {
        System.out.println("\n=== 测试多次出现的序列 ===");
        String s = "TTTTTTTTTTTTT";
        List<String> result = solution.findRepeatedDnaSequences(s);
        
        assertEquals(1, result.size(), "多次出现的序列应该只记录一次");
        assertEquals("TTTTTTTTTT", result.get(0), "重复序列应为TTTTTTTTTT");
    }
    
    /**
     * 测试目的：验证混合字符序列的正确识别
     * 测试用例：
     *   - 输入："ACGTACGTGGACGTACGTGG"（混合字符重复序列）
     * 预期结果：正确识别重复序列
     * 测试类型：混合字符测试
     */
    public void testMixedCharacterSequence() {
        System.out.println("\n=== 测试混合字符序列 ===");
        String s = "ACGTACGTGGACGTACGTGG";
        List<String> result = solution.findRepeatedDnaSequences(s);
        
        List<String> expected = Arrays.asList("ACGTACGTGG");
        assertEquals(expected, result, "应正确识别混合字符重复序列");
    }
}
