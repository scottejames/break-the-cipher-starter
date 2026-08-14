<p align="center">
  <img src="logo.svg" alt="Break the Cipher" width="760">
</p>

# Break the Cipher — starter project

A burst of static just handed you a wall of text — one unbroken run of
lowercase letters, no spaces, no punctuation, not one clue about where a
word ends and the next begins. Somewhere in the recovered dictionary is
proof this thing actually says something. Whether it does, and what it
takes to prove it, is now your problem.

This repo is where you build the decoder, in Python or Java, whichever
you're happier in. Both are graded the same way, so pick on comfort, not
on which one you think looks better.

## The problem

You're given a `message` — a string of lowercase letters with no spaces —
and a `dictionary` — a list of known valid words. A *decoding* splits the
message into a sequence of dictionary words, end to end, using every
character exactly once, in order, with nothing left over.

More than one decoding might exist. You want the one that uses the
**fewest words**.

**Your job:**

```
min_words_to_decode(message, dictionary) -> int
```

Work out the minimum number of dictionary words needed to fully decode
the message. If there's no way to do it at all — some stretch of
characters that matches nothing, however you slice it — say so honestly:
return `-1`.

### Worked example

```
message:    abcde
dictionary: abc, ab, cde
```

The obvious first move is `abc` — it's the longest match sitting right at
the start. Take it, though, and you're stuck: whatever's left (`de`)
doesn't match anything in the dictionary. Back up and take the shorter
`ab` instead, and the rest falls into place: `ab` + `cde`.

- Answer → `2`

Want a slower, more thorough walk through this and one more example,
staged decision by decision? See [EXAMPLE.md](EXAMPLE.md).

### Constraints

Nothing sneaky here — just the numbers to design around:

- `1 ≤ length of message ≤ 20,000`
- `1 ≤ number of words in dictionary ≤ 2,000`
- `1 ≤ length of each dictionary word ≤ 20`
- all characters, in both the message and the dictionary, are lowercase
  `a`–`z`

## Layout

```
break-the-cipher-starter/
  test_data/
    simple/    <- 8 tiny, hand-traceable messages
    medium/    <- 5 bigger hand-designed cases, still traceable on paper
    hard/      <- 6 generated cases — too large to solve by hand
  python/
    break_cipher.py    <- implement your solution here
    test_data.py         loads cases from ../test_data
    main.py               a small demo runner (prints one example)
    run_tests.py           the test harness — every case, PASS/FAIL, timing, an efficiency band
    scripts/
      compile.sh           syntax-checks the Python files
      run.sh                runs main.py
      test.sh               runs run_tests.py
  java/
    src/
      BreakCipher.java    <- implement your solution here
      TestData.java         loads cases from ../test_data
      Main.java              a small demo runner (prints one example)
      TestRunner.java         the test harness — every case, PASS/FAIL, timing, an efficiency band
    scripts/
      compile.sh           javac's everything into java/build
      run.sh                compiles, then runs Main
      test.sh               compiles, then runs TestRunner
```

You only need to touch `break_cipher.py` / `BreakCipher.java` — everything
else is scaffolding that's already wired up and ready to go: the test
data, the demo runner, the test harness, the shell scripts.

Each solution file has a couple of empty helper methods already sketched
in (turning the dictionary into something you can check membership
against quickly, finding the longest word). Use them, rename them, rip
them out entirely — whatever gets you to a solution you're happy with.
They're there to save you some typing, not to tell you how to think about
the problem.

## Test data tiers

- **Simple** (`test_data/simple/`) — a handful of characters, small
  enough to check your basic decoding logic just by looking at it.
- **Medium** (`test_data/medium/`) — still small enough to trace on
  paper if you want to sanity-check an answer, but it takes real
  attention — more than one case is built specifically so that the
  obvious first move turns out to be the wrong one.
- **Hard** (`test_data/hard/`) — nobody's tracing these by hand. Some are
  short but nasty on purpose, built to make an approach that explores
  every possibility from scratch grind to a halt; others are just long —
  realistic-scale transmissions, thousands of characters, to check that
  your solution's performance holds up once the input actually gets big.
  If a run hangs or drags on the `hard` tier, that's worth digging into —
  the message isn't broken, your approach probably needs a rethink.

## Quick start

Python (needs Python 3.8+, no other dependencies):

```bash
cd python
./scripts/test.sh     # run the test suite
./scripts/run.sh       # run the demo on one example case
```

Java (needs a JDK on your PATH, no build tool required):

```bash
cd java
./scripts/test.sh     # compiles, then runs the test suite
./scripts/run.sh       # compiles, then runs the demo on one example case
```

## Definition of done

`./scripts/test.sh` should print `TOTAL: 19 passed, 0 failed` in both
languages, ending with `Efficiency band: Efficient (< 2s total)`. Right
now every test fails with `NOT IMPLEMENTED` — that's your starting line,
not a bug.

That last line is reading the `hard` tier's total time: `Efficient` under
2 seconds, `Adequate` up to 10, `Slow` beyond that. A correct, reasonably
efficient solution should land comfortably in `Efficient`. If you're
seeing `Adequate` or `Slow`, or the hard tier just never finishes, take
that seriously — it's telling you something real about your approach, not
just filling space at the bottom of the output.

Good luck. Somewhere in that noise is a message.
