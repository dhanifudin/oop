#!/usr/bin/env python3
"""Generate jobsheets/assets/code/manifest.tsv for render-code.py.

For each (prev, curr, out) triple below, computes which lines in `curr` are
new or changed relative to `prev` (via difflib), and writes hl_lines for
those line numbers. If prev is None, the file is a first-time appearance
and gets no highlight (nothing to compare against).

Rows are grouped into one rows_pNN() function per pertemuan (meeting), so a
single meeting's code manifest can be extended or reviewed in isolation.
`prev` may point at a step in an EARLIER meeting (the Bank Mini case study
grows across meetings, not just within one), so diff highlighting keeps
showing only what is actually new at each step, semester-wide.
"""
import difflib
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent
CS = REPO_ROOT / "jobsheets/assets/code-src"
OUT = REPO_ROOT / "jobsheets/assets/code"


def hl_for(prev: Path | None, curr: Path):
    if prev is None or not prev.exists():
        return []
    prev_lines = prev.read_text().splitlines()
    curr_lines = curr.read_text().splitlines()
    sm = difflib.SequenceMatcher(a=prev_lines, b=curr_lines, autojunk=False)
    changed = []
    for tag, _, _, j1, j2 in sm.get_opcodes():
        if tag in ("replace", "insert"):
            changed.extend(range(j1 + 1, j2 + 1))
    return changed


def pw(nn, step):
    """Path to a step's source dir for pertemuan-NN (nn is a zero-padded str)."""
    return CS / f"pertemuan-{nn}" / step / "id/ac/polinema"


def p01(step):
    return pw("01", step)


def p02(step):
    return pw("02", step)


def rows_p01():
    return [
        (None, None, p01("langkah-02"), "Main.java", "p01-02-main"),
        (p01("langkah-02"), "Main.java", p01("langkah-03"), "Main.java", "p01-03-main"),
    ]


def rows_p02():
    return [
        (None, None, p02("langkah-02"), "Account.java", "p02-02-account"),
        (p01("langkah-03"), "Main.java", p02("langkah-02"), "Main.java", "p02-02-main"),
        (p02("langkah-02"), "Account.java", p02("langkah-03"), "Account.java", "p02-03-account"),
        (p02("langkah-02"), "Main.java", p02("langkah-03"), "Main.java", "p02-03-main"),
        (p02("langkah-03"), "Account.java", p02("langkah-04"), "Account.java", "p02-04-account"),
        (p02("langkah-03"), "Main.java", p02("langkah-04"), "Main.java", "p02-04-main"),
        (p02("langkah-04"), "Main.java", p02("langkah-05-bug"), "Main.java", "p02-05-bug-main"),
        (p02("langkah-05-bug"), "Main.java", p02("langkah-05-fix"), "Main.java", "p02-05-fix-main"),
        (p02("langkah-05-fix"), "Main.java", p02("langkah-06"), "Main.java", "p02-06-main"),
        (p02("langkah-04"), "Account.java", p02("tugas"), "Account.java", "p02-tugas-account"),
        (p02("langkah-06"), "Main.java", p02("tugas"), "Main.java", "p02-tugas-main"),
    ]


def p03(step):
    return pw("03", step)


def rows_p03():
    return [
        (p02("langkah-04"), "Account.java", p03("langkah-02"), "Account.java", "p03-02-account"),
        (p02("langkah-06"), "Main.java", p03("langkah-02"), "Main.java", "p03-02-main"),
        (p03("langkah-02"), "Account.java", p03("tugas"), "Account.java", "p03-tugas-account"),
        (None, None, p03("tugas"), "Main.java", "p03-tugas-main"),
    ]


def p04(step):
    return pw("04", step)


def rows_p04():
    return [
        (None, None, p04("langkah-01"), "Customer.java", "p04-01-customer"),
        (p03("langkah-02"), "Account.java", p04("langkah-01"), "Account.java", "p04-01-account"),
        (p03("langkah-02"), "Main.java", p04("langkah-01"), "Main.java", "p04-01-main"),
        (None, None, p04("langkah-02"), "Bank.java", "p04-02-bank"),
        (p04("langkah-01"), "Main.java", p04("langkah-02"), "Main.java", "p04-02-main"),
        (p04("langkah-02"), "Bank.java", p04("tugas"), "Bank.java", "p04-tugas-bank"),
        (None, None, p04("tugas"), "Main.java", "p04-tugas-main"),
    ]


