# prueba-tecnica-gatling-qa-adsg
Repositorio de prueba tecnica Gatling QA Andres Suarez
Para ejecuatr los test

mvn gatling:test -Dgatling.simulationClass=simulation.RegistroUsuario
mvn gatling:test -Dgatling.simulationClass=simulation.ConsultaMasiva

- Para ejecutar los test: ./mvnw gatling:test
- Para instalar dependencias: ./mvnw install
- Para limpiar el proyecto: ./mvnw clean
