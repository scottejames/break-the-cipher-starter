# Test data

Every case for Break the Cipher lives here as a plain text file, grouped
into three difficulty tiers. Both `python/run_tests.py` and
`java/src/TestRunner.java` load directly from this directory — nothing is
duplicated in code.

## File format

```
name=<case name>
expected=<int>
message=<string>
dictionary=
<word 0>
<word 1>
...
```

Everything from the line after `dictionary=` to the end of the file is
the dictionary, one word per line. Filenames are numbered (`01_...`,
`02_...`) purely so both loaders sort them into a stable, predictable
order when they list a directory — the number carries no other meaning.

`expected` was never computed by hand — it comes from a working reference
implementation kept outside this repository, checked once and then
treated as ground truth. Take it as correct.

## Simple — `simple/`

Tiny messages and dictionaries. Each one isolates a single mechanic
rather than combining several, so a failure here points at a specific
piece of missing logic rather than "something is wrong somewhere."

| Case | Message | Dictionary | Expected | Tests |
|---|---|---|---|---|
| `01_single_word` | `cat` | `cat` | 1 | The whole message is one dictionary word. If this fails, the basic mechanic itself is broken. |
| `02_two_words` | `catdog` | `cat`, `dog` | 2 | A message made of more than one word, with an unambiguous split. |
| `03_undecodable_leftover` | `cats` | `cat` | -1 | The dictionary covers a prefix of the message but there's a leftover (`s`) that matches nothing. Checks that a partial match isn't mistaken for a full one. |
| `04_single_char_fallback` | `abc` | `a`, `b`, `c` | 3 | Only single-character words are available, so every character is its own word. |
| `05_must_minimize` | `aaaa` | `a`, `aa`, `aaa` | 2 | `a`+`a`+`a`+`a` is a *valid* decoding, but not the shortest one (`aa`+`aa` is). Checks that the solution finds the minimum, not just any valid split. |
| `06_no_match_at_all` | `xyz` | `abc` | -1 | Nothing in the dictionary even matches the first character. |
| `07_irrelevant_dictionary_entries` | `ab` | `a`, `b`, `xyz`, `longwordnotused` | 2 | The dictionary contains words that don't appear anywhere in the message. Checks that unrelated entries don't confuse the result. |
| `08_prefer_whole_word` | `apple` | `apple`, `app`, `le` | 1 | `apple` could be split into `app`+`le`, but the whole word is itself in the dictionary and is the better answer. |

## Medium — `medium/`

Still small enough to work out with pencil and paper, but big enough that
the interesting behaviour is a genuine decision, not a one-glance
inspection.

| Case | Message | Dictionary | Expected | Tests |
|---|---|---|---|---|
| `01_trap` | `abcde` | `abc`, `ab`, `cde` | 2 | **The trap.** A solution that decodes greedily — always taking the longest matching word at the current position — picks `abc` first, then gets stuck: nothing in the dictionary matches what's left (`de`). It would report this message as undecodable, even though `ab` + `cde` is a perfectly valid decoding. This is the single highest-signal case in the whole suite. |
| `02_trap_doubled` | `abcdeabcde` | `abc`, `ab`, `cde` | 4 | The same trap, twice in a row. Checks that getting case `01` right wasn't a fluke — the same wrong turn has to be avoided consistently, not just once. |
| `03_must_minimize_longer` | `aaaaaaaaaa` (10 a's) | `a`, `aaa`, `aaaaa` | 2 | The same "must minimize, not just find a valid split" idea as the simple tier's `05_must_minimize`, but with more candidate word lengths to weigh against each other. |
| `04_near_miss_undecodable` | `codewordisnotherenow` | `code`, `word`, `the`, `is`, `not`, `here` | -1 | Almost the whole message decodes cleanly — it's only the last few characters that don't match anything. Checks that a solution keeps checking to the very end rather than declaring success early. |
| `05_lookahead` | `therein` | `the`, `there`, `he`, `r`, `e`, `i`, `n` | 3 | `the` matches at the very start and looks like the obvious first word, but committing to it leads to a longer decoding overall than taking `there` instead. |

## Hard — `hard/`

Two different kinds of case, both too large to work out by hand.

**Pathological (`01`–`03`).** Each message is a run of the same repeated
character, with a dictionary containing every prefix length up to some
maximum — but never a single word long enough to cover the whole
message. There are enormous numbers of different ways to split these
messages into valid words, almost all of them irrelevant to the actual
minimum. An approach that explores each possible split from scratch,
without reusing what it already worked out about shorter prefixes of the
message, will take a very long time here even though these messages are
barely a few dozen characters long. An approach that avoids repeating
that work stays instant regardless.

**Long transmissions (`04`–`06`).** Realistic-scale messages (several
thousand characters) built from a themed word list, either fully
decodable by construction or, in one case, deliberately corrupted so it
isn't. These aren't adversarial the way the pathological cases are —
they exist to check that a solution's performance holds up at a size
where a per-character or per-position cost that seemed negligible on the
small cases can add up.

| Case | Message length | Expected | Tests |
|---|---|---|---|
| `01_pathological_n30` | 30 | 3 | Smallest pathological case — confirms the basic behaviour before scaling up. |
| `02_pathological_n40` | 40 | 4 | A shorter maximum word length than `01`, forcing more pieces overall. |
| `03_pathological_n44` | 44 | 3 | A longer maximum word length again, on the longest of the three pathological messages. |
| `04_transmission_6000` | 5,994 | 934 | A long, fully decodable message. Mostly a scale and performance check. |
| `05_transmission_12000` | 11,997 | 1,855 | Roughly double the length of `04` — the heaviest scale check in the suite. |
| `06_corrupted_transmission` | 8,997 | -1 | The same kind of long message as `04`/`05`, but with a single character changed partway through so no valid decoding exists. Checks that a solution correctly detects global infeasibility at scale, rather than reporting a wrong partial count or running out of time trying. |
