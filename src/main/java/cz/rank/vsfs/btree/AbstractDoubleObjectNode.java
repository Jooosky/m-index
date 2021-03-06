/*
 * Copyright © 2012 Karel Rank All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *  Neither the name of Karel Rank nor the names of its contributors may be used to
 *   endorse or promote products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package cz.rank.vsfs.btree;

/**
 * @author Karel Rank
 */
abstract class AbstractDoubleObjectNode<V> implements DoubleObjectNode<V> {
    private final int degree;

    public AbstractDoubleObjectNode(int degree) {
        this.degree = degree;

        doCheckDegree();
    }

    /**
     * @throws IllegalArgumentException if {@code degree} is less than 2
     */
    private void doCheckDegree() {
        if (degree < 2) {
            throw new IllegalArgumentException("Node degree must be at least 2. Current degree: " + degree);
        }
    }

    /**
     * @return
     */
    @Override
    public int getDegree() {
        return degree;
    }

    /**
     * @return
     */
    protected int maxChildren() {
        return maxKeys() + 1;
    }

    /**
     * @return
     */
    protected int maxKeys() {
        return 2 * degree - 1;
    }

    /**
     * @return
     */
    @Override
    public boolean isFull() {
        return getKeysCount() == maxKeys();
    }

    /**
     * Converts result from {@link java.util.Collections#binarySearch(java.util.List, Object)} to correct positive position
     *
     * @param pos
     * @return {@code pos} if {@code pos >= 0}; otherwise {@code -pos - 1}
     */
    protected int fixBinPos(int pos) {
        return pos < 0 ? -pos - 1 : pos;
    }
}
