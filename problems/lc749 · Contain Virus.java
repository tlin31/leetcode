LC 749 · Contain Virus 🔴 Bonus Hard — Graph Simulation


📋 Problem

A virus is spreading on a grid. Each cell is either:

0 — uninfected
1 — infected

Each day:

You install walls to completely contain the single most threatening region (the infected region that would infect the most uninfected cells the next day)
All other uncontained infected regions spread to their adjacent uninfected neighbors

Return the total number of walls used to contain the virus.

Input: grid = [[0,1,0,0,0,0,0,1],
                [0,1,0,0,0,0,0,1],
                [0,0,0,0,0,0,0,1],
                [0,0,0,0,0,0,0,0]]
Output: 10

Input: grid = [[1,1,1],
               [1,0,1],
               [1,1,1]]
Output: 4
1. Pattern ID

BFS/DFS simulation with greedy containment: each day, identify all infected regions via DFS, greedily contain the most threatening one (walls = its perimeter touching uninfected cells), then BFS-spread all remaining regions — repeat until no more spreading possible.

2. Algorithm Discussion

This is a multi-phase daily simulation problem. Each day has three phases:

Phase 1 — Identify all infected regions
DFS/BFS to find all connected components of infected cells (1s). For each region track:

infected — set of cells in this region
frontier — set of uninfected cells adjacent to this region (what it threatens)
walls — number of walls needed to fully contain this region (edges between infected and uninfected cells)

Phase 2 — Contain the most threatening region
The most threatening region = the one with the largest frontier (most uninfected neighbors it would infect). Mark all its cells as -1 (contained, permanently neutralized) and add its wall count to total.

Phase 3 — Spread all other regions
All uncontained regions (1s not marked -1) spread to their frontier cells, turning them to 1.

Repeat until no region can spread further (all frontiers empty).

Day 1:
  Region A: threatens 10 cells → most threatening → contain (walls += perimeter)
  Region B: spreads to its frontier

Day 2:
  Re-identify regions (B has grown)
  Most threatening → contain
  Others spread...

Why greedy containment is correct:
Each day we can only contain ONE region. Containing the most threatening one minimizes total future spread — greedy choice is locally and globally optimal here because contained regions never spread again.

🧠 Pop quiz! — Why do we use a set for frontier instead of a list when counting threatened cells?

Answer: a single uninfected cell can be adjacent to multiple infected cells in the same region — counting it twice would overcount the threat. A set deduplicates automatically, giving the true count of unique uninfected cells threatened. This is critical for correctly identifying the most threatening region! 🎯

3. Edge Cases
No infected cells — return 0 ✅
Entire grid infected — no spreading possible, return 0 ✅
Single infected cell — walls = number of uninfected neighbors ✅
Island of 0s completely surrounded — frontier correctly captured by set ✅
Multiple regions with equal frontier size — break ties arbitrarily, both correct ✅
Grid becomes fully infected before containment — simulation terminates ✅