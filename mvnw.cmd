@echo off
setlocal

set "WRAPPER_DIR=%~dp0.mvn\wrapper"

for /f "usebackq tokens=1,* delims==" %%A in ("%WRAPPER_DIR%\maven-wrapper.properties") do (
    if "%%A"=="distributionUrl" set DISTRIBUTION_URL=%%B
    if "%%A"=="distributionPath" set DISTRIBUTION_PATH=%%B
)

if not defined DISTRIBUTION_URL (
    echo distributionUrl is missing from .mvn\wrapper\maven-wrapper.properties
    exit /b 1
)

if not defined DISTRIBUTION_PATH (
    echo distributionPath is missing from .mvn\wrapper\maven-wrapper.properties
    exit /b 1
)

set "MAVEN_HOME=%~dp0%DISTRIBUTION_PATH%"

if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
    echo Downloading Maven distribution...
    powershell -NoProfile -ExecutionPolicy Bypass -Command ^
        "$ErrorActionPreference='Stop';" ^
        "$distributionUrl='%DISTRIBUTION_URL%';" ^
        "$mavenHome='%MAVEN_HOME%';" ^
        "$zipPath=Join-Path '%WRAPPER_DIR%' 'apache-maven.zip';" ^
        "$extractPath=Join-Path '%WRAPPER_DIR%' 'extract';" ^
        "if (Test-Path $zipPath) { Remove-Item -LiteralPath $zipPath -Force };" ^
        "if (Test-Path $extractPath) { Remove-Item -LiteralPath $extractPath -Recurse -Force };" ^
        "Invoke-WebRequest -Uri $distributionUrl -OutFile $zipPath;" ^
        "Expand-Archive -LiteralPath $zipPath -DestinationPath $extractPath -Force;" ^
        "$root=Get-ChildItem -LiteralPath $extractPath | Select-Object -First 1;" ^
        "if (-not $root) { throw 'Unable to extract Maven distribution.' };" ^
        "if (Test-Path $mavenHome) { Remove-Item -LiteralPath $mavenHome -Recurse -Force };" ^
        "New-Item -ItemType Directory -Path (Split-Path -Parent $mavenHome) -Force | Out-Null;" ^
        "Move-Item -LiteralPath $root.FullName -Destination $mavenHome;" ^
        "Remove-Item -LiteralPath $zipPath -Force;" ^
        "Remove-Item -LiteralPath $extractPath -Recurse -Force;"

    if errorlevel 1 (
        echo Failed to download or extract Maven.
        exit /b 1
    )
)

call "%MAVEN_HOME%\bin\mvn.cmd" %*
exit /b %errorlevel%
