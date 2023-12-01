package day1

import (
	"regexp"
	"testing"
)

func TestFindAllOverlappingMatches(t *testing.T) {
	re := regexp.MustCompile(`(\d|one|two|three|four|five|six|seven|eight|nine)`)
	s := "15six44qndpslhnine8twonehkb"
	result := FindAllOverlappingMatches(re, s)
	expected := []string{"1", "5", "six", "4", "4", "nine", "8", "two", "one"}
	if len(result) != len(expected) {
		t.Errorf("Got %q, expected %q\n", result, expected)
	}
}
