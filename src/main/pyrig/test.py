

def toBcd(number, bcd_len):
        """
        Converts number to 4bit BCD values (little endian).
        Example: toBcd(1234,10)--> [0x34, 0x12, 0x00, 0x00, 0x00]

        :param number: number to be converted to BCD format
        :param bcd_len: how many BCD character should the output contain (must be an even value)
        :return: list of integers containing the BCD values
        """

        if bcd_len%2 != 0:
            raise ValueError("bcd_len should be even number!")
        if len(str(number)) > bcd_len:
            raise ValueError("number is too big!")

        result = []

        for i in range(0,bcd_len/2):
            byte = number%10;
            number /= 10
            byte |= (number%10)<<4
            number /= 10
            result.append(byte)

        return result

list = toBcd(4123456789, 12)

print ' '.join('0x%02x' % b for b in list)

b = bytearray(list)
print len(b)
print b
print type(b)

# myList = ['0', '1', '2', '3', '4', '5']
#
# for i in range(0,len(myList)/2):
#     myList[i] = myList[i]+myList[i+1]
#     myList.pop(i+1)
#
# print myList