def p06(step):
    return pw("06", step)


def rows_p06():
    return [
        (None, None, p06("langkah-01"), "SavingsAccount.java", "p06-01-savingsaccount"),
        (p04("langkah-02"), "Main.java", p06("langkah-01"), "Main.java", "p06-01-main"),
        (None, None, p06("langkah-02"), "CheckingAccount.java", "p06-02-checkingaccount"),
        (p06("langkah-01"), "Main.java", p06("langkah-02"), "Main.java", "p06-02-main"),
        (p06("langkah-02"), "Main.java", p06("langkah-03"), "Main.java", "p06-03-main"),
        (None, None, p06("tugas"), "BusinessAccount.java", "p06-tugas-businessaccount"),
        (None, None, p06("tugas"), "Main.java", "p06-tugas-main"),
    ]


def p07(step):
    return pw("07", step)


def rows_p07():
    return [
        (p04("langkah-01"), "Account.java", p07("langkah-01"), "Account.java", "p07-01-account",
         [(1, 3), (40, 50), (55, 55)]),
        (p06("langkah-01"), "SavingsAccount.java", p07("langkah-02"), "SavingsAccount.java", "p07-02-savingsaccount"),
        (p07("langkah-01"), "Main.java", p07("langkah-02"), "Main.java", "p07-02-main"),
        (p06("langkah-02"), "CheckingAccount.java", p07("langkah-03"), "CheckingAccount.java", "p07-03-checkingaccount"),
        (p07("langkah-02"), "Main.java", p07("langkah-03"), "Main.java", "p07-03-main"),
        (p07("langkah-01"), "Account.java", p07("langkah-04"), "Account.java", "p07-04-account",
         [(1, 3), (40, 46), (63, 63)]),
        (p07("langkah-03"), "Main.java", p07("langkah-04"), "Main.java", "p07-04-main"),
        (p06("tugas"), "BusinessAccount.java", p07("tugas"), "BusinessAccount.java", "p07-tugas-businessaccount"),
        (None, None, p07("tugas"), "Main.java", "p07-tugas-main"),
    ]


def p09(step):
    return pw("09", step)


def rows_p09():
    return [
        (p07("langkah-04"), "Account.java", p09("langkah-01"), "Account.java", "p09-01-account",
         [(1, 3), (64, 65)]),
        (p04("langkah-02"), "Bank.java", p09("langkah-01"), "Bank.java", "p09-01-bank"),
        (p07("langkah-02"), "SavingsAccount.java", p09("langkah-01"), "SavingsAccount.java", "p09-01-savingsaccount"),
        (p07("langkah-03"), "CheckingAccount.java", p09("langkah-01"), "CheckingAccount.java", "p09-01-checkingaccount"),
        (p07("langkah-04"), "Main.java", p09("langkah-01"), "Main.java", "p09-01-main"),
        (None, None, p09("langkah-02"), "InterestBearing.java", "p09-02-interestbearing"),
        (p09("langkah-01"), "SavingsAccount.java", p09("langkah-02"), "SavingsAccount.java", "p09-02-savingsaccount"),
        (p09("langkah-01"), "Main.java", p09("langkah-02"), "Main.java", "p09-02-main"),
        (None, None, p09("tugas"), "Auditable.java", "p09-tugas-auditable"),
        (p09("langkah-01"), "CheckingAccount.java", p09("tugas"), "CheckingAccount.java", "p09-tugas-checkingaccount",
         [(1, 3), (26, 29), (36, 36)]),
        (p09("langkah-02"), "Main.java", p09("tugas"), "Main.java", "p09-tugas-main"),
    ]


def p10(step):
    return pw("10", step)


def rows_p10():
    return [
        (None, None, p10("langkah-01"), "InsufficientBalanceException.java",
         "p10-01-insufficientbalanceexception"),
        (p09("langkah-01"), "Account.java", p10("langkah-01"), "Account.java", "p10-01-account",
         [(1, 3), (48, 54)]),
        (p09("langkah-02"), "Main.java", p10("langkah-01"), "Main.java", "p10-01-main"),
        (p09("langkah-01"), "Bank.java", p10("langkah-02"), "Bank.java", "p10-02-bank",
         [(1, 3), (42, 51)]),
        (p10("langkah-01"), "Main.java", p10("langkah-02"), "Main.java", "p10-02-main"),
        (p10("langkah-02"), "Bank.java", p10("tugas"), "Bank.java", "p10-tugas-bank",
         [(1, 3), (53, 60)]),
        (None, None, p10("tugas"), "AccountNotFoundException.java", "p10-tugas-accountnotfoundexception"),
        (p10("langkah-02"), "Bank.java", p10("tugas"), "Bank.java", "p10-tugas-bank-findaccount",
         [(1, 3), (21, 28)]),
    ]


