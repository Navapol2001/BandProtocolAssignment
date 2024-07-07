# Boss Baby's Revenge

## Problem Description

Boss Baby, known for his cool and clever ways, deals with teasing from neighborhood kids who shoot water guns at his house. In response, Boss Baby seeks revenge by shooting `at least one shot back`, but only if the kids have already shot at him first. Your task is to check if Boss Baby has sought revenge for every shot aimed at him at least once and hasn't initiated any shooting himself.

### Input
A string (S, 1 <= len(S) <= 1,000,000) containing characters 'S' and 'R', where:
- 'S' represents a shot from the neighborhood kids
- 'R' represents a revenge shot from Boss Baby

### Output
- Return "Good boy" if all shots have been revenged at least once and Boss Baby doesn't initiate any shots.
- Return "Bad boy" if these conditions are not satisfied.

## Solution Approach

1. Check if the first character is 'R'. If so, return "Bad boy" immediately.
2. Iterate through the string, counting 'S' and 'R' occurrences.
3. Compare the counts:
    - If revenge count >= shot count, return "Good boy"
    - Otherwise, return "Bad boy"
