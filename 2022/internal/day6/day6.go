package day6

import (
	"fmt"
	"io/ioutil"
)

func count(values []byte, value byte) int {
	total := 0
	for _, v := range values {
		if v == value {
			total += 1
		}
	}
	return total
}

func Part1(filename string) {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		panic(err)
	}

	var buffer []byte
	for i, c := range content {
		buffer = append(buffer, c)
		// If we don't have 4 yet just keep going
		if len(buffer) < 4 {
			continue
		}
		// If we have 4 check for a match
		marker := true
		for _, b := range buffer {
			if count(buffer, b) > 1 {
				marker = false
				break
			}
		}
		if marker {
			fmt.Printf("%d, %c\n", i+1, buffer)
			break
		}
		// Prep for next loop by dropping the first char
		buffer = buffer[1:]
	}
}

func Part2(filename string) {

}