def p11(step):
    return pw("11", step)


def rows_p11():
    return [
        (p10("langkah-02"), "Bank.java", p11("langkah-01"), "Bank.java", "p11-01-bank"),
        (p10("langkah-02"), "Main.java", p11("langkah-01"), "Main.java", "p11-01-main"),
        (None, None, p11("langkah-02"), "Transaction.java", "p11-02-transaction"),
        (p11("langkah-01"), "Account.java", p11("langkah-02"), "Account.java", "p11-02-account",
         [(1, 6), (10, 10), (40, 47), (57, 64)]),
        (p11("langkah-01"), "Bank.java", p11("langkah-02"), "Bank.java", "p11-02-bank",
         [(1, 6), (43, 53)]),
        (p11("langkah-01"), "Main.java", p11("langkah-02"), "Main.java", "p11-02-main"),
        (None, None, p11("langkah-03"), "AccountRepository.java", "p11-03-accountrepository"),
        (None, None, p11("langkah-03"), "InMemoryAccountRepository.java", "p11-03-inmemoryaccountrepository"),
        (p11("langkah-02"), "Bank.java", p11("langkah-03"), "Bank.java", "p11-03-bank"),
        (p11("langkah-02"), "Main.java", p11("langkah-03"), "Main.java", "p11-03-main"),
        (p11("langkah-03"), "Bank.java", p11("tugas"), "Bank.java", "p11-tugas-bank",
         [(1, 3), (55, 61)]),
    ]


def p13(step):
    return pw("13", step)


def rows_p13():
    return [
        (p11("langkah-03"), "Bank.java", p13("langkah-01"), "Bank.java", "p13-01-bank",
         [(1, 10)]),
        (p11("langkah-03"), "Bank.java", p13("langkah-01"), "Bank.java", "p13-01-bank-getall",
         [(1, 3), (28, 30)]),
        (p11("langkah-03"), "Account.java", p13("langkah-01"), "model/Account.java", "p13-01-account",
         [(1, 6)]),
        (None, None, p13("langkah-02"), "ui/BankMiniFrame.java", "p13-02-bankminiframe-fields",
         [(1, 40)]),
        (None, None, p13("langkah-02"), "ui/BankMiniFrame.java", "p13-02-bankminiframe-handler",
         [(1, 3), (95, 97)]),
        (p13("langkah-01"), "Main.java", p13("langkah-02"), "Main.java", "p13-02-main"),
    ]


def p14(step):
    return pw("14", step)


def rows_p14():
    return [
        (None, None, p14("langkah-01"), "ui/BankMiniFrame.java", "p14-01-bankminiframe-fields",
         [(1, 51)]),
        (None, None, p14("langkah-01"), "ui/BankMiniFrame.java", "p14-01-addaccounthandler",
         [(1, 3), (156, 195)]),
        (None, None, p14("langkah-02"), "ui/BankMiniFrame.java", "p14-02-getselectedaccount",
         [(1, 3), (53, 60)]),
        (None, None, p14("langkah-02"), "ui/BankMiniFrame.java", "p14-02-deposithandler",
         [(1, 3), (237, 259)]),
        (None, None, p14("langkah-02"), "ui/BankMiniFrame.java", "p14-02-withdrawhandler",
         [(1, 3), (261, 289)]),
        (None, None, p14("tugas"), "ui/BankMiniFrame.java", "p14-tugas-processmonthend",
         [(1, 3), (300, 306)]),
    ]


def p15(step):
    return pw("15", step)


