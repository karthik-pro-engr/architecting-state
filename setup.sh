#!/usr/bin/env bash
# setup.sh - Bootstrap script for collaborators
# Usage: ./setup.sh
set -euo pipefail

echo "📦 Setting up Conventional Commit & Husky for this repo..."

# Detect OS
OS="$(uname -s 2>/dev/null || echo Unknown)"
case "$OS" in
    Linux*)   platform="Linux" ;;
    Darwin*)  platform="macOS" ;;
    CYGWIN*|MINGW*|MSYS*) platform="Windows" ;;
    *)        platform="Unknown" ;;
esac

echo "🔍 Detected platform: $platform"

# 1) Install dependencies
if command -v npm >/dev/null 2>&1; then
  echo "📥 Running npm install..."
  npm install
else
  echo "❌ npm not found. Please install Node.js + npm, then re-run this script."
  exit 1
fi

# 2) Husky install
echo "🔧 Setting up Husky git hooks..."
npx husky install

# 3) Ensure hooks are executable (varies by OS)
if [ -d ".husky" ]; then
  case "$platform" in
    Linux|macOS)
      chmod +x .husky/*
      ;;
    Windows)
      # Git Bash/WSL supports chmod too, but we add extra note for Windows users
      chmod +x .husky/* || true
      echo "⚠️ If hooks don't run, ensure Git is installed with Bash support on Windows."
      ;;
    *)
      chmod +x .husky/* || true
      echo "⚠️ Unknown platform. Hooks may need manual chmod +x."
      ;;
  esac
fi

# 4) Final success message
echo "✅ Setup complete!"
echo ""
echo "👉 Try making a commit to verify setup:"
echo "   git add -A"
echo "   git commit -m \"chore: verify setup\""
echo ""
echo "If commit fails with a lint error, that means commitlint + husky are working correctly 🎉"
