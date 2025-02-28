/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;

public final class ByteArrayModificationFactory {

    /**
     * @param xor bytes to xor
     * @param startPosition negative numbers mean that the position is taken from the end
     * @return variable modification
     */
    public static VariableModification<byte[]> xor(byte[] xor, int startPosition) {
        return new ByteArrayXorModification(xor, startPosition);
    }

    /**
     * *
     *
     * @param bytesToInsert bytes to insert
     * @param startPosition negative numbers mean that the position is taken from the end
     * @return variable modification
     */
    public static VariableModification<byte[]> insertValue(
            byte[] bytesToInsert, int startPosition) {
        return new ByteArrayInsertValueModification(bytesToInsert, startPosition);
    }

    public static VariableModification<byte[]> appendValue(byte[] bytesToAppend) {
        return new ByteArrayAppendValueModification(bytesToAppend);
    }

    public static VariableModification<byte[]> prependValue(byte[] bytesToPrepend) {
        return new ByteArrayPrependValueModification(bytesToPrepend);
    }

    /**
     * * Deletes $count bytes from the input array beginning at $startPosition
     *
     * @param startPosition negative numbers mean that the position is taken from the end
     * @param count number of bytes to be deleted
     * @return variable modification
     */
    public static VariableModification<byte[]> delete(int startPosition, int count) {
        return new ByteArrayDeleteModification(startPosition, count);
    }

    /**
     * Duplicates the byte array
     *
     * @return duplicate variable modification
     */
    public static VariableModification<byte[]> duplicate() {
        return new ByteArrayDuplicateModification();
    }

    public static VariableModification<byte[]> explicitValue(byte[] explicitValue) {
        return new ByteArrayExplicitValueModification(explicitValue);
    }

    /**
     * Shuffles the bytes in the array, given a specified array of positions.
     *
     * @param shuffle positions that define shuffling
     * @return shuffling variable modification
     */
    public static VariableModification<byte[]> shuffle(byte[] shuffle) {
        return new ByteArrayShuffleModification(shuffle);
    }

    private ByteArrayModificationFactory() {
        super();
    }
}
