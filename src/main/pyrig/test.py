import utils
import re

#list = toBcd(4123456789, 12)
#
# print ' '.join('0x%02x' % b for b in list)
#
# b = bytearray(list)
# print len(b)
# print b
# print type(b)



# def _transaction(command, data):
#         transaction= [0xfe, 0xfe, 0x5c, 0xe0, command]
#         if len(data):
#           transaction += data
#         transaction.append(0xfd)
#         return transaction
#
# trans = _transaction(0, [0, 1, 2])
# print utils.printListInHex(trans)
#
# print bytearray(trans).__str__()


transaction = bytearray([0xFE, 0xFE, 0xE0, 0x5C, 0, 0, 0xFD, 0xFE, 0xFE, 0xE0, 0x5C, 0, 0, 1, 0xFD])
TRANS_START = bytearray([0xFE, 0xFE, 0xE0, 0x5C]) # Transactions send by the Icom starts with: 0xFE 0xFE 0xE0 CIV-Adress
TRANS_END = bytearray([0xFD]) # Transactions send by the Icom ends with: 0xFD
invalid = bytearray([0xFC])


not_found = """{
"command": "not_found",
}"""



trans_start_index = transaction.find(TRANS_START)
trans_end_index = transaction.find(TRANS_END)

print 'start: '+int(trans_start_index).__str__()
print 'start: '+int(trans_end_index).__str__()


print bytearray(transaction.__str__()).__str__()




# mode_codes ={'LSB', 0x00,
#                  'USB', 0x01,
#                  'AM', 0x02,
#                  'CW', 0x03,
#                  'RTTY', 0x04,
#                  'FM', 0x05,
#                  'CWR', 0x07,
#                  'RTTYR', 0x08}
#
# print mode_codes.__contains__("CW")test.py:43