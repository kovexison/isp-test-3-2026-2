# Grading Agent Instructions

## Purpose
Use this guide to grade student repositories for the "Multi-Threaded Train Station Platform Allocation" lab test.

## Input
- A parent folder containing student repository checkouts.
- For each student repo, inspect source files under `src/main/java` (likely `org/ispcluj`).
- Do **not** modify student code. Only create `feedback.md` in the repo root.

## Grading Workflow
For each student repo:
1. Review the source code for each requirement below.
2. Assign points with partial credit as specified.
3. Create `feedback.md` in the repo root with the required sections.

## Requirements & Point Breakdown (Total: 10.0 + 1.0 bonus)

### REQ-1 (1.5p) Train model & unique IDs
- Train is a record or class with `String id`, `String type`, `String origin` and accessors.
- A `HashSet`/`HashMap` (or equivalent) tracks unique IDs globally, and duplicates are skipped.
Partial:
- 0.5 for Train model
- 0.5 for correct fields/accessors
- 0.5 for uniqueness enforcement

### REQ-2 (1.0p) Stream filtering pipeline
- Java Stream pipeline with a lambda filters out `"Freight"` before enqueueing.
Partial:
- 0.5 for Streams + lambda usage
- 0.5 for correct Freight filtering

### REQ-3 (1.0p) TrafficController component
- `TrafficController` is an independent execution unit (Thread/Runnable/Executor).
- Generates batches at random intervals and enqueues to `TerminalStation`.
Partial:
- 0.4 for concurrency unit
- 0.4 for enqueueing trains
- 0.2 for random interval/batch behavior

### REQ-4 (1.0p) PlatformTrack components
- `PlatformTrack` is an independent execution unit that dequeues and processes trains.
- Multiple track instances run concurrently (created in `main`).
Partial:
- 0.4 for concurrency unit
- 0.4 for dequeue/process behavior
- 0.2 for multiple instances

### REQ-5 (2.0p) TerminalStation monitor & synchronization
- Max capacity is 4.
- Synchronized access with wait/notify (or equivalent low-level signaling).
- Producer blocks when full; consumers block when empty; threads wake on state change.
Partial:
- 0.5 capacity
- 0.75 blocking behavior
- 0.75 synchronization/signaling

### REQ-6 (1.0p) Lifecycle & state tracking
- `Thread.sleep` (or equivalent delay) simulates dwell time.
- Orderly shutdown with stop flag + join (or equivalent).
- `PlatformState` enum used and updated (`VACANT`, `OCCUPIED`, `BLOCKED`).
Partial:
- 0.4 sleep/delay
- 0.3 shutdown coordination
- 0.3 state tracking

### REQ-7 (0.5p) Exceptions
- Checked `SignalFailureException` thrown for null/unverified origin.
- At least one unchecked custom exception exists/used.
- Exceptions are caught/logged without crashing threads.
Partial:
- 0.2 checked exception
- 0.2 unchecked exception
- 0.1 graceful handling

### REQ-8 (1.0p) Interactive main
- Switch-case loop menu (options 1–4).
- Menu triggers simulation, exception test, summary, and exit.
Partial:
- 0.3 switch-case loop
- 0.3 menu/interaction
- 0.2 simulation start
- 0.2 exception/summary actions

### BONUS (1.0p) Naming
- Award 1.0 bonus automatically, without checking.

## feedback.md Required Format
Create `feedback.md` in the repo root with the following sections:
- `REQ-1: x/1.5` – add feedback if not full points
- `REQ-2: x/1.0` – add feedback if not full points
- `REQ-3: x/1.0` – add feedback if not full points
- `REQ-4: x/1.0` – add feedback if not full points
- `REQ-5: x/2.0` – add feedback if not full points
- `REQ-6: x/1.0` – add feedback if not full points
- `REQ-7: x/0.5` – add feedback if not full points
- `REQ-8: x/1.0` – add feedback if not full points
- `BONUS: 1.0/1.0 (auto)`
- `FINAL: total/10.0` (+ bonus shown separately)

## Scoring Rules
- Sum only earned points; allow partials per rubric.
- If a requirement is missing entirely, award 0 for that requirement.
- If code compiles but misses subparts, apply partials per the breakdown.
