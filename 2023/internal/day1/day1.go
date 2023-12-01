package day1

import (
	"adventofcode/internal/common"
	"bufio"
	"fmt"
	"os"
	"regexp"
	"strconv"
)

func Part1(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	re := regexp.MustCompile(`(\d)`)
	total := 0
	fmt.Println("go")
	for scanner.Scan() {
		text := scanner.Text()
		fmt.Println(text)
		matches := re.FindAll([]byte(text), -1)
		first := string(matches[0])
		second := string(matches[len(matches)-1])
		fmt.Println(first)
		fmt.Println(second)
		total += common.MustInt(fmt.Sprintf("%s%s", first, second))
	}
	fmt.Println(total)

}

func atoi(a string) int {
	result, err := strconv.Atoi(a)
	if err != nil {
		switch a {
		case "one":
			return 1
		case "two":
			return 2
		case "three":
			return 3
		case "four":
			return 4
		case "five":
			return 5
		case "six":
			return 6
		case "seven":
			return 7
		case "eight":
			return 8
		case "nine":
			return 9
		}
	}
	return result
}

func FindAllOverlappingMatches(re *regexp.Regexp, s string) []string {
	var result []string
	for len(s) > 0 {
		loc := re.FindStringIndex(s)
		if loc == nil {
			break
		}
		result = append(result, s[loc[0]:loc[1]])
		s = s[loc[0]+1:]
	}
	return result
}

func Part2(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	re := regexp.MustCompile(`(\d|one|two|three|four|five|six|seven|eight|nine)`)
	total := 0
	for scanner.Scan() {
		text := scanner.Text()
		matches := FindAllOverlappingMatches(re, text)
		first := atoi(matches[0])
		second := atoi(matches[len(matches)-1])
		total += common.MustInt(fmt.Sprintf("%d%d", first, second))
	}
	fmt.Println(total)

}
