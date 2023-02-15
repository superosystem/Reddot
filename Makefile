.PHONY: docker-dev-up
docker-dev-up:
	@echo "Docker Compose Dev Running..."
	docker compose -f docker-compose-dev.yml up

.PHONY: docker-dev-down
docker-dev-down:
	@echo "Docker Compose Dev Down..."
	docker compose -f docker-compose-dev.yml down

.PHONY: run-dev
run-dev:
	@echo "Starting Application..."
	gradle bootRun

.PHONY: migrate-up
migrate-up:
	@echo "Database Migration Up..."
	flyway migrate

.PHONY: migrate-clean
migrate-clean:
	@echo "Database Migration Clean Up..."
	flyway clean