# Sistema de Gestión PBA

Proyecto universitario desarrollado con OpenXava, JPA/Hibernate, Maven y MySQL.
Permite registrar usuarios, productos, insumos, proveedores, clientes, pedidos y
atenciones de postventa.

## Tecnologías usadas

- Java 8 o superior
- OpenXava 7.7.2
- Maven
- MySQL 8
- JUnit 5
- NetBeans

## Preparar la base de datos

1. Iniciar MySQL.
2. Abrir MySQL Workbench o la consola de MySQL.
3. Ejecutar el archivo `pba_db.sql` que está en la raíz del proyecto.
4. El script crea la base de datos `pba_db`, sus tablas y algunos datos de
   ejemplo.

Los productos, proveedores y clientes del script son datos inventados para
probar el sistema. Antes de una entrega real deben reemplazarse por datos
autorizados de PBA EIRL.

## Configurar usuario y contraseña

Revisar estos dos archivos:

- `src/main/resources/META-INF/persistence.xml`
- `src/main/webapp/META-INF/context.xml`

Por defecto se usa el usuario `root` sin contraseña. Si MySQL tiene otra
configuración, cambiar el usuario y la contraseña en ambos archivos.

## Abrir y ejecutar en NetBeans

1. En NetBeans seleccionar **File > Open Project**.
2. Elegir esta carpeta como proyecto Maven.
3. Esperar que Maven descargue las dependencias.
4. Ejecutar **Clean and Build**.
5. Ejecutar la clase:
   `com.pba.pbasistem.run.pbasistem`.
6. Abrir `http://localhost:8080/pbasistem`.

El usuario inicial de acceso es `admin` y la contraseña es `admin`. Esta cuenta
pertenece al inicio de sesión de OpenXava. La entidad `Usuario` sirve para los
usuarios de negocio solicitados en el trabajo.

También se puede compilar desde una terminal:

```bash
mvn clean package
```

## Ejecutar las pruebas

```bash
mvn test
```

Las pruebas están separadas por entidad. Son pruebas unitarias sencillas y no
modifican la base de datos real.

## Reportes

En el menú se muestran los módulos:

- **Pedidos por estado**
- **Productos más vendidos**
- **Reclamos pendientes y resueltos**

Las listas pueden filtrarse por estado y exportarse o imprimirse con las
acciones estándar de OpenXava. En `Pedido` también está la vista `Factura`, que
muestra el cliente, los detalles y el total.

## Organización del código

Las entidades están juntas en:

```text
src/main/java/com/pba/pbasistem/model
```

Se dejó un solo paquete de modelo para mantener el proyecto simple.
