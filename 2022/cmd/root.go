package cmd

import (
	"adventofcode/internal/day1"
	"adventofcode/internal/day2"
	"adventofcode/internal/day3"
	"adventofcode/internal/day4"
	"adventofcode/internal/day5"
	"adventofcode/internal/day6"
	"fmt"
	"github.com/spf13/cobra"
	"os"
)

var day int
var part int
var inputFilename string

var days [][]func(string) = [][]func(string){
	{day1.Part1, day1.Part2},
	{day2.Part1, day2.Part2},
	{day3.Part1, day3.Part2},
	{day4.Part1, day4.Part2},
	{day5.Part1, day5.Part2},
	{day6.Part1, day6.Part2},
}

var rootCmd = &cobra.Command{
	Use:   "adventofcode",
	Short: "Advent of Code 2022!",
	Run: func(cmd *cobra.Command, args []string) {
		days[day-1][part-1](inputFilename)
	},
}

func init() {
	rootCmd.Flags().IntVarP(&day, "day", "d", 0, "Which day")
	rootCmd.Flags().IntVarP(&part, "part", "p", 0, "Which part")
	rootCmd.Flags().StringVarP(&inputFilename, "input", "f", "", "Which input filename")
}

func Execute() {
	if err := rootCmd.Execute(); err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}
