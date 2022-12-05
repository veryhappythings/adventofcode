package day4

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
	"strconv"
)

type assignment struct {
	start int
	end   int
}

func mustInt(in string) int {
	out, err := strconv.Atoi(in)
	if err != nil {
		panic(err)
	}
	return out
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
		first := assignment{mustInt(match[1]), mustInt(match[2])}
		second := assignment{mustInt(match[3]), mustInt(match[4])}

		if first.start <= second.start && first.end >= second.end {
			total += 1
		} else if second.start <= first.start && second.end >= first.end {
			total += 1
		}
	}
	fmt.Println(total)
}
func Part2(filename string) {

}
