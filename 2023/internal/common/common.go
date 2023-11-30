package common

import "strconv"

func Most(values []int) int {
	highest := 0
	for _, v := range values {
		if v > highest {
			highest = v
		}
	}
	return highest
}

func MustInt(in string) int {
	out, err := strconv.Atoi(in)
	if err != nil {
		panic(err)
	}
	return out
}
