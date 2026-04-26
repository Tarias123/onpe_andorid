# ONPE Android

Aplicación Android nativa que replica el sistema de visualización de resultados electorales de la ONPE (Oficina Nacional de Procesos Electorales), basada en los datos de la segunda vuelta presidencial 2016.

## Stack tecnológico

- **Lenguaje:** Kotlin
- **UI:** XML Views (no Jetpack Compose)
- **Arquitectura:** Fragments + ViewPager2 + BottomNavigationView
- **Base de datos:** Firebase Firestore (tiempo real)
- **Min SDK:** API 36

## Estructura del proyecto

```
app/src/main/
├── java/com/onpe/app/
│   ├── SplashActivity.kt
│   ├── MainActivity.kt
│   ├── adapter/
│   │   ├── CandidatoDesgloseAdapter.kt
│   │   └── CandidatoPresAdapter.kt
│   ├── data/
│   │   ├── Models.kt              # Data classes: Candidato, RegionalStats
│   │   ├── MockData.kt            # Datos locales de fallback
│   │   └── FirestoreService.kt    # Listeners en tiempo real con Firestore
│   └── ui/
│       ├── presidencial/
│       │   ├── PresidencialFragment.kt
│       │   ├── ResumenGeneralFragment.kt
│       │   ├── ResultadosPresidencialesFragment.kt
│       │   ├── ResultadoPorTipoFragment.kt
│       │   └── FullTableBottomSheet.kt
│       ├── actas/
│       │   └── ActasFragment.kt
│       └── participacion/
│           └── ParticipacionFragment.kt
└── res/
    ├── layout/      # 14 layouts XML
    ├── drawable/    # Imágenes de candidatos y recursos vectoriales
    ├── values/      # Colores, temas, strings
    └── menu/        # Menú de navegación inferior
```

## Funcionalidades

- **Resultados presidenciales** con desglose por candidato (votos, porcentaje)
- **Avance de escrutinio** (actas totales / procesadas / contabilizadas)
- **Participación ciudadana** (electores hábiles, participantes, ausentismo)
- **Resultado por tipo de voto** (válidos, blancos, nulos con barras de progreso)
- **Toggle Perú / Extranjero** en todas las secciones
- **Vista completa en Bottom Sheet** con tabla detallada
- **Búsqueda de acta** por número de 6 dígitos
- **Datos en tiempo real** vía Firestore `addSnapshotListener`, con mock data como fallback inmediato

## Firestore — estructura esperada

```
firestore/
├── stats/
│   ├── peru          # { totalActas, procesadas, contabilizadas, electoresHabiles,
│   │                 #   participantes, porcentajeFinal, ausentismo, votosValidos,
│   │                 #   pctValidos, votosBlancos, pctBlancos, votosNulos,
│   │                 #   pctNulos, totalEmitidos }
│   └── extranjero    # misma estructura
└── candidatos/
    ├── ppk           # { nombre, partido, votos, porcentaje, porcentajeEmitidos,
    │                 #   ambito: "peru", orden: 1 }
    ├── keiko         # { ambito: "peru", orden: 2, ... }
    ├── ppk_ext       # { ambito: "extranjero", orden: 1, ... }
    └── keiko_ext     # { ambito: "extranjero", orden: 2, ... }
```

## Instalación

### Prerrequisitos

- Android Studio Hedgehog o superior
- JDK 11+
- Cuenta de Firebase con proyecto configurado

### Pasos

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Tarias123/onpe_andorid.git
   ```

2. Abrir el proyecto en Android Studio (`File → Open` y seleccionar la carpeta raíz).

3. El archivo `google-services.json` ya está incluido en `app/`. Si se desea conectar a un proyecto Firebase propio, reemplazarlo con el correspondiente desde la consola de Firebase.

4. Sincronizar Gradle:
   ```
   File → Sync Project with Gradle Files
   ```

5. Ejecutar en emulador o dispositivo físico:
   ```
   Run → Run 'app'
   ```

> **Nota:** La app carga datos mock locales de forma inmediata. Los datos reales desde Firestore se aplican en segundo plano una vez establecida la conexión.
