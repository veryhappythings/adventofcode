package day1

import (
	"fmt"
	"io/ioutil"
	"strconv"
	"strings"
)

type elf []int

func (e *elf) Total() int {
	total := 0
	for _, v := range *e {
		total += v
	}
	return total
}

func most(values []int) int {
	highest := 0
	for _, v := range values {
		if v > highest {
			highest = v
		}
	}
	return highest
}

func Part1(filename string) {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		panic(err)
	}

	var elves []elf
	var e elf
	for _, value := range strings.Split(string(content), "\n") {
		if value == "" {
			elves = append(elves, e)
			e = []int{}
			continue
		}

		converted, err := strconv.Atoi(value)
		if err != nil {
			panic(err)
		}
		e = append(e, converted)
	}

	var totals []int
	for _, e := range elves {
		totals = append(totals, e.Total())
	}
	fmt.Println(most(totals))
}
