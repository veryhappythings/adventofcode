package day2

import (
	"adventofcode/internal/common"
	"bufio"
	"fmt"
	"os"
	"strings"
)

type Game struct {
	rounds []*Round
}

type Round struct {
	red   int
	green int
	blue  int
}

type Bag Round

func NewRound(roundStr string) *Round {
	var red, green, blue int
	roundStrs := strings.Split(strings.TrimSpace(roundStr), ",")
	for _, r := range roundStrs {
		parts := strings.SplitN(strings.TrimSpace(r), " ", 2)
		n := common.MustInt(parts[0])
		colour := parts[1]
		switch colour {
		case "red":
			red = n
		case "green":
			green = n
		case "blue":
			blue = n
		}
	}
	return &Round{red, green, blue}
}

func (r *Round) String() string {
	return fmt.Sprintf("%d,%d,%d", r.red, r.green, r.blue)
}

func (b *Bag) Power() int {
	return b.red * b.green * b.blue
}

func (g *Game) String() string {
	str := ""
	for _, r := range g.rounds {
		str += r.String() + ";"
	}
	return str
}

// Tolerates returns true if the game in question could be played with the given bag - i.e. the game never reveals more of a given colour than is in the bag.
func (g *Game) Tolerates(b Bag) bool {
	for _, r := range g.rounds {
		if r.red > b.red || r.green > b.green || r.blue > b.blue {
			return false
		}
	}
	return true
}

// Min returns the minimum number of each colour that could be in the bag to play the game.
func (g *Game) Min() Bag {
	min := Bag{red: 0, green: 0, blue: 0}
	for _, r := range g.rounds {
		if r.red > min.red {
			min.red = r.red
		}
		if r.green > min.green {
			min.green = r.green
		}
		if r.blue > min.blue {
			min.blue = r.blue
		}
	}
	return min
}

func Part1(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	games := make([]Game, 0)
	for scanner.Scan() {
		text := scanner.Text()
		gameStrs := strings.SplitN(text, ":", 2)
		roundsStrs := strings.Split(gameStrs[1], ";")
		game := Game{}
		for _, r := range roundsStrs {
			game.rounds = append(game.rounds, NewRound(r))
		}
		games = append(games, game)
	}

	total := 0
	for i, g := range games {
		if g.Tolerates(Bag{red: 12, green: 13, blue: 14}) {
			total += i + 1
		}
	}
	fmt.Println(total)
}

func Part2(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	games := make([]Game, 0)
	for scanner.Scan() {
		text := scanner.Text()
		gameStrs := strings.SplitN(text, ":", 2)
		roundsStrs := strings.Split(gameStrs[1], ";")
		game := Game{}
		for _, r := range roundsStrs {
			game.rounds = append(game.rounds, NewRound(r))
		}
		games = append(games, game)
	}

	total := 0
	for _, g := range games {
		min := g.Min()
		total += min.Power()
	}
	fmt.Println(total)

}
