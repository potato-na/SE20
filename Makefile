.PHONY: build
build:
	docker-compose up -d --build

.PHONY: compile
compile:
	docker-compose run kt kotlinc Sample.kt Checker.kt
	
.PHONY: run 
run:
	docker-compose run kt kotlin SampleKt

.PHONY: down
down:
	docker-compose down
