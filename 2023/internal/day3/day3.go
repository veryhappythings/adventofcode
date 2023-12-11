package day3

import (
	"adventofcode/internal/common"
	"bufio"
	"fmt"
	"os"
	"regexp"
)

type Point struct {
	X int
	Y int
}

type Number struct {
	Start Point
	End   Point
	Value int
}

type Symbol struct {
	Location Point
	Value    rune
}

// Adjacent returns true if the given symbol is adjacent to the number. Diaganols count.
func (n *Number) Adjacent(s Symbol) bool {
	// Symbol is too far left of the number
	if s.Location.X < n.Start.X-1 {
		return false
	}
	// Symbol is too far right of the number
	if s.Location.X > n.End.X+1 {
		return false
	}
	// Symbol is too far above the number
	if s.Location.Y < n.Start.Y-1 {
		return false
	}
	// Symbol is too far below the number
	if s.Location.Y > n.End.Y+1 {
		return false
	}
	return true
}

func Part1(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	numRe := regexp.MustCompile(`\d`)
	numbers := make([]Number, 0)
	symbols := make([]Symbol, 0)
	y := 0
	for scanner.Scan() {
		// Grimy hack to avoid the edge - make it always not quite the edge by adding an extra character
		text := scanner.Text() + "."
		buffer := make([]rune, 0)
		start := 0
		for x, c := range text {
			// If it's a number, we add it to the buffer
			if numRe.MatchString(string(c)) {
				if len(buffer) == 0 {
					start = x
				}
				buffer = append(buffer, c)
				// If it's an empty space (.), wrap up our current number if we have one
			} else {
				if len(buffer) > 0 {
					numbers = append(numbers, Number{
						Start: Point{X: start, Y: y},
						End:   Point{X: x - 1, Y: y},
						Value: common.MustInt(string(buffer)),
					})
					// Count all other spaces as symbols
				}
				buffer = make([]rune, 0)
				if c != '.' {
					symbols = append(symbols, Symbol{
						Location: Point{X: x, Y: y},
						Value:    c,
					})
				}
			}
		}
		y += 1
	}
	// for _, n := range numbers {
	// 	fmt.Println(n.Value)
	// }
	// for _, n := range symbols {
	// 	fmt.Println(n, string(n.Value))
	// }

	// fmt.Println("---------")
	// fmt.Println("---------")
	// fmt.Println("---------")
	// fmt.Println("---------")

	total := 0
	for _, n := range numbers {
		matched := false
		for _, s := range symbols {
			if n.Adjacent(s) {
				fmt.Println(n.Value, string(s.Value))
				total += n.Value
				matched = true
				break
			}
		}
		if !matched {
			fmt.Printf("No match for %d\n", n.Value)
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
	numRe := regexp.MustCompile(`\d`)
	numbers := make([]Number, 0)
	symbols := make([]Symbol, 0)
	y := 0
	for scanner.Scan() {
		// Grimy hack to avoid the edge - make it always not quite the edge by adding an extra character
		text := scanner.Text() + "."
		buffer := make([]rune, 0)
		start := 0
		for x, c := range text {
			// If it's a number, we add it to the buffer
			if numRe.MatchString(string(c)) {
				if len(buffer) == 0 {
					start = x
				}
				buffer = append(buffer, c)
				// If it's an empty space (.), wrap up our current number if we have one
			} else {
				if len(buffer) > 0 {
					numbers = append(numbers, Number{
						Start: Point{X: start, Y: y},
						End:   Point{X: x - 1, Y: y},
						Value: common.MustInt(string(buffer)),
					})
					// Count all other spaces as symbols
				}
				buffer = make([]rune, 0)
				if c != '.' {
					symbols = append(symbols, Symbol{
						Location: Point{X: x, Y: y},
						Value:    c,
					})
				}
			}
		}
		y += 1
	}

	total := 0
	for _, s := range symbols {
		if s.Value == '*' {
			var adjacent []Number
			for _, n := range numbers {
				if n.Adjacent(s) {
					adjacent = append(adjacent, n)
				}
			}
			if len(adjacent) == 2 {
				total += adjacent[0].Value * adjacent[1].Value
			}
		}
	}
	fmt.Println(total)
}
