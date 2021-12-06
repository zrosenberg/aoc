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

    # sanitize input
    raw_lines = f.readlines()
    lines = []
    for line in raw_lines:
        clean = line.strip("\n")
        lines.append(clean)

    # we will use bitwise operations to build up a gamma
    # epsilon will be the inverse
    gamma = 0

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
            x = int(line, 2)
            digit = (x & col_and_isolator) / col_and_isolator
            col_total += digit  # add either 0 or 1 to our column total

        # check the number of 1's received is greater than 0's for this column
        val = 0
        if col_total >= len(lines) / 2:
            val += 1

        # again use our column isolator to insert the value into gamma
        gamma = (val * col_and_isolator) & col_and_isolator | gamma

    # epsilon is the complement of gamma. XOR gamma with a bitmask of all ones
    eps_mask = ''.join('1' for _ in range(len(lines[0])))
    epsilon = gamma ^ int(eps_mask, 2)

    print(eps_mask)
    print(f"Gamma value ", bin(gamma))
    print(f"Epsilon value ", bin(epsilon))
    print(f"Gamma X Epsilon ", gamma * epsilon)

    starting_digit = len(lines[0]) - 1
    oxygen = find_match(lines, starting_digit, True)
    co2 = find_match(lines, starting_digit, False)
    print(f"oxygen ", oxygen)
    print(f"co2 ", co2)
    print(f"oxygen X co2 {int(oxygen, 2) * int(co2, 2)}")


def find_match(str_list, digit_index, one_bias):
    # Recursion end condition: we've found the closest match
    if len(str_list) == 1:
        return str_list[0]

    one_this_digit = []
    zero_this_digit = []
    ones = 0
    for line in str_list:
        column_mask = int(math.pow(2, digit_index))
        sample_digit_value = (int(line, 2) & column_mask) / column_mask

        if sample_digit_value == 1:
            ones += 1
            one_this_digit.append(line)
        else:
            zero_this_digit.append(line)

    # This is so ugly. I misread the instructions and went down a strange path to recover
    # If starting from scratch, I would do differently
    use_ones: bool
    if ones > len(str_list) / 2:
        use_ones = True
    elif ones == len(str_list) / 2:
        use_ones = True
    else:
        use_ones = False

    # the conditions are flipped for co2, we want least common.
    if not one_bias:
        use_ones = not use_ones

    if use_ones:
        return find_match(one_this_digit, digit_index - 1, one_bias)
    else:
        return find_match(zero_this_digit, digit_index - 1, one_bias)


if __name__ == '__main__':
    day3_part2("day3.txt")