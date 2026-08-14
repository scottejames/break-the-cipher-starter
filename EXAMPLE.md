# Worked example, step by step

This walks through the problem by hand, one small decision at a time,
using a real example (it's the same case as
`test_data/medium/01_trap.txt`, so you can cross-check the final answer
against that file). The goal here is to make sure the *rules* of the
problem are completely clear before you write any code — it deliberately
stops short of showing you an efficient way to solve it. See
[README.md](README.md) for the full problem statement and constraints.

## The setup

```
message:    abcde
dictionary: abc, ab, cde
```

A **decoding** is a way of cutting `message` into a sequence of pieces,
left to right, where:

- every piece is a word that appears in `dictionary`,
- the pieces use every character of `message` exactly once, in order,
  with nothing skipped and nothing left over.

We want the decoding that uses the **fewest pieces** — or `-1` if no
decoding exists at all.

## Stage 1 — try the word that fits best right away

Start at the very first character. Which dictionary words match the
message starting from position 0?

- `abc` — matches `message[0:3]` = `"abc"`. Yes.
- `ab` — matches `message[0:2]` = `"ab"`. Also yes.
- `cde` — would need the message to start with `c`. It starts with `a`.
  No match here.

Two words fit at the start. The one that eats the most characters is
`abc`, so let's provisionally take it:

```
abc | de
```

## Stage 2 — that choice turns out to be a dead end

Three characters used, two left over: `de` (positions 3–4). Check the
dictionary again, this time against what's left:

- `abc` — doesn't match `"de"`.
- `ab` — doesn't match `"de"`.
- `cde` — would need `de` to start with `c`. It doesn't.

Nothing matches. Taking `abc` first leaves a remainder that can't be
decoded by anything in the dictionary. That path is a dead end — not
because the message is undecodable, but because *this particular first
choice* was wrong. We have to back up and try the other option from
Stage 1.

## Stage 3 — back up and try the shorter word instead

Back at position 0, take `ab` instead of `abc`:

```
ab | cde
```

Two characters used, three left over: `cde` (positions 2–4). Check the
dictionary against `"cde"`:

- `cde` — matches exactly. 

Nothing left over after that — the whole message is used up.

## Stage 4 — confirm it's a valid decoding

`ab` + `cde` = `abcde`. Every character of the original message is
covered exactly once, in order, and both pieces are real dictionary
words. This is a valid decoding, using **2** words.

## Stage 5 — check it's actually the *minimum*

A decoding using just **1** word would require the entire message,
`"abcde"`, to itself be a dictionary word. It isn't — the dictionary only
contains `abc`, `ab`, and `cde`. So 1 word is impossible, and 2 is the
smallest number of words any valid decoding can use.

**Answer: `min_words_to_decode("abcde", ["abc", "ab", "cde"])` = `2`.**

The lesson to take from this isn't "always try shorter words" — a
different message and dictionary could easily make the *longer* first
match the right one. The lesson is that the first match you find at a
position isn't guaranteed to lead anywhere, and you can't know that
without seeing what happens to the rest of the message afterwards.

---

## A second wrinkle: "a valid decoding" isn't always "the answer"

Different example (same as `test_data/simple/05_must_minimize.txt`):

```
message:    aaaa
dictionary: a, aa, aaa
```

Several different decodings are all valid here — see for yourself that
each one below uses only dictionary words and covers all four letters
with nothing left over:

| Decoding | Pieces used |
|---|---|
| `a` + `a` + `a` + `a` | 4 |
| `aaa` + `a` | 2 |
| `a` + `aaa` | 2 |
| `aa` + `aa` | 2 |

All four are legitimate decodings of `aaaa`. But the question isn't
"find a decoding" — it's "find the one with the fewest words." The
smallest count among all of them is **2**, so that's the answer, even
though a perfectly valid 4-word decoding also exists. A solution that
stops as soon as it finds *any* working decoding, without checking
whether a shorter one exists, would get this case wrong.

**Answer: `min_words_to_decode("aaaa", ["a", "aa", "aaa"])` = `2`.**
