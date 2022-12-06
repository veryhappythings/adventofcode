package common

func Most(values []int) int {
    highest := 0
    for _, v := range values {
        if v > highest {
            highest = v
        }
    }
    return highest
}
