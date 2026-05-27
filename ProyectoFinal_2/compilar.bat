@echo off
echo Compilando proyecto...

if not exist out mkdir out

javac -encoding UTF-8 -d out ^
  modelo\TipoServicio.java ^
  modelo\EstadoSolicitud.java ^
  modelo\Zona.java ^
  modelo\taxi\Taxi.java ^
  modelo\taxi\TaxiEstandar.java ^
  modelo\taxi\TaxiMaletas.java ^
  modelo\taxi\TaxiMascotas.java ^
  modelo\Conductor.java ^
  modelo\Solicitud.java ^
  excepcion\ZonaInexistenteException.java ^
  excepcion\SinConectividadException.java ^
  excepcion\ConductorNoHabilitadoException.java ^
  excepcion\SinConductoresDisponiblesException.java ^
  excepcion\SolicitudInvalidaException.java ^
  excepcion\CancelacionInvalidaException.java ^
  patron\TaxiCreador.java ^
  patron\TaxiEstandarCreador.java ^
  patron\TaxiMaletasCreador.java ^
  patron\TaxiMascotasCreador.java ^
  estructura\NodoSolicitud.java ^
  estructura\ColaSolicitudes.java ^
  estructura\NodoHistorial.java ^
  estructura\PilaHistorial.java ^
  estructura\NodoConductor.java ^
  estructura\ListaConductores.java ^
  estructura\Arista.java ^
  estructura\GrafoCiudad.java ^
  servicio\GestorConductores.java ^
  servicio\GestorSolicitudes.java ^
  servicio\AsignadorServicio.java ^
  persistencia\IPersistencia.java ^
  persistencia\PersistenciaConductores.java ^
  persistencia\PersistenciaSolicitudes.java ^
  consola\MenuPrincipal.java ^
  Taxi_Solicitud.java

if %ERRORLEVEL% == 0 (
    echo.
    echo Compilacion exitosa. Ejecutando...
    echo.
    java -cp out Taxi_Solicitud
) else (
    echo.
    echo Error de compilacion.
)
pause
