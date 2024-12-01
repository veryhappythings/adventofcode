package day4

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
	"strings"
)

func Part1(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	whitespaceRe := regexp.MustCompile(`\s`)
	for scanner.Scan() {
		text := scanner.Text()
		card := strings.SplitN(text, ":", 2)[1]
		winnersStr, numbersStr := strings.SplitN(card, "|", 2)[0], strings.SplitN(card, "|", 2)[1]
		winners := whitespaceRe.Split(winnersStr, -1)
		numbers := whitespaceRe.Split(numbersStr, -1)
		fmt.Println(winners)
		fmt.Println(numbers)
	}
}

func Part2(filename string) {
}
