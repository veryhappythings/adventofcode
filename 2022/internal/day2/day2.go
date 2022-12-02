package day2

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
)

type Shape int

const (
	Rock Shape = iota
	Paper
	Scissors
)

type Round struct {
	Villain Shape
	Hero    Shape
}

func (r *Round) Score() int {
	score := 0
	switch r.Hero {
	case Rock:
		score += 1
		switch r.Villain {
		case Rock:
			score += 3
		case Paper:
			score += 0
		case Scissors:
			score += 6
		}
	case Paper:
		score += 2
		switch r.Villain {
		case Rock:
			score += 6
		case Paper:
			score += 3
		case Scissors:
			score += 0
		}
	case Scissors:
		score += 3
		switch r.Villain {
		case Rock:
			score += 0
		case Paper:
			score += 6
		case Scissors:
			score += 3
		}
	}
	return score
}

func Part1(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	var tournament []Round
	re := regexp.MustCompile(`(\w) (\w)`)
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		text := scanner.Text()
		var round Round
		match := re.FindStringSubmatch(text)
		switch match[1] {
		case "A":
			round.Villain = Rock
		case "B":
			round.Villain = Paper
		case "C":
			round.Villain = Scissors
		default:
			panic(match[1])
		}
		switch match[2] {
		case "X":
			round.Hero = Rock
		case "Y":
			round.Hero = Paper
		case "Z":
			round.Hero = Scissors
		default:
			panic(match[2])
		}
		tournament = append(tournament, round)
	}

	total := 0
	for _, round := range tournament {
		total += round.Score()
	}
	fmt.Println(total)
}
