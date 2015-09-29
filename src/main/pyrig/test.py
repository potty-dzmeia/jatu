from deluge.ui.gtkui.torrentdetails import Tab
import utils
import re


#
#   # Icom control codes used for changing the mode
# mode_codes ={'lsb':     0x00,
#                  'usb':     0x01,
#                  'am':      0x02,
#                  'cw':      0x03,
#                  'rtty':    0x04,
#                  'fm':      0x05,
#                  'cwr':     0x07,
#                  'rttyr':   0x08}
#
# def decodeMode(mode):
#         """
#         Returns a string describing the current working mode
#         :param mode: Byte describing the mode see cls.mode_codes
#         :type mode: int
#         :return: String describing the working mode (e.g. "CW"). "none" if we couldn't recognize the mode.
#         :rtype: str
#         """
#
#         # Convert the "mode" to valid mode string
#         for key, value in mode_codes.items():
#             if mode == value:
#                 return key
#
#         # In case of unknown mode integer
#         return "none"
#
#
#
#

#
# print TRANS_START.__str__()
# print TRANS_START[0:7]
# print type(TRANS_START[:10])
#
# a

def toBcd(number, bcd_len):
    """
    Converts number to 4bit BCD values (little endian).
    Example: toBcd(1234, 10) --> [0x34, 0x12, 0x00, 0x00, 0x00]

    :param number: number to be converted to BCD format
    :type number: int
    :param bcd_len: how many BCD character should the output contain (must be an even value)
    :type bcd_len: int
    :return: list of integers containing the BCD values
    :rtype: list
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


buffer1 = bytearray([0x31, 0x02, 0x10, 0x14, 0x00])
buffer = bytearray([0x00, 0x0, 0x0, 0x0, 0x10,0x20])

def fromBcd(byte_array):
    """
    Converts number in little endian 4bits per digit bcd format to number under
    :param byteArray: digits in 4bit bcd form   at (little endian)
    :type byteArray: bytearray
    :return: string containing the number
    :rtype: str
    """
    frq = 0;
    mult = 1;
    for b in byte_array:
        frq += (b & 0x0F) * mult
        mult *= 10
        frq += ((b>>4) & 0x0F) * mult
        mult *= 10

    return frq


jsn = dict()