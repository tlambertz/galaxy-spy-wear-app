#!/bin/bash
set -e
cd "$(dirname "$0")"

echo "Decompiling watchmanager"
jadx -e -d watchmanager-jadx watchmanager-patch/app/apk/*

echo "Decompiling neobeanmgr"
jadx -e -d neobeanmgr-jadx neobeanmgr-patch/app/apk/*