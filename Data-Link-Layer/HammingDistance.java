package Data-Link-Layer;

import java.util.List;

class HammingDistance {
    /**
     * Calculates the hamming distance of the given code, or returns -1 if it cannot be calculated.
     *
     * @param code The code
     * @return The hamming distance of the given code , or -1 if it cannot be calculated
     */
    public static long calculate(List<Long> code) {
        if (code.size() < 2) {
            return -1;
        }

        int hammingDistance = Integer.MAX_VALUE;
        for (int i = 0; i < code.size(); i++) {
            long x = code.get(i);
            for (int j = i + 1; j < code.size(); j++) {
                long y = code.get(j);
                long res = x ^ y;
                int length = (int) Long.bitCount(res);

                if (length < hammingDistance) {
                    hammingDistance = length;
                }
            }
        }
        return hammingDistance;
    }
}
