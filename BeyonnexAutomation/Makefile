# Makefile

.PHONY: image
image:
	docker build -t beyonnexautomation:2.0 .

.PHONY: test
test:
	docker run -it --rm --network=beyonnexautomation_test-network beyonnexautomation:2.0 mvn test

.PHONY: start
start:
	docker-compose up -d

.PHONY: stop
stop:
	docker-compose down
