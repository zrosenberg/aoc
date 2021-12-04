import math


def day3(file):
    f = open(file, "r")
    lines = f.readlines()

    # we will use bitwise operations to build up a gamma
    # epsilon will be the inverse
    gamma = 0
    epsilon = 0

    # all lines are the same length, iterate through the columns
    # We are wasting some iterations but who cares. TODO make it not n^2
    for col in range(len(lines[0])):
        # probably some smart bit thing we can do here too
        # col total is the number of 1's in this column
        col_total = 0

        # A multiple of 2 will have a single 1 in binary.
        # When we AND that to any value, we isolate the value in that column.
        col_and_isolator = int(math.pow(2, col))

        # go through all the lines, and AND them with our column isolator bitmask.
        # then add the resulting digit to our counter; its either 0 or 1, so its a count of 1's.
        for line in lines:
            line = line.strip("\n")
            x = int(line, 2)
            digit = (x & col_and_isolator) / col_and_isolator
            print(bin(x))
            col_total += digit  # add either 0 or 1 to our column total

        # check the number of 1's received is greater than 0's for this column
        val = 0
        if col_total > len(lines) / 2:
            val += 1

        # again use our column isolator to insert the value into gamma
        gamma = (val * col_and_isolator) & col_and_isolator | gamma

    # epsilon is the complement of gamma. XOR gamma with a bitmask of all ones
    eps_mask = ''.join('1' for _ in range(len(lines[0]) - 1))
    epsilon = gamma ^ int(eps_mask, 2)

    print(f"Gamma value ", gamma)
    print(f"Epsilon value ", epsilon)
    print(f"Gamma X Epsilon ", gamma * epsilon)


def day3_part2(file):
    f = open(file, "r")
    lines = f.readlines()

    # we will use bitwise operations to build up a gamma
    # epsilon will be the inverse
    gamma = 0
    epsilon = 0

    # all lines are the same length, iterate through the columns
    # We are wasting some iterations but who cares. TODO make it not n^2
    for col in range(len(lines[0])):
        # probably some smart bit thing we can do here too
        # col total is the number of 1's in this column
        col_total = 0

        # A multiple of 2 will have a single 1 in binary.
        # When we AND that to any value, we isolate the value in that column.
        col_and_isolator = int(math.pow(2, col))

        # go through all the lines, and AND them with our column isolator bitmask.
        # then add the resulting digit to our counter; its either 0 or 1, so its a count of 1's.
        for line in lines:
            line = line.strip("\n")
            x = int(line, 2)
            digit = (x & col_and_isolator) / col_and_isolator
            print(bin(x))
            col_total += digit  # add either 0 or 1 to our column total

        # check the number of 1's received is greater than 0's for this column
        val = 0
        if col_total > len(lines) / 2:
            val += 1

        # again use our column isolator to insert the value into gamma
        gamma = (val * col_and_isolator) & col_and_isolator | gamma

    # epsilon is the complement of gamma. XOR gamma with a bitmask of all ones
    eps_mask = ''.join('1' for _ in range(len(lines[0]) - 1))
    epsilon = gamma ^ int(eps_mask, 2)

    print(f"Gamma value ", gamma)
    print(f"Epsilon value ", epsilon)
    print(f"Gamma X Epsilon ", gamma * epsilon)


if __name__ == '__main__':
    day3("day3.txt")