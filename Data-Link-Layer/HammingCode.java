package Data-Link-Layer;

/**
 * Number the bits starting from 1: bit 1, 2, 3, 4, 5, 6, 7 etc.
 * Write the numbers in binary : 1, 10, 11, 100, 101, 110, 111 etc.
 * All bit positions that are powers of 2 are parity bits: 1, 2, 4 etc. (1, 10, 100 etc.)
 * All other bit positions are data bits : 3, 5, 6, 7 etc. (11, 101, 110, 111 etc.)
 * The setup is as follows : _, _, 1, _, 2, 3, 4, _, 5, 6, 7, 8, 9, 10, 11, _, 12 etc.
 * Parity bit 1 covers the bit positions : 1, 3, 5, 7
 * Parity bit 2 covers the bit positions : 2, 3, 6, 7
 * Parity bit 4 covers the bit positions : 4, 5, 6, 7
 *
 * As a rule of thumb, every parity bit covers all the bits
 * where (parity position) && (bit position) != 0
 */
class HammingCode {
    /**
     * Calculates the hamming code of the given bit sequence.
     *
     * @param bitSequence  The input bit sequence
     * @param inputLength  The length of the input bit sequence (including possible leading zeros)
     * @param isEvenParity Boolean indicating if the hamming algorithm should use even parity or not
     * @return The Hamming code sequence
     */
    public static long calcHamming(long bitSequence, int inputLength, boolean isEvenParity) {
        int parityBits = 0;
        while (inputLength + parityBits + 1 > Math.pow(2, parityBits)) {
            parityBits++;
        }

        long initialBitSequence = bitSequence;
        int initialInputLength = inputLength;
        bitSequence &= 0;
        int counter = 0;
        int parityPos = 0;

        for (int i = 1; i <= inputLength; i++) {
            bitSequence = bitSequence << 1;
            if (i == Math.pow(2, parityPos)) {
                inputLength++;
                parityPos++;
            } else {
                long mask = initialBitSequence >> (initialInputLength - 1) - counter;
                bitSequence = bitSequence | (mask & 1);
                counter++;
            }
        }

        for (int i = 0; i < parityBits; i++) {
            int parityBit = (int) Math.pow(2, i);
            int numberOfOnes = 0;

            for (int j = parityBit; j <= inputLength; j++) {
                if ((j & parityBit) != 0) {
                    long mask = bitSequence >> inputLength - j;
                    if ((mask & 1) == 1L) {
                        numberOfOnes++;
                    }
                }
            }
            if (isEvenParity) {
                if (numberOfOnes % 2 != 0) {
                    bitSequence = bitSequence | (1L << (inputLength - (int) Math.pow(2, i)));
                }
            } else {
                if (numberOfOnes % 2 == 0) {
                    bitSequence = bitSequence | (1L << (inputLength - (int) Math.pow(2, i)));
                }
            }
        }


        return bitSequence;
    }

    /**
     * Returns the corrected (if needed) hamming code of the given bit sequence.
     *
     * @param bitSequence  The Hamming code bit sequence
     * @param inputLength  The length of the input bit sequence (including possible leading zeros)
     * @param isEvenParity Boolean indicating if the hamming algorithm should use even parity or not
     * @return The correct Hamming code sequence
     */
    public static long checkHamming(long bitSequence, int inputLength, boolean isEvenParity) {
        int parityBits = 0;
        for (int i = 0; i < inputLength; i++) {
            if (i == Math.pow(2, parityBits)) {
                parityBits++;
            }
        }

        int countErrors = 0;
        for (int i = 0; i < parityBits; i++) {
            int parityBit = (int) Math.pow(2, i);
            int numberOfOnes = 0;

            for (int j = parityBit; j <= inputLength; j++) {
                if ((j & parityBit) != 0) {
                    long temp = bitSequence >> inputLength - j;
                    if ((temp & 1) == 1) {
                        numberOfOnes++;
                    }
                }
            }
            if (isEvenParity) {
                if (numberOfOnes % 2 != 0) {
                    countErrors += Math.pow(2, i);
                }
            } else {
                if (numberOfOnes % 2 == 0) {
                    countErrors += Math.pow(2, i);
                }
            }
        }

        if (countErrors != 0) {
            bitSequence = bitSequence ^ (1L << (inputLength - countErrors));
            return bitSequence;
        }
        return bitSequence;
    }
}
