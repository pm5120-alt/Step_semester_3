# Week 9 — Quiz and Concept Answers

## Part B — Quiz

1. **B** — Two pointers make one pass through the sorted array: O(n) time and O(1) extra space.
2. **C — Traversal.** Traversal systematically visits each element.
3. **C — O(1).** A range sum is obtained by subtracting two prefix sums.
4. **C.** Prioritize speed when users need immediate responses from a frequently used feature and the extra memory is acceptable.
5. **C.** Inserting or deleting at an arbitrary array position can require shifting O(n) elements.
6. **C — Hash-based structure.** Hash tables typically offer O(1) average-case key lookup, insertion, and deletion.
7. **C.** Binary search requires the collection to be sorted according to the searched key.
8. **A, B, C, E.** Ignore constant factors; keep the dominant growth term; add sequential complexities; multiply nested-loop complexities over the same input size. Do not assume best-case complexity by default.
9. **A, B, C, D.** A fixed-size sliding window tracks a contiguous range; each element enters and leaves at most once, allowing O(n) time instead of repeated O(nk) or O(n²) work. It is not limited to non-linear structures.
10. **C — O(n).** Elements may need to shift when inserting at the beginning or middle of an array.

## Part C — Concept Questions

### 1. Big-O and constants
Algorithm A performs 3n + 10 steps, so its growth is O(n). Algorithm B performs n² + 2 steps, so its growth is O(n²). For small inputs, B may run faster because constants and fixed overhead matter. As n grows, the quadratic term eventually dominates the linear term, so A scales better. Big-O describes asymptotic growth and ignores constant factors and lower-order terms.

### 2. Primitive vs non-primitive data structures
Primitive types (such as int, char, and boolean) represent single basic values. Non-primitive structures (such as arrays, linked lists, trees, and hash maps) organize collections of values and/or relationships between them. A student directory keyed by student ID is a useful example: a map associates each ID with a complete student record, which is more suitable than unrelated primitive variables.

### 3. Binary search and sorting
Binary search relies on sorted order: after checking the middle element, it can safely discard the half that cannot contain the target. In an unsorted array, that elimination is not valid. Sorting first typically costs O(n log n), followed by O(log n) per binary search. For one query, a linear scan at O(n) is often cheaper; sorting becomes worthwhile when many searches reuse the same data.

## Complexity reference for Week 9 coding problems

| Problem | Optimized time | Extra space |
| --- | --- | --- |
| Pair sum in sorted array | O(n) | O(1) |
| Warehouse grid summary | O(mn) | O(1) |
| Library catalog lookup | O(log n) per query | O(1) |
| Maximum sum subarray of size k | O(n) | O(1) |
| Mall footfall range report | O(n + q) preprocessing and queries | O(n + q) including output |
| Longest budget-friendly streak | O(n) | O(1) |
| Net-balance period counter | O(n) average | O(n) |
| Exam score band counter | O(log n) per query | O(1) |
| Spiral stock audit route | O(mn) | O(mn) for output |

Here, n is the number of elements, q is the number of range queries, and m × n is the grid size.
