#!/bin/bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Check if kotlin is available
if ! command -v kotlin &> /dev/null; then
    echo "Kotlin not found. Installing via SDKMAN..."

    # Check/install SDKMAN
    if [[ ! -f "$HOME/.sdkman/bin/sdkman-init.sh" ]]; then
        echo "Installing SDKMAN..."
        curl -s "https://get.sdkman.io" | bash
    fi

    # Source SDKMAN
    source "$HOME/.sdkman/bin/sdkman-init.sh"

    # Install Kotlin
    sdk install kotlin
fi

# Run the main-kts script
kotlin "$SCRIPT_DIR/init_template.main.kts"
