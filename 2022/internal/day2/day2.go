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

var villianLookup map[string]Shape = map[string]Shape{"A": Rock, "B": Paper, "C": Scissors}
var heroLookup map[string]Shape = map[string]Shape{"X": Rock, "Y": Paper, "Z": Scissors}

// Part2HeroLookup can be used to look up first XYZ for lose/draw/win, then what the villian picked to choose our pick
var part2HeroLookup map[string]map[string]Shape = map[string]map[string]Shape{
	// Lose
	"X": map[string]Shape{"A": Scissors, "B": Rock, "C": Paper},
	// Draw
	"Y": map[string]Shape{"A": Rock, "B": Paper, "C": Scissors},
	// Win
	"Z": map[string]Shape{"A": Paper, "B": Scissors, "C": Rock},
}

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
		round.Villain = villianLookup[match[1]]
		round.Hero = heroLookup[match[2]]
		tournament = append(tournament, round)
	}

	total := 0
	for _, round := range tournament {
		total += round.Score()
	}
	fmt.Println(total)
}
func Part2(filename string) {
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
		round.Villain = villianLookup[match[1]]
		round.Hero = part2HeroLookup[match[2]][match[1]]
		tournament = append(tournament, round)
	}

	total := 0
	for _, round := range tournament {
		total += round.Score()
	}
	fmt.Println(total)
}
