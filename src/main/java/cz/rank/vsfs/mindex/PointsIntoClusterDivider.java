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

package cz.rank.vsfs.mindex;

import java.util.Map;
import java.util.Set;

public class PointsIntoClusterDivider<D extends Distanceable<D>> {
    private final Map<Pivot<D>, Cluster<D>> clusters;
    private final Set<Pivot<D>> pivots;
    private final Set<D> points;

    public PointsIntoClusterDivider(Map<Pivot<D>, Cluster<D>> clusters, Set<Pivot<D>> pivots, Set<D> points) {
        this.clusters = clusters;
        this.pivots = pivots;
        this.points = points;
    }

    public void divide() {
        VoronoiQuickDivider<D> divider = new VoronoiQuickDivider<D>(pivots, points);
        Map<D, Pivot<D>> nearestPivots = divider.calculate();

        assignObjectsToClusters(nearestPivots);

        normalizeClusters();
    }

    private void assignObjectsToClusters(Map<D, Pivot<D>> nearestPivots) {
        for (Map.Entry<D, Pivot<D>> entry : nearestPivots.entrySet()) {
            final Cluster<D> cluster = clusters.get(entry.getValue());

            cluster.add(entry.getKey());
        }
    }

    private void normalizeClusters() {
        for (Cluster<D> cluster : clusters.values()) {
            cluster.normalizeDistances();
        }
    }
}