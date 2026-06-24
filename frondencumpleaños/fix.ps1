Get-ChildItem -Path src/modules,src/app -Recurse -File -Include *.ts,*.tsx | ForEach-Object {
    $content = Get-Content $_.FullName
    $newContent = $content -replace "'\.\./interfaces'", "'../../interfaces'"
    Set-Content $_.FullName -Value $newContent
}
Get-ChildItem -Path src -Recurse -File -Include *.ts,*.tsx | ForEach-Object {
    $content = Get-Content $_.FullName
    $newContent = $content -replace "^import React from 'react';\s*$", ""
    $newContent = $newContent -replace "^import React,\s*", "import "
    Set-Content $_.FullName -Value $newContent
}
