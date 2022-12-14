package day3

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

const pos string = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

func Part1(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	total := 0
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		text := scanner.Text()
		first := text[:len(text)/2]
		second := text[len(text)/2:]
		fmt.Printf("%s -> %s, %s\n", text, first, second)

		if len(first) != len(second) {
			panic("Backpack sizes not even")
		}

		for _, r := range first {
			if strings.ContainsRune(second, r) {
				total += strings.IndexRune(pos, r)
				break
			}
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
	var currentGroup []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		text := scanner.Text()
		currentGroup = append(currentGroup, text)

		if len(currentGroup) != 3 {
			continue
		}

		for _, r := range currentGroup[0] {
			if strings.ContainsRune(currentGroup[1], r) && strings.ContainsRune(currentGroup[2], r) {
				total += strings.IndexRune(pos, r)
				break
			}
		}
		currentGroup = []string{}
	}
	fmt.Println(total)
}
