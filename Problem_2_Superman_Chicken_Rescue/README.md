# Superman's Chicken Rescue

## Problem Description

In a one-dimensional world, Superman needs to protect chickens from a heavy rainstorm using a roof of limited length. Given the positions of chickens and the length of the roof Superman can carry, determine the maximum number of chickens Superman can protect.

### Input
- Two integers `n` and `k` (1 <= n,k <= 1,000,000), where:
   - `n` represents the number of chickens
   - `k` denotes the length of the roof Superman can carry
- `n` integers (1 <= x <= 1,000,000,000) representing the positions of the chickens on the one-dimensional axis

### Output
A single integer denoting the maximum number of chickens Superman can protect with the given roof length.

## Solution Approach

This problem can be efficiently solved using the Sliding Window algorithm:

1. Initialize two pointers, `left` and `right`, both at the start of the array of chicken positions.
2. Move the `right` pointer to expand the window until the difference between positions at `right` and `left` is greater than or equal to `k` (roof length).
3. Update the maximum number of chickens if the current window size is larger.
4. Move the `left` pointer to the right to start a new window.
5. Repeat steps 2-4 until the `right` pointer reaches the end of the array.