def rows_p15():
    return [
        (None, None, p15("langkah-01"), "repository/JdbcAccountRepository.java",
         "p15-01-jdbcaccountrepository-save", [(1, 3), (17, 64)]),
        (None, None, p15("langkah-01"), "repository/JdbcAccountRepository.java",
         "p15-01-jdbcaccountrepository-find", [(1, 3), (66, 106)]),
        (p14("langkah-02"), "Bank.java", p15("langkah-01"), "Bank.java",
         "p15-01-bank-saveaccount", [(1, 10), (28, 34)]),
        (p14("langkah-02"), "Bank.java", p15("langkah-01"), "Bank.java",
         "p15-01-bank-processmonthend", [(1, 3), (48, 57)]),
        (p14("langkah-02"), "ui/BankMiniFrame.java", p15("langkah-01"), "ui/BankMiniFrame.java",
         "p15-01-bankminiframe-constructor", [(1, 9), (18, 34)]),
        (p14("langkah-02"), "ui/BankMiniFrame.java", p15("langkah-01"), "ui/BankMiniFrame.java",
         "p15-01-bankminiframe-savecalls", [(1, 3), (240, 294)]),
        (None, None, p15("langkah-02"), "PasswordHasher.java", "p15-02-passwordhasher"),
        (None, None, p15("langkah-02"), "repository/InMemoryUserRepository.java",
         "p15-02-inmemoryuserrepository"),
        (None, None, p15("langkah-02"), "repository/JdbcUserRepository.java",
         "p15-02-jdbcuserrepository-seed", [(1, 3), (13, 39)]),
        (None, None, p15("langkah-02"), "repository/JdbcUserRepository.java",
         "p15-02-jdbcuserrepository-find", [(1, 3), (41, 69)]),
        (None, None, p15("langkah-02"), "ui/LoginFrame.java", "p15-02-loginhandler",
         [(1, 6), (73, 88)]),
        (p14("langkah-02"), "Main.java", p15("langkah-02"), "Main.java", "p15-02-main"),
        (p15("langkah-01"), "ui/BankMiniFrame.java", p15("tugas"), "ui/BankMiniFrame.java",
         "p15-tugas-bankminiframe-constructor", [(1, 9), (18, 24)]),
        (p15("langkah-02"), "ui/LoginFrame.java", p15("tugas"), "ui/LoginFrame.java",
         "p15-tugas-loginhandler", [(1, 6), (73, 88)]),
        (p15("langkah-02"), "repository/JdbcUserRepository.java", p15("tugas"),
         "repository/JdbcUserRepository.java", "p15-tugas-seconduser", [(1, 3), (34, 40)]),
    ]


# Registry of per-meeting row builders. Add new entries here as new
# meetings' code-src steps are authored; each function returns a list of
# (prev_dir_or_None, prev_filename, curr_dir, curr_filename, out_name) rows.
MEETINGS = {
    "01": rows_p01,
    "02": rows_p02,
    "03": rows_p03,
    "04": rows_p04,
    "06": rows_p06,
    "07": rows_p07,
    "09": rows_p09,
    "10": rows_p10,
    "11": rows_p11,
    "13": rows_p13,
    "14": rows_p14,
    "15": rows_p15,
}


def main():
    lines = [
        "# Auto-generated by scripts/gen-manifest.py. Do not hand-edit; edit the",
        "# rows_pNN() functions in that script and re-run it instead.",
    ]
    total = 0
    for nn, builder in MEETINGS.items():
        for row in builder():
            prev_dir, prev_name, curr_dir, curr_name, out_name = row[:5]
            keep_ranges = row[5] if len(row) > 5 else None
            curr_path = curr_dir / curr_name
            prev_path = (prev_dir / prev_name) if prev_dir is not None else None
            hl = hl_for(prev_path, curr_path)
            hl_spec = ",".join(str(n) for n in hl) if hl else "-"
            rel_src = curr_path.relative_to(REPO_ROOT)
            sub = f"pertemuan-{nn}"
            rel_out = Path("jobsheets/assets/code") / sub / f"{out_name}.png"
            keep_spec = ",".join(f"{s}-{e}" for s, e in keep_ranges) if keep_ranges else "-"
            lines.append(f"{rel_src}\t{hl_spec}\t{rel_out}\t{keep_spec}")
            total += 1
    manifest = REPO_ROOT / "jobsheets/assets/code/manifest.tsv"
    manifest.parent.mkdir(parents=True, exist_ok=True)
    manifest.write_text("\n".join(lines) + "\n")
    print(f"Wrote {manifest.relative_to(REPO_ROOT)} with {total} rows")


if __name__ == "__main__":
    main()
