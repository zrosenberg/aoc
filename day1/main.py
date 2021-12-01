def day_1():
    # const
    f = open("day1.txt", "r")
    lines = f.readlines()
    last = None  # using none for the first is pretty hack
    increased_count = 0

    for i in lines:
        if last is None:
            last = i
            continue
        if int(last) < int(i):
            increased_count += 1
        last = i
    print(increased_count)


# This solution works if we can fit all values in memory, and with this dataset I know we can
# if it was huge, I would consider a moving window class and push/pop values out of the window itself.
# maybe its easier to not even keep track of the values and just add them up everytime, while we are indexing.
def day_1_part2():
    f = open("day1.txt", "r")
    lines = f.readlines()
    window_a = 0
    window_b = 0
    increased_count = 0

    # Same iteration, but instead of the lines we iterate over index so we can index values to add/sub from windows
    for i in range(len(lines)):
        if i == 0:  # This gets our two windows offset from each other
            window_a += int(lines[i])
        else:
            window_b += int(lines[i])
            # compare
            # after 3 iterations, both windows will have 3 entries.
            # since A had a head start, we add 1 to b to get it to 3 entries.
            # Then we compare, and after that subtract 1 from each.
            # That brings them both to 2 entries, then we add the current value to A for the next iteration.
            if i > 2:
                if window_b > window_a:
                    increased_count += 1

                window_a -= int(lines[i - 3])
                window_b -= int(lines[i - 2])
            window_a += int(lines[i])
    print(increased_count)


if __name__ == '__main__':
    day_1()
    day_1_part2()

