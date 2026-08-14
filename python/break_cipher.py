"""
Break the Cipher.

Implement min_words_to_decode below. See ../README.md for the full
problem statement, constraints, and worked examples.

The helper functions are optional scaffolding — use them, change their
signatures, or delete them and structure your solution however you like.
"""

from typing import List, Set


def to_word_set(dictionary: List[str]) -> Set[str]:
    """Return the dictionary as a set, for fast membership checks."""
    # TODO: implement
    raise NotImplementedError


def max_word_length(dictionary: List[str]) -> int:
    """Return the length of the longest word in the dictionary (0 if empty)."""
    # TODO: implement
    raise NotImplementedError


def min_words_to_decode(message: str, dictionary: List[str]) -> int:
    """
    Return the minimum number of dictionary words needed to exactly split
    message end-to-end, or -1 if it can't be fully decoded using only
    words from the dictionary.
    """
    # TODO: implement
    raise NotImplementedError
