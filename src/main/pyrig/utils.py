__author__ = 'chavdar'



def toBcd(number, bcd_len):
    """
    Converts number to 4bit BCD values (little endian).
    Example: toBcd(1234,10)--> [0x34, 0x12, 0x00, 0x00, 0x00]

    :param number: number to be converted to BCD format
    :param bcd_len: how many BCD character should the output contain (must be an even value)
    :return: list of integers containing the BCD values
    """

    if bcd_len % 2 != 0:
        raise ValueError("bcd_len should be even number!")
    if len(str(number)) > bcd_len:
        raise ValueError("number is too big!")

    result = []

    for i in range(0, bcd_len / 2):
        byte = number % 10;
        number /= 10
        byte |= (number % 10) << 4
        number /= 10
        result.append(byte)

    return result


def printListInHex(list):
    print ' '.join('0x%02x' % b for b in list)

#----------------------------------------------------------------
# Unit testing below
#----------------------------------------------------------------
