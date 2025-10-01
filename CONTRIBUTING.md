# Repo bootstrap: Conventional Commits + Husky + Lint (Auto Setup)

This repository includes a ready-to-run bootstrap that makes it trivial for contributors to get the Conventional Commit + Husky + lint setup running locally.

> Small automation. Big time saved. Helps contributors (and you) stay consistent — one more step toward your **1 Crore job**.

---

## What this repo provides

- `package.json` (devDependencies & scripts for husky, commitlint, ktlint, etc.)
- `commitlint.config.cjs` — commit message rules (Conventional Commits)
- `.husky/` — git hooks (commit-msg, pre-commit, etc.)
- `pre-commit` (template) — runs `npm run lint` before commit
- `setup.sh` — POSIX installer for macOS, Linux, WSL, Git-Bash
- `setup.ps1` — Windows PowerShell installer

---

## Goal

Make contributor onboarding one command:
- macOS / Linux / WSL / Git-Bash:
  ```bash
  git clone <repo-url>
  cd <repo>
  ./setup.sh ```
- Windows (PowerShell):
  ```bash
  git clone <repo-url>
  cd <repo>
  PowerShell -ExecutionPolicy Bypass -File .\setup.ps1```
