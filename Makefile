SERVICE_NAME := kt
IMPORT_TEST := kotlin-test.jar

.PHONY: up
up:
	docker-compose up -d --build

.PHONY: compile/Sample
compile/Sample:
	docker-compose run ${SERVICE_NAME} kotlinc src/Sample.kt src/Checker.kt -include-runtime -d src/Sample.jar
	
.PHONY: run/Sample
run/Sample:
	docker-compose run ${SERVICE_NAME} kotlin -cp src/Sample.jar SampleKt

.PHONY: compile/CheckerTest
compile/CheckerTest:
	docker-compose run ${SERVICE_NAME} kotlinc -cp lib/${IMPORT_TEST} src/CheckerTest.kt src/Checker.kt -include-runtime -d src/CheckerTest.jar
	
.PHONY: run/CheckerTest
run/CheckerTest:
	docker-compose run ${SERVICE_NAME} kotlin -cp lib/${IMPORT_TEST}:src/CheckerTest.jar CheckerTestKt

.PHONY: down
down:
	docker-compose down

