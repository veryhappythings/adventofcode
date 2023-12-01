package day7

import (
	"adventofcode/internal/common"
	"bufio"
	"fmt"
	"os"
	"regexp"
	"strings"
)

type node struct {
	name     string
	parent   *node
	size     int
	subnodes []*node
}

func (n *node) Size() int {
	if len(n.subnodes) == 0 {
		return n.size
	} else {
		total := 0
		for _, node := range n.subnodes {
			total += node.Size()
		}
		return total
	}
}

func (n *node) String() string {
	result := fmt.Sprintf("- %s", n.name)
	for _, child := range n.subnodes {
		result += fmt.Sprintf("\n- %d %s", child.size, child.name)
	}
	result += "\n"
	return result
}

func Part1(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	current := &node{}
	root := current
	cmdRe := regexp.MustCompile(`\$ (\w+)( [\S]+)?`)
	lsRe := regexp.MustCompile(`(\S+) (\S+)`)
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		text := scanner.Text()
		fmt.Println(text)
		if cmdRe.MatchString(text) {
			match := cmdRe.FindStringSubmatch(text)
			command := match[1]
			arg := strings.Trim(match[2], " ")

			if command == "cd" {
				if arg == "/" {
					// Start fresh
					current = &node{name: "/"}
					root = current
				} else if arg == ".." {
					current = current.parent
				} else {
					for _, n := range current.subnodes {
						if n.name == arg {
							current = n
							break
						}
					}
					// Failure case is missing here
				}
			} else if command == "ls" {
				// Do nothing here because our next scans will pick it up
			} else {
				panic("huh")
			}
		} else {
			// ls output case
			match := lsRe.FindStringSubmatch(text)
			size := 0
			if match[1] != "dir" {
				size = common.MustInt(match[1])
			}
			name := match[2]
			n := node{name: name, size: size, parent: current}
			current.subnodes = append(current.subnodes, &n)
		}
	}

	fmt.Println(root)
}

func Part2(filename string) {

}
