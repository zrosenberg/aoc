
def day2(file):
    f = open(file, "r")
    lines = f.readlines()
    depth = 0
    hpos = 0

    for line in lines:
        instructions = line.split(" ")
        distance = int(instructions[1])
        command = instructions[0]

        if command == "forward":
            hpos += distance
        elif command == "down":
            depth += distance
        elif command == "up":
            depth -= distance

    print(f"Horizontal position: ", hpos)
    print(f"Depth: ", depth)
    return depth * hpos


def day2_part2(file):
    f = open(file, "r")
    lines = f.readlines()
    depth = 0
    hpos = 0
    aim = 0

    for line in lines:
        instructions = line.split(" ")
        distance = int(instructions[1])
        command = instructions[0]

        # TODO: upgrade to python 3.10 and use match
        if command == "forward":
            hpos += distance
            depth += aim * distance
        elif command == "down":
            aim += distance
        elif command == "up":
            aim -= distance

    print(f"Horizontal position: ", hpos)
    print(f"Depth: ", depth)
    return depth * hpos


if __name__ == '__main__':
    print(f"depth X horizontal position: ", day2("day2.txt"))
    print(day2_part2("day2.txt"))



