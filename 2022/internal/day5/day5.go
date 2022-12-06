package day5

import (
	"adventofcode/internal/common"
	"bufio"
	"fmt"
	"os"
	"regexp"
	"strings"
)

type stack []byte

type instruction struct {
	quantity    int
	source      int
	destination int
}

func clamp(value int, min int, max int) int {
	if value < min {
		return min
	}
	if value > max {
		return max
	}
	return value
}

func insert(source []byte, index int, value byte) []byte {
	if len(source) == index {
		return append(source, value)
	}
	source = append(source[:index+1], source[index:]...)
	source[index] = value
	return source
}

func load(filename string) ([]stack, []instruction) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	var stacks [10]stack
	var instructions []instruction
	numbersRegex := regexp.MustCompile(`\s*\d+`)
	instructionRegex := regexp.MustCompile(`move (\d+) from (\d+) to (\d+)`)
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		text := scanner.Text()

		// Ignore the whitespace
		if len(text) == 0 {
			continue
		}

		if instructionRegex.MatchString(text) {
			match := instructionRegex.FindStringSubmatch(text)
			instructions = append(instructions, instruction{
				quantity:    common.MustInt(match[1]),
				source:      common.MustInt(match[2]),
				destination: common.MustInt(match[3]),
			})
		} else {
			// We can ignore the list of numbers
			if numbersRegex.MatchString(text) {
				continue
			}
			for i := 1; len(text) > 0; i++ {
				// pop the next box from the line
				box := strings.Trim(text[:3], " ")
				text = text[clamp(len(text), 3, 4):]
				fmt.Printf("Found box %s for stack %d\n", box, i)
				if len(box) == 0 {
					continue
				}
				// If it's a box, stick it on a stack
				stacks[i] = append(stacks[i], box[1])
			}
		}
	}
}

func Part1(filename string) {
	stacks, instructions := load(filename)
	fmt.Printf("%c\n", stacks)

	for _, inst := range instructions {
		for i := 0; i < inst.quantity; i++ {
			value := stacks[inst.source][0]
			stacks[inst.source] = stacks[inst.source][1:]
			stacks[inst.destination] = insert(stacks[inst.destination], 0, value)
		}
	}
	fmt.Printf("%c\n", stacks)
	for _, s := range stacks {
		if len(s) > 0 {
			fmt.Printf("%c", s[0])
		}
	}
	fmt.Println()
}

func Part2(filename string) {

}
