package day4

import (
	"adventofcode/internal/common"
	"bufio"
	"fmt"
	"os"
	"regexp"
)

type assignment struct {
	start int
	end   int
}

func Part1(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	total := 0
	re := regexp.MustCompile(`(\d+)-(\d+),(\d+)-(\d+)`)
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		text := scanner.Text()
		match := re.FindStringSubmatch(text)
		first := assignment{common.MustInt(match[1]), common.MustInt(match[2])}
		second := assignment{common.MustInt(match[3]), common.MustInt(match[4])}

		if first.start <= second.start && first.end >= second.end {
			total += 1
		} else if second.start <= first.start && second.end >= first.end {
			total += 1
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

	total := 0
	re := regexp.MustCompile(`(\d+)-(\d+),(\d+)-(\d+)`)
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		text := scanner.Text()
		match := re.FindStringSubmatch(text)
		first := assignment{common.MustInt(match[1]), common.MustInt(match[2])}
		second := assignment{common.MustInt(match[3]), common.MustInt(match[4])}

		if second.end < first.start || first.end < second.start {
			continue
		} else {
			total += 1
		}
	}
	fmt.Println(total)
}
