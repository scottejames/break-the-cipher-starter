"""Demo runner — runs min_words_to_decode on one example case and prints the result."""

import os

from break_cipher import min_words_to_decode
from test_data import TEST_DATA_DIR, load_case


def main() -> None:
    path = os.path.join(TEST_DATA_DIR, "medium", "01_trap.txt")
    case = load_case(path)

    print(f"Case: {case.name}")
    print(f"Message: {case.message}")
    print(f"Dictionary: {case.dictionary}")

    result = min_words_to_decode(case.message, case.dictionary)
    print(f"Minimum words to decode: {result}")


if __name__ == "__main__":
    main()
