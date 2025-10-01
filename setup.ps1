# Run in PowerShell (as normal user):
# PowerShell -ExecutionPolicy Bypass -File .\setup.ps1

param(
  [switch]$SkipInstall
)

Write-Host "📦 Repo setup (PowerShell) starting..."

# Check Node/npm
if (-not (Get-Command node -ErrorAction SilentlyContinue)) {
  Write-Host "❌ Node.js is not installed or not on PATH. Install Node (LTS) and rerun." -ForegroundColor Red
  exit 1
}
if (-not (Get-Command npm -ErrorAction SilentlyContinue)) {
  Write-Host "❌ npm not found. Ensure npm is available (comes with Node.js)." -ForegroundColor Red
  exit 1
}

if (-not $SkipInstall) {
  if (Test-Path package-lock.json) {
    Write-Host "📥 Running npm ci..."
    npm ci
  } else {
    Write-Host "📥 Running npm install..."
    npm install
  }
} else {
  Write-Host "ℹ️ Skipping npm install (SkipInstall passed)."
}

# Husky install
Write-Host "🔧 Running npx husky install..."
npx husky install

# Make hooks executable is not necessary on Windows, but ensure they exist
if (Test-Path ".husky") {
  Get-ChildItem -Path .husky -File | ForEach-Object {
    Write-Host "Found hook: $($_.Name)"
  }
} else {
  Write-Host "⚠️ .husky folder not found."
}

Write-Host ""
Write-Host "✅ Setup complete! Verify with:"
Write-Host "  git add -A"
Write-Host "  git commit -m 'chore: verify hooks'  # commit-msg will validate"
Write-Host ""
Write-Host "Tip: If you face ExecutionPolicy issues, you can run PowerShell with -ExecutionPolicy Bypass as an admin."
Write-Host "This helps contributors on Windows get started quickly (and professionally)."
