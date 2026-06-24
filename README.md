# Birthday Management System (MVP)

## Descripción del Proyecto
Plataforma web integral diseñada para la automatización y gestión logística de eventos de cumpleaños. El sistema permite a los clientes solicitar presupuestos automáticos mediante un motor de cálculo matemático, mientras que el administrador y el staff gestionan la logística, el inventario, la asignación de personal y los reportes operativos en tiempo real.

El proyecto se desarrolla bajo una arquitectura desacoplada, siguiendo el estándar de **Vistas 4+1 de Kruchten**, garantizando mantenibilidad y escalabilidad.

---

## 🚀 Funcionalidades Clave
- **Cotizador Automático:** Motor de cálculo lógico basado en cantidad de niños, tipo de fiesta y extras logísticos.
- **Gestión de Eventos:** Flujo completo desde la solicitud, aprobación, hasta la ejecución (Minuta y Menú).
- **Control de Inventario:** Gestión de insumos con alertas automáticas ante stock crítico.
- **Gestión de Staff:** Asignación inteligente de personal según disponibilidad y rol.
- **Bitácora y Agenda:** Seguimiento histórico de eventos y control de agenda operativa.

---

## 🛠 Arquitectura del Sistema
El sistema se divide en cuatro capas lógicas para asegurar la separación de responsabilidades:

1.  **Capa de Presentación:** React + TypeScript (Frontend).
2.  **Capa de Lógica de Negocio:** Microservicios con Spring Boot (Java).
3.  **Capa de Persistencia:** JPA / Hibernate + PostgreSQL.
4.  **Capa de Infraestructura:** Docker, API Gateway (Eureka), y servicios externos (SMTP/Cloud Storage).

---

## 📅 Roadmap del Proyecto (MVP - 14 Semanas / 4 Sprints)

| Sprint | Duración | Foco Principal | Estado |
| :--- | :--- | :--- | :--- |
| **S1** | Semanas 1-4 | Análisis, Requerimientos y Modelamiento (UML/MER) | ✅ Terminado |
| **S2** | Semanas 5-8 | Core: Autenticación, Usuarios y Clientes | 🔄 En Progreso |
| **S3** | Semanas 9-11 | Módulos Operativos: Inventario, Staff, Eventos | ⏳ Planificado |
| **S4** | Semanas 12-14 | Pruebas, Reportes y Despliegue final | ⏳ Planificado |

---

## 📄 Documentación Técnica
El modelamiento del sistema se encuentra bajo estándares UML 2.0 y está disponible para revisión:
* **Modelo Entidad-Relación (MER):** Normalizado a 3FN.
* **Diagrama de Componentes:** Arquitectura en capas (Presentación, Lógica, Datos).
* **Diagramas de Actividad:** Procesos de Cotización, Enrolamiento y Logística.

---

## ⚙️ Tecnologías Utilizadas
- **Frontend:** React, TypeScript, Vite, Tailwind CSS.
- **Backend:** Java 17+, Spring Boot, JWT Security.
- **Base de Datos:** PostgreSQL.
- **Gestión:** Kanban (Trello), Git, Swagger (Documentación API).

---

## 👤 Equipo de Desarrollo
* [Tu Nombre/Equipo]
* Proyecto de Titulación - 2026
