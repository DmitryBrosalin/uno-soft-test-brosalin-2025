package com.uno;

import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    private final List<Integer> roots;
    private final List<Integer> ranks;

    public UnionFind() {
        this.roots = new ArrayList<>();
        this.ranks = new ArrayList<>();
    }

    public void addNewElement(int index) {
        roots.add(index);
        ranks.add(0);
    }

    public int findRoot(int index) {
        if (roots.get(index) != index) {
            roots.set(index, findRoot(roots.get(index)));
        }
        return roots.get(index);
    }

    public void union(int index1, int index2) {
        index1 = findRoot(index1);
        index2 = findRoot(index2);

        if (index1 == index2) return;

        int rank1 = ranks.get(index1);
        int rank2 = ranks.get(index2);
        if (rank1 > rank2) {
            roots.set(index2, index1);
        } else if (rank1 < rank2) {
            roots.set(index1, index2);
        } else {
            roots.set(index2, index1);
            ranks.set(index1, rank1 + 1);
        }
    }
}
