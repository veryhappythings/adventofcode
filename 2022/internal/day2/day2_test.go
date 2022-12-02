package day2

import "testing"

func TestScore(t *testing.T) {
	cases := []struct {
		villain  Shape
		hero     Shape
		expected int
	}{
		{Rock, Rock, 4},
		{Rock, Paper, 8},
		{Rock, Scissors, 3},
		{Paper, Rock, 1},
		{Paper, Paper, 5},
		{Paper, Scissors, 9},
		{Scissors, Rock, 7},
		{Scissors, Paper, 2},
		{Scissors, Scissors, 6},
	}

	for _, tc := range cases {
		t.Run("case", func(t *testing.T) {
			round := Round{tc.villain, tc.hero}
			score := round.Score()
			if score != tc.expected {
				t.Errorf("Expected %d, got %d", tc.expected, score)
			}
		})
	}
}
