$src = "c:\Users\bb205\OneDrive\Desktop\backendcumple\src\main\java\com\micumpleanos\eventos"
$destBase = "c:\Users\bb205\OneDrive\Desktop\backendcumple"

function Move-Domain {
    param($domain, $service, $pkg)
    $dest = "$destBase\$service\src\main\java\com\micumpleanos\$pkg"
    
    Get-ChildItem -Path $src -Recurse -Filter "*$domain*" | ForEach-Object {
        $relPath = $_.FullName.Substring($src.Length + 1)
        $target = "$dest\$relPath"
        $parent = Split-Path $target
        if (!(Test-Path $parent)) { New-Item -ItemType Directory -Force -Path $parent | Out-Null }
        Copy-Item -Path $_.FullName -Destination $target -Force
    }
}

Move-Domain -domain "Cliente" -service "cliente-service" -pkg "cliente"

Move-Domain -domain "Evento" -service "evento-service" -pkg "evento"
Move-Domain -domain "Minuta" -service "evento-service" -pkg "evento"
Move-Domain -domain "Menu" -service "evento-service" -pkg "evento"
Move-Domain -domain "Agenda" -service "evento-service" -pkg "evento"
Move-Domain -domain "Bitacora" -service "evento-service" -pkg "evento"
Move-Domain -domain "TipoFiesta" -service "evento-service" -pkg "evento"

Move-Domain -domain "Personal" -service "personal-service" -pkg "personal"
Move-Domain -domain "RolPersonal" -service "personal-service" -pkg "personal"
Move-Domain -domain "Asignacion" -service "personal-service" -pkg "personal"

Move-Domain -domain "Usuario" -service "auth-service" -pkg "auth"
Move-Domain -domain "RolSistema" -service "auth-service" -pkg "auth"

$services = @(
    @("cliente-service", "cliente", "Cliente"),
    @("evento-service", "evento", "Evento"),
    @("personal-service", "personal", "Personal"),
    @("auth-service", "auth", "Auth")
)

foreach ($s in $services) {
    $srv = $s[0]
    $pkg = $s[1]
    $cap = $s[2]
    
    $dest = "$destBase\$srv\src\main\java\com\micumpleanos\$pkg"
    
    # Copy shared files
    $sharedDest = "$dest\shared"
    if (!(Test-Path $sharedDest)) { New-Item -ItemType Directory -Force -Path $sharedDest | Out-Null }
    Copy-Item -Path "$src\shared\*" -Destination $sharedDest -Recurse -Force
    
    $configDest = "$dest\config"
    if (!(Test-Path $configDest)) { New-Item -ItemType Directory -Force -Path $configDest | Out-Null }
    Copy-Item -Path "$src\config\*" -Destination $configDest -Recurse -Force

    $entityDest = "$dest\domain\entity"
    if (!(Test-Path $entityDest)) { New-Item -ItemType Directory -Force -Path $entityDest | Out-Null }
    Copy-Item -Path "$src\domain\entity\BaseEntity.java" -Destination "$entityDest\BaseEntity.java" -Force

    # Copy application classes
    Copy-Item -Path "$src\EventosInfantilesApplication.java" -Destination "$dest\${cap}ServiceApplication.java" -Force
    
    # Update packages
    Get-ChildItem -Path $dest -Recurse -Filter "*.java" | ForEach-Object {
        $content = Get-Content $_.FullName
        $content = $content -replace "package com.micumpleanos.eventos", "package com.micumpleanos.$pkg"
        $content = $content -replace "import com.micumpleanos.eventos", "import com.micumpleanos.$pkg"
        $content = $content -replace "EventosInfantilesApplication", "${cap}ServiceApplication"
        Set-Content -Path $_.FullName -Value $content
    }
}
