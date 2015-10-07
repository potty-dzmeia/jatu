import json
from utils import getListInHex


# TRANS_START = bytearray([0xFE, 0xFE, 0xE0])
#
#
# jsonCommandContent = dict()
#
# jsonCommandContent["frequency"] = "14195000"
# jsonCommandContent["vfo"] = getListInHex(TRANS_START)
#
# jsonBlock = dict()
# jsonBlock["frequency"] = jsonCommandContent
# print json.dumps(jsonBlock, indent=4)

def __vfo_number_to_letter(vfo_number):
        """
        Converts VFO number to a letter that can be used in the communication with the radio
        Example: 0-->"A"; 1-->"B"
        :param vfo_number: VFO number (starting from 0)
        :type vfo_number: int
        :return: The VFO letter
        :rtype: str
        """
        if vfo_number == 0:
            return "A"
        if vfo_number == 1:
            return "B"
        else:
            raise Exception("Not allowed VFO number")

print __vfo_number_to_letter(2)