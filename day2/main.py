
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


if __name__ == '__main__':
    print(f"depth X horizontal position: ", day2("day2.txt"))



