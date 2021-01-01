package Data-Link-Layer;

class CRC {
    /**
     * Calculates the CRC check value, or -1 if it cannot be calculated.
     *
     * @param bitSequence The input bit sequence
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param generatorSequence The generator bit sequence
     * @return check value
     */
    public static long calculateCRC(long bitSequence, int inputLength, long generatorSequence) {
        // TODO
        if (inputLength == 0) {
            return -1;
        }

        //Find the length of the generator sequence
        int generatorLength = (int) Math.floor(Math.log(generatorSequence)/ Math.log(2)) + 1;

        if (generatorLength > inputLength) {
            return -1;
        }

        long originalGeneratorSeq = generatorSequence;

        //Add the padding to the bit sequence
        int padding = generatorLength - 1;
        bitSequence = bitSequence << (padding);

        //Update the input length
        inputLength = inputLength + padding;

        //Shift the generator sequence to the left
        generatorSequence = generatorSequence << (inputLength - generatorLength);

        /*
         * Change the length, XOR, shift generatorSequence to the right and then loop
         */
        while (generatorLength <= inputLength) {
            bitSequence = bitSequence ^ generatorSequence;
            long newPadding = (int) Math.floor(Math.log(bitSequence)/ Math.log(2)) + 1 - generatorLength;
            generatorSequence = originalGeneratorSeq << (newPadding);
            inputLength = (int) Math.floor(Math.log(bitSequence)/ Math.log(2)) + 1;
        }
        return bitSequence;
    }

    /**
     * Checks the correctness of the bit sequence.
     *
     * @param bitSequence CRC bit sequence
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param generatorSequence The generator bit sequence used
     * @param checkSequence CRC check value to check against
     * @return true if the sequence is correct, false otherwise
     */
    public static boolean checkCRC(
            long bitSequence, int inputLength, long generatorSequence, long checkSequence) {
        // TODO
        long result = calculateCRC(bitSequence, inputLength, generatorSequence);
        return (result == checkSequence);
    }
}

