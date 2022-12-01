.PHONY=test aoc

aoc:
	go build -o bin/aoc -v main.go

test: build-proto
	go test -v ./